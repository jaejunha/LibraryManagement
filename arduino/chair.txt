#include <ESP8266WiFi.h>

const char *ssid = "iptime";
const char *password = "";

const char *ip = "192.168.1.6";
const int port = 3105;

WiFiClient client;

int irIn = 14;//D5, Sig
int uwIn = 12;//D6, Echo
int uwOut = 13;//D7, Trig

int irStatus;
long uwTime;
long uwValue;

char buffer[100];

void setup() {
  WiFi.disconnect(true);
  WiFi.begin(ssid, password);
  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
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
    
    for(int i=0;i<4;i++){
      delay(200);
      irStatus = digitalRead(irIn);
      sprintf(buffer,"{\"ctname\":\"cnt-ir\",\"con\":%d}", irStatus);
      Serial.println(buffer);
      client.print(buffer);
      
      digitalWrite(uwOut,HIGH); 
      delayMicroseconds(10); 
      digitalWrite(uwOut,LOW);
  
      uwTime = pulseIn(uwIn,HIGH);
      uwValue = (uwTime/29/2);
      sprintf(buffer,"{\"ctname\":\"cnt-uw\",\"con\":%ld}", uwValue);
      Serial.println(buffer);
      client.print(buffer);
  
      if(client.available())
        Serial.println("data is transferred");
    }
}