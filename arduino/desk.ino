#include <Servo.h>
Servo servo;

int bluePin = 3;
int redPin = 5;
int greenPin = 6;

int bluePin2 = 7;
int redPin2 = 8;
int greenPin2 = 2;

int rgbStatus = 0;

int servoPin = 9;
int servoStatus = 0;

String buffer;

void setup() {
	Serial.begin(9600);
        
	servo.attach(servoPin); 

        pinMode(bluePin, OUTPUT);
        pinMode(redPin, OUTPUT);
        pinMode(greenPin, OUTPUT);
        
        pinMode(bluePin2, OUTPUT);
        pinMode(redPin2, OUTPUT);
        pinMode(greenPin2, OUTPUT);
        
        
	servoStatus = 0;
        rgbStatus = 0;	
        servo.write(servoStatus);
        analogWrite(bluePin, rgbStatus);
        analogWrite(redPin, rgbStatus);
        analogWrite(greenPin, rgbStatus);
        analogWrite(bluePin2, 0);
        analogWrite(redPin2,0);
        analogWrite(greenPin2, 255);
}

void loop() {
	if(Serial.available()){
	        buffer = Serial.readString();
                if(buffer == "init"){
                        servoStatus = 0;
                        rgbStatus = 0;	
        		servo.write(servoStatus);
        		analogWrite(bluePin, rgbStatus);
        	        analogWrite(redPin, rgbStatus);
                	analogWrite(greenPin, rgbStatus);
        		analogWrite(bluePin2, 0);
        	        analogWrite(redPin2,0);
                	analogWrite(greenPin2, 255);
                }
                else{
        		if(buffer.charAt(0)=='m'){
        			servoStatus = buffer.substring(2).toInt();	
        			servo.write(servoStatus);
        
        		}
        		if(buffer.charAt(0)=='l'){
        			rgbStatus = buffer.substring(2).toInt();
        			analogWrite(bluePin, rgbStatus);
        	        	analogWrite(redPin, rgbStatus);
                		analogWrite(greenPin, rgbStatus);
        		}
        		if(buffer.charAt(0)=='r'){
        			analogWrite(bluePin2, 0);
        		        analogWrite(redPin2, 255);
                		analogWrite(greenPin2, 0);
        		}
                        if(buffer.charAt(0)=='g'){
        			analogWrite(bluePin2, 0);
        		        analogWrite(redPin2,0);
                		analogWrite(greenPin2, 255);
        		}
                        if(buffer.charAt(0)=='b'){
        			analogWrite(bluePin2, 255);
        		        analogWrite(redPin2, 0);
                		analogWrite(greenPin2, 0);
        		}
                }
	}
}