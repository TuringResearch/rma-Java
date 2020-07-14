void controlMotor(String msg) {
    if (msg == ON && canOperate  && digitalRead(MOTOR) == LOW) {
        digitalWrite(MOTOR, HIGH);
        resourceData = resourceData + "motor(on);";
    } if ((msg == OFF || !canOperate) && digitalRead(MOTOR) == HIGH) {
        digitalWrite(MOTOR, LOW);
    }

    if (digitalRead(MOTOR) == HIGH) {
      resourceData = resourceData + "motor(on);";
    } else {
      resourceData = resourceData + "motor(off);";
    }
}
