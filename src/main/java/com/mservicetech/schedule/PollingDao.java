package com.mservicetech.schedule;

import com.networknt.tram.cdc.mysql.connector.MessageWithDestination;
import com.networknt.tram.cdc.polling.connector.MessagePollingDataProvider;
import com.networknt.tram.cdc.polling.connector.PublishedMessageBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

public class PollingDao<EVENT_BEAN, EVENT, ID> {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private MessagePollingDataProvider pollingDataParser;
    private DataSource dataSource;
    private int maxEventsPerPolling;
    private int maxAttemptsForPolling;
    private int pollingRetryIntervalInMilliseconds;

    public PollingDao(MessagePollingDataProvider pollingDataParser, DataSource dataSource, int maxEventsPerPolling, int maxAttemptsForPolling, int pollingRetryIntervalInMilliseconds) {
        if (maxEventsPerPolling <= 0) {
            throw new IllegalArgumentException("Max events per polling parameter should be greater than 0.");
        } else {
            this.pollingDataParser = pollingDataParser;
            this.dataSource = dataSource;
            this.maxEventsPerPolling = maxEventsPerPolling;
            this.maxAttemptsForPolling = maxAttemptsForPolling;
            this.pollingRetryIntervalInMilliseconds = pollingRetryIntervalInMilliseconds;
        }
    }

    public int getMaxEventsPerPolling() {
        return this.maxEventsPerPolling;
    }

    public void setMaxEventsPerPolling(int maxEventsPerPolling) {
        this.maxEventsPerPolling = maxEventsPerPolling;
    }

    public List<MessageWithDestination> findEventsToPublish() {
        String query = String.format("SELECT TOP  %s * FROM %s WHERE %s = 0  ORDER BY %s ASC", this.maxEventsPerPolling, this.pollingDataParser.table(), this.pollingDataParser.publishedField(), this.pollingDataParser.idField());
        List<PublishedMessageBean> messageBeans = (List)this.handleConnectionLost(() -> {
            return this.handleFindQuery(query);
        });
        return (List)messageBeans.stream().map(pollingDataParser::transformEventBeanToEvent).collect(Collectors.toList());
    }

    private List<PublishedMessageBean> handleFindQuery(String query) throws SQLException {
        this.logger.info("cdc polling query:" + query);
        ArrayList messages = new ArrayList();

        try {
            Connection connection = this.dataSource.getConnection();
            Throwable var5 = null;

            try {
                PreparedStatement stmt = connection.prepareStatement(query);
                ResultSet rs = stmt.executeQuery();

                while(rs.next()) {
                    PublishedMessageBean publishedEventBean = new PublishedMessageBean(rs.getString(this.pollingDataParser.idField()), rs.getString(this.pollingDataParser.destinationField()), rs.getString(this.pollingDataParser.headersField()), rs.getString(this.pollingDataParser.payloadField()));
                    messages.add(publishedEventBean);
                }
            } catch (Throwable var17) {
                var5 = var17;
                throw var17;
            } finally {
                if (connection != null) {
                    if (var5 != null) {
                        try {
                            connection.close();
                        } catch (Throwable var16) {
                            var5.addSuppressed(var16);
                        }
                    } else {
                        connection.close();
                    }
                }

            }

            return messages;
        } catch (SQLException var19) {
            this.logger.error("SqlException:", var19);
            throw new SQLException(var19);
        }
    }

    public void markEventsAsPublished(List<MessageWithDestination> events) {
        List<String> ids = (List)events.stream().map((message) -> {
            return this.pollingDataParser.getId(message);
        }).collect(Collectors.toList());
        String query = String.format("UPDATE %s SET %s = 1 WHERE %s in (%s)", this.pollingDataParser.table(), this.pollingDataParser.publishedField(), this.pollingDataParser.idField(), preparePlaceHolders(ids.size()));
        this.handleConnectionLost(() -> {
            return this.handleUpdatePublished(query, ids);
        });
    }

    private int handleUpdatePublished(String query, List<String> ids) throws SQLException {
        this.logger.info("mark Events As Published query:" + query);
        boolean var3 = false;

        try {
            Connection connection = this.dataSource.getConnection();
            Throwable var5 = null;

            int count;
            try {
                PreparedStatement stmt = connection.prepareStatement(query);
                setValues(stmt, ids.toArray());
                count = stmt.executeUpdate();
                System.out.println("result:" + count);
            } catch (Throwable var15) {
                var5 = var15;
                throw var15;
            } finally {
                if (connection != null) {
                    if (var5 != null) {
                        try {
                            connection.close();
                        } catch (Throwable var14) {
                            var5.addSuppressed(var14);
                        }
                    } else {
                        connection.close();
                    }
                }

            }

            return count;
        } catch (SQLException var17) {
            this.logger.error("SqlException:", var17);
            throw new SQLException(var17);
        }
    }

    private <T> T handleConnectionLost(Callable<T> query) {
        int attempt = 0;

        while(true) {
            try {
                T result = query.call();
                if (attempt > 0) {
                    this.logger.info("Reconnected to database");
                }

                return result;
            } catch (SQLException var6) {
                this.logger.error(String.format("Could not access database %s - retrying in %s milliseconds", var6.getMessage(), this.pollingRetryIntervalInMilliseconds), var6);
                if (attempt++ >= this.maxAttemptsForPolling) {
                    throw new RuntimeException(var6);
                }

                try {
                    Thread.sleep((long)this.pollingRetryIntervalInMilliseconds);
                } catch (InterruptedException var5) {
                    this.logger.error(var5.getMessage(), var5);
                }
            } catch (Exception var7) {
                throw new RuntimeException(var7);
            }
        }
    }

    public static String preparePlaceHolders(int length) {
        return String.join(",", Collections.nCopies(length, "?"));
    }

    public static void setValues(PreparedStatement preparedStatement, Object... values) throws SQLException {
        for(int i = 0; i < values.length; ++i) {
            preparedStatement.setObject(i + 1, values[i]);
        }

    }
}
