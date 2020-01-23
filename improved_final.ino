
#include<Servo.h>
Servo myServo;
int incomingByte;


void setup() {
  Serial.begin(9600);
  pinMode(13, OUTPUT);    // sets the digital pin 13 as output

  myServo.attach(9);
  myServo.write(-9);
  delay(100);
  myServo.write(9);
}

void loop() {

  digitalWrite(13,LOW);
  delay(500);
  digitalWrite(13,HIGH);
  delay(500);
  if (Serial.available()>0)
  {
    incomingByte = Serial.read();
    Serial.print("I received: ");
    Serial.println(incomingByte, DEC);

     
    if (incomingByte == 50)
    {
      /*
       
       digitalWrite(13, LOW);
          delay(1500);
          digitalWrite(13, HIGH);
          delay(1500);
          */
          delay(300);
          myServo.write(-9);
          delay(100);
          myServo.write(9);
    }
    if (incomingByte == 51)
    {
      digitalWrite(13, LOW);
      while(1){
    }
    }
delay(500);
digitalWrite(13,HIGH);
delay(500);

}
}
