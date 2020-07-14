// Um teste de frequência do arduino mostrou que, para o código atual, estamos sendo capazes de fazer 20Hz. Neste caso, irei definir que a frequência de envio de medidas de sensor será de 10Hz.
#define DESIRED_FREQUENCY 10

unsigned long firstFreqTime = millis();
unsigned long firstSecondTime = millis();
String sensorValues = "";
void readSensor(String msg) {
  float value = analogRead(TEMPERATURE_SENSOR);
  float degreeValue = (float (value)* 5 / (1023)) / 0.01;
  resourceData = resourceData + "temperature(" + String(degreeValue) + ");";
}
/*
void readSensor() {
  unsigned long currentTime = millis();
  
  if ((currentTime - firstFreqTime) >= (1000 / DESIRED_FREQUENCY)) {
      float value = analogRead(TEMPERATURE_SENSOR);
      float degreeValue = (float (value)* 5 / (1023)) / 0.01;

      sensorValues = sensorValues + String(degreeValue) + ";";
      firstFreqTime = millis();
  }

  if ((currentTime - firstSecondTime) >= 1000) {
      sensorValues = sensorValues.substring(0, sensorValues.length() - 1);
      j.sendmsg(sensorValues);
      sensorValues = "";
      firstSecondTime = millis();
  }
}
*/
