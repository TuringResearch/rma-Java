void indicateAvailability(String msg) {
    if (msg == CAN_OPERATE) {
        canOperate = true;
        digitalWrite(CAN_INDICATOR, HIGH);
        digitalWrite(CANNOT_INDICATOR, LOW);
    } else if (msg == CANNOT_OPERATE) {
        canOperate = false;
        digitalWrite(CAN_INDICATOR, LOW);
        digitalWrite(CANNOT_INDICATOR, HIGH);
        digitalWrite(MOTOR, LOW);
    } 
    if (digitalRead(CAN_INDICATOR) == HIGH) {
      resourceData = resourceData + "locker(canOp);";
    } else {
      resourceData = resourceData + "locker(cannotOp);";
    }
}
