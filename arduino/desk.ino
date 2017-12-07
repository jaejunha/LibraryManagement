#include <Servo.h>
Servo servo;

int bluePin = 3;
int redPin = 5;
int greenPin = 6;

int rgbStatus = 0;

int servoPin = 9;
int servoStatus = 0;

void setup() {
	Serial.begin(9600);
        
	servo.attach(servoPin); 
	servo.write(90);

        pinMode(bluePin, OUTPUT);
        pinMode(redPin, OUTPUT);
        pinMode(greenPin, OUTPUT);

}

void loop() {
	if(servoStatus==0)
		servoStatus = 90;
	else
		servoStatus = 0; 
	servo.write(servoStatus);

	rgbStatus+=100;
        if(rgbStatus >= 256)
                rgbStatus = 0;

        analogWrite(bluePin, rgbStatus);
        analogWrite(redPin, rgbStatus);
        analogWrite(greenPin, rgbStatus);

        delay(10000);
}