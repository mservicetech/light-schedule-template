# light-schedule-template
It works as template for light-4j schedule service


## Project Structure diagram


//TODO


## Service details


light-schedule-template  service will start light-4j  provider (StartupHookProvider) to initial the message pulling.

For different environments, user can implement different message publisher which implement CdcPublisher interface.

And then simply change the config in the values.yml to real message publisher

 ```
  - com.mservicetech.schedule.publisher.CdcPublisher:
      - com.mservicetech.schedule.publisher.LocalCdcLogPublisher
 ```


### Configuration


planning-milestone cdc (change data capture) service includes following config for message recode pulling and message deliver:


 -- maxEventsPerPolling: maximum records each time pulling from message table

 -- maxAttemptsForPolling: in case the pulling failure,attempt time to get the from message table

 -- pollingRetryIntervalInMilliseconds:  polling Retry Interval time  In Milliseconds level

 -- pollingIntervalInMilliseconds: polling Interval time In Milliseconds level

 -- messageDeliverRetry: in case message deliver fail, retry the message deliver message


## Deploy and verify

 ### Local environment from command line

 Build and start service:

 ```
 cd ~/workspace
 git clone git@github.com:mservicetech/light-schedule-template.git
 cd light-schedule-template
 git pull origin develop
 git checkout develop

 mvn clean install


java -jar   target/light-schedule-template-1.00.jar

 ```

 for different database:

  ```
 java -jar  -Dlight-4j-config-dir=configuration/sqlserver/config  target/planning-milestone-cdc-1.00.jar


 ```