//#define TEST_MODE
#include <string>
#include <memory>
#include "Graphics.h"
#include "JsonAdapter.h"
#include "RestClient.h"

const char* testJson = "{\"name\": \"testJsonName\",\"pos_x\":7,\"pos_y\":7,\"rotationPoint_x\":7,\"rotationPoint_y\":7,\"imageData\":\"0000A7A700010100A7A70202A700A70303A700A70404A700A70505A700A70606A700A70707A700A70806A800A70909A700A70A0AA700A70B0BA700A7\"}";
const char* testJson2 = "{\"name\": \"testJsonName2\",\"pos_x\":0,\"pos_y\":0,\"rotationPoint_x\":7,\"rotationPoint_y\":7,\"imageData\":\"0A0A00A7000B0B00A7000F0F00A700\"}";


void setup() {
  Serial.begin(115200);
  //connectToWiFi();
  //postClientId();
}

float angle = 0.0;

void loop() {
  Serial.println("Starte");
  try {
    colorAll(0, 0, 0);
    //LedPanelObject testObject = parseLedPanelJson(getUpdate().c_str());
    LedPanelObject testObject = parseLedPanelJson(testJson);
    LedPanelObject testObject2 = parseLedPanelJson(testJson2);
    Serial.println(testObject.name.c_str());
    Serial.println(testObject.pos_x);
    Serial.println(testObject.pos_y);
    Serial.println(testObject.rotationPoint_x);
    Serial.println(testObject.rotationPoint_y);
    Serial.println(testObject.imageData_length);
    //drawRotatedObject(&testObject, angle);
    //drawObject(&testObject);
    drawObject(&testObject2);
  } catch (const JsonParseException& e) {
    Serial.println(e.what());
    Serial.println(testJson);
  }
  adjustBrightness();
  delay(1000);
  angle = angle >= 360.0 ? 0.0 : angle + 11.0;
}
