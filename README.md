# Resource Management Architecture (Java)
## Description
An architecture that supports the virtualization of Devices, exposing their Resources so that it is consumed by users interested in it. This RMA is composed of 3 independent layers: Device Layer, Resource Management Layer (RML), and Application Layer;
### Device Layer: 
It is a layer of RMA composed of different or similars IoT Objects. IoT Object is a Device able to connecting and registering in the RML. Besides, IoT Objects send Data from resources to RML, and receive Actions from RML to perform some task;
### Resource Management Layer (RML): 
It is a layer of the RMA that works as a server in the IoT network. Besides, the RML manages the registering process  of IoT Objects, the Resource Data persistence, and the Actions to be performed by these Resources;
### Application Layer: 
It is a layer of the RMA composed of applications that users access to interact with IoT Objects and their environments;

## Commands

### Install ContextNet and OpenSplice
- [Click here](https://1drv.ms/u/s!AvIsgxTTFPWkiiampwSrpS5toXG2) to download the ContextNet folder.
- Extract the ContextNet.zip to a safe folder. There is no need to create a ContextNet folder for this.
- Go to ContextNet folder and execute, in administrator mode, the file install.bat. If you already have the OpenSplica
 environment variables, remove these.

### Install database
In the core/script folder:
- Edit the mongoService.bat file replacing all *@basePath* with the desired folder where you want to install the
 database.
- Save the mongoService.bat file.
- run as administrator the command *call mongoService.bat*

### Create the RMA packages
In the rma-java root: mvn clean package

### Run RML
Inside the rml/target directory, get the rml-VERSION.jar file and run:
*java -jar rml-1.0.0-SNAPSHOT.jar <gatewayIP1> <gatewayPort1> <gatewayIP2> <gatewayPort2> <...>*
    
### Run IoT Object
Inside the rma/devicelayer/target directory, get the devicelayer-VERSION.jar file and run:
*java -jar devicelayer-1.0.0-SNAPSHOT.jar <gatewayIP> <gatewayPort> <ConfigJsonFile>*
