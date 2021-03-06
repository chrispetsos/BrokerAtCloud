BrokerAtCloud
=============

This repository will contain the various software components developed by SEERC for the needs of Broker@Cloud project.

PolicyCompletenessCompliance
----------------------------

This is an Eclipse project that contains the code for the Service Description validation mechanism. The code needs as input a Service Description file and a Broker Policy file to validate against.

The path to the Broker Policy file is declared here,

https://github.com/chrispetsos-seerc/BrokerAtCloud/blob/master/PolicyCompletenessCompliance/src/org/seerc/brokeratcloud/policycompletenesscompliance/PolicyCompletenessCompliance.java#L39

and the path to the Service Description file is declared here,

https://github.com/chrispetsos-seerc/BrokerAtCloud/blob/master/PolicyCompletenessCompliance/src/org/seerc/brokeratcloud/policycompletenesscompliance/PolicyCompletenessCompliance.java#L40

You are free to change those paths to point to your own Broker Policy and Service Description files in order to demonstrate the mechanism.


MessageBroker
----------------------------

This is an Eclipse project that contains the code for various components that orchestrate the procedure of interacting with the WSO2 Message Broker (MB) and the WSO2 Governance Registry (GReg). Specifically,

Class https://github.com/chrispetsos-seerc/BrokerAtCloud/blob/master/MessageBroker/src/org/seerc/brokeratcloud/messagebroker/EvaluationComponentSDSubscriber.java 
is responsible for receiving new published Service Descriptions, sending them to GReg and publishing evaluation reports back to MB.

Class https://github.com/chrispetsos-seerc/BrokerAtCloud/blob/master/MessageBroker/src/org/seerc/brokeratcloud/messagebroker/RegistryRepositoryTopicSubscriber.java 
is responsible for listening for SD reports published at the MB and assigning those reports to SDs' properties inside GReg.

Some hepler classes are,
https://github.com/chrispetsos-seerc/BrokerAtCloud/blob/master/MessageBroker/src/org/seerc/brokeratcloud/messagebroker/MessageBrokerDemo.java that runs a demo of publishing a new SD in order to see the whole procedure in action.
https://github.com/chrispetsos-seerc/BrokerAtCloud/blob/master/MessageBroker/src/org/seerc/brokeratcloud/messagebroker/MessageBrokerStressTest.java contains some stress tests that bring WSO2 MB and GReg to their limits.

Folder https://github.com/chrispetsos-seerc/BrokerAtCloud/tree/master/MessageBroker/resources/properties has some configuration files used for connecting to the WSO2 MB and GReg.

MessageBrokerComponents
----------------------------

This is an Eclipse Dynamic Web project (web application) that wraps the MessageBroker project and starts various needed MB components on startup of the web application.

org.seerc.brokeratcloud.webservice
----------------------------

This is an Eclipse project that contains a web application which publishes all REST services provided by SEERC.

BrokerPolicyValidationHandler
----------------------------
This is a WSO2 Developer Studio Registry Handler project. It implements the Handler functionality for validating Broker Policies when they are PUT inside GReg at their specified path.

It also contains the **BrokerAtCloud sub-project** which is a WSO2 Composite Application (CApp) that registers the handler when run inside GReg. Note that in order for the BrokerAtCloud CApp to run successfully in WSO2 Governance Registry, one should put inside CARBON_HOME/repository/components/lib the PolicyCompletenessCompliance and MessageBroker code as JAR files and the Apache Jena 2.11.1 JARs contained in its "lib" subfolder. Jena's JARs should be the following:

commons-codec-1.6.jar

httpclient-4.2.3.jar

httpcore-4.2.2.jar

jcl-over-slf4j-1.6.4.jar

jena-arq-2.11.1.jar

jena-core-2.11.1.jar

jena-iri-1.0.1.jar

jena-sdb-1.4.1.jar

jena-tdb-1.0.1.jar

log4j-1.2.16.jar

slf4j-api-1.6.4.jar

slf4j-log4j12-1.6.4.jar

xercesImpl-2.11.0.jar

xml-apis-1.4.01.jar

Also the MessageBroker's JARs (appearing in its lib folder should be put in the same place (keep newer slf4j...).

After those JARs have been put in place, one should restart GReg and should be able to run the "BrokerAtCloud" CApp inside WSO2 GReg.

It should be also noted that whenever the PolicyCompletenessCompliance and/or MessageBroker changes, one should:

1) Re-build the PolicyCompletenessCompliance and/or MessageBroker jar.

2) Copy the built jar(s) in the CARBON_HOME/repository/components/lib folder.

3) Restart GReg

ServiceDeprecationHandler
----------------------------
This is a WSO2 Developer Studio Registry Handler project. It implements the Handler functionality for service deprecation.

BrokerLibs
----------------------------
Needed JAR libs are no longer exported in JARs and WARs. This has been done in order to facilitate deployment, bundles were more than 50 MB. Now, all contents of lib folder in BrokerLibs should be placed in "<GREG_HOME>/repository/components/lib" and "<TOMCAT_HOME>/lib". Bundles have now been diminished to ~100/KB.
