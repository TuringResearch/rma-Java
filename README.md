# Resource Management Architecture (Java)
## Description
An architecture that supports the virtualization of Devices, exposing their Resources so that it is consumed by users interested in it. This RMA is composed of 3 independent layers: Device Layer, Resource Management Layer (RML), and Application Layer;
### Device Layer: it is a layer of RMA composed of different or similars IoT Objects. IoT Object is a Device able to connecting and registering in the RML. Besides, IoT Objects send Data from resources to RML, and receive Actions from RML to perform some task;
### Resource Management Layer (RML): it is a layer of the RMA that works as a server in the IoT network. Besides, the RML manages the registering process  of IoT Objects, the Resource Data persistence, and the Actions to be performed by these Resources;
### Application Layer: it is a layer of the RMA composed of applications that users access to interact with IoT Objects and their environments;

    
