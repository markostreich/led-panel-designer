#define TEST_MODE
#include <string>
#include <memory>
#include "Graphics.h"
#include "JsonAdapter.h"
#include "RestClient.h"

const char* testJson = "{\"name\": \"testJsonName\",\"pos_x\":0,\"pos_y\":0,\"rotationPoint_x\":5,\"rotationPoint_y\":5,\"imageData\":\"1112A7A2D70A32A7A277\"}";

void setup() {
  Serial.begin(115200);
  connectToWiFi();
  postClientId();
}

void loop() {
  Serial.println("Starte");
  try {
    LedPanelObject testObject = parseLedPanelJson(getUpdate().c_str());
    Serial.println(testObject.name.c_str());
    Serial.println(testObject.pos_x);
    Serial.println(testObject.pos_y);
    Serial.println(testObject.rotationPoint_x);
    Serial.println(testObject.rotationPoint_y);
    Serial.println(testObject.imageData_length);
    drawObject(&testObject);
  } catch (const JsonParseException& e) {
    Serial.println(e.what());
    Serial.println(testJson);
  }
  adjustBrightness();
  delay(1000);
}
