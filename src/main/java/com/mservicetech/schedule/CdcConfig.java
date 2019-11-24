package com.mservicetech.schedule;

public class CdcConfig {

    int maxEventsPerPolling;
    int maxAttemptsForPolling;
    int pollingRetryIntervalInMilliseconds;
    int pollingIntervalInMilliseconds;

    public int getMaxEventsPerPolling() {
        return maxEventsPerPolling;
    }

    public void setMaxEventsPerPolling(int maxEventsPerPolling) {
        this.maxEventsPerPolling = maxEventsPerPolling;
    }

    public int getMaxAttemptsForPolling() {
        return maxAttemptsForPolling;
    }

    public void setMaxAttemptsForPolling(int maxAttemptsForPolling) {
        this.maxAttemptsForPolling = maxAttemptsForPolling;
    }

    public int getPollingRetryIntervalInMilliseconds() {
        return pollingRetryIntervalInMilliseconds;
    }

    public void setPollingRetryIntervalInMilliseconds(int pollingRetryIntervalInMilliseconds) {
        this.pollingRetryIntervalInMilliseconds = pollingRetryIntervalInMilliseconds;
    }

    public int getPollingIntervalInMilliseconds() {
        return pollingIntervalInMilliseconds;
    }

    public void setPollingIntervalInMilliseconds(int pollingIntervalInMilliseconds) {
        this.pollingIntervalInMilliseconds = pollingIntervalInMilliseconds;
    }
}
