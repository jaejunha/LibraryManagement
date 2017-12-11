#include <ESP8266WiFi.h>

const char *ssid = "iptime";
const char *password = "";

const char *ip = "192.168.1.6";
const int port = 3105;

WiFiClient client;

int irIn = 13;//D7, Sig
int uwIn = 12;//D6, Echo
int uwOut = 14;//D5, Trig

int irStatus;
long uwTime;
long uwValue;

int irCount;
int minUw;

char buffer[100];

void setup() {
  WiFi.disconnect(true);
  WiFi.begin(ssid, password);
  while (WiFi.status() != WL_CONNECTED) {
    delay(100);
    Serial.print(".");
  }
  Serial.println("WiFi connected");
  Serial.print("WiFi: ");
  Serial.println(WiFi.SSID());
  Serial.print("IP address: ");
  Serial.println(WiFi.localIP());
  Serial.print("MAC address: ");
  Serial.println(WiFi.macAddress());
  
  pinMode(irIn, INPUT);
  pinMode(uwIn, INPUT);
  pinMode(uwOut, OUTPUT);
  
  Serial.begin(115200);
}

void loop() {
    if (!client.connect(ip, port)) {
      Serial.println("connection failed");
      return;
    }
    Serial.println("succeed connection");
    
    for(int i=0, irCount =0, minUw=999;i<4;i++){
      
      for(int i=0;i<10;i++){
        delay(100);
        irStatus = digitalRead(irIn);
        irCount+=irStatus;
        
        digitalWrite(uwOut,HIGH); 
        delayMicroseconds(10); 
        digitalWrite(uwOut,LOW);
        uwTime = pulseIn(uwIn,HIGH);
        uwValue = (uwTime/29/2);
        if(minUw>uwValue)
          minUw = uwValue;
      }
      
      sprintf(buffer,"{\"ctname\":\"cnt-ir\",\"con\":%d}", irCount);
      Serial.println(buffer);
      client.print(buffer);

      sprintf(buffer,"{\"ctname\":\"cnt-uw\",\"con\":%ld}", minUw);
      Serial.println(buffer);
      client.print(buffer);
  
      if(client.available())
        Serial.println("data is transferred");
    }
}