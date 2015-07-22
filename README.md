Canary
======

CanaryServer
------------
**Building**

To build this project;

1. Copy canary.properties.template as canary.properties and edit it to specify
   your desired configuration.
2. Run `ant build`, this will generate `build/canary-server.war`.

**Running**

To run this project;

1a. Run `java -jar ../jetty-runner-8.1.9.v20130131.jar build/canary-server.war`
1b. Or deploy `build/canary-server.war` to your application server.

CanaryClient
------------
**Building**

To build this project;

1. Run `npm install`.
2. Run `grunt build`, this will generate `build/canary-client.war`.