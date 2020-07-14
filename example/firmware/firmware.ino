#include<Javino.h>

//VALORES 
#define ON "on"// fffe02on
#define OFF "off" // fffe03off
#define CAN_OPERATE "canOp" // fffe05canOp
#define CANNOT_OPERATE "cannotOp" //fffe08cannotOp
#define READ_SENSOR "getResourceData" // fffe0fgetResourceData

// ATUADORES
#define MOTOR 13
#define CAN_INDICATOR 12
#define CANNOT_INDICATOR 11

// SENSORES
#define TEMPERATURE_SENSOR A0

#define SERIAL_BEGIN 9600

Javino j;

bool canOperate = true;
String resourceData = "";

void controlMotor();
void indicateAvailability();
void readSensor();

void setup() {
    pinMode(MOTOR, OUTPUT);
    pinMode(CAN_INDICATOR, OUTPUT);
    pinMode(CANNOT_INDICATOR, OUTPUT);

    pinMode(TEMPERATURE_SENSOR, INPUT);

    Serial.begin(SERIAL_BEGIN);

    digitalWrite(CAN_INDICATOR, canOperate);
    digitalWrite(CANNOT_INDICATOR, !canOperate);
}
void loop() {  
    String msg = "";
    if (j.availablemsg()) {
        msg = j.getmsg();
        controlMotor(msg);
        indicateAvailability(msg);
        readSensor(msg);
        if (msg == READ_SENSOR) {
          resourceData = resourceData.substring(0, resourceData.length() - 1);
          j.sendmsg(resourceData);
        }
        resourceData = "";
    }
}
