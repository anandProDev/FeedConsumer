##FeedMe Consumer
###Overview
This application consumes feeds from rabbitMq and persists them onto couchbase.

`http://localhost:8081/FeedConsumer`

/status - Displays if the application is running along with the status of FeedMe producer

/health – Shows application health information (a simple ‘status’ when accessed over an unauthenticated connection or full message details when authenticated). It is sensitive by default.
/info – Displays arbitrary application info. Not sensitive by default.
/metrics – Shows ‘metrics’ information for the current application. It is also sensitive by default.
/trace – Displays trace information (by default the last few HTTP requests
/mappings
/env
/health
/dump
/autoconfig

The above endpoints should be accessible with basic oAuth with username `feedme` and password `feedme`


###Couchbase setup

Once you run the command docker-compose up from the feedme app, it should bring up an instance of couchbase

1. You can access the couchbase instance with url http://localhost:8091/ui/index.html
2. Setup a cluster with defaults
3. Once the cluster is setup, create a bucket with name `feedme`
4. Add a user from the security tab with username `feedme` and password as `feedme` and give it admin privileges

###Running the application

You can start the application from command line using the command
`mvn spring-boot:run`

Once the application starts up it shoudl start reading messages from rabitMq and writing it to couchbase