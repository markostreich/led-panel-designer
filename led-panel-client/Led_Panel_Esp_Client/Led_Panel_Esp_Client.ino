#include "JsonAdapter.h"
#include <string>  // For std::string

const char* testJson = "{\"name\": \"testJsonName\",\"pos_x\":10,\"pos_y\":10,\"rotationPoint_x\":5,\"rotationPoint_y\":5,\"imageData\":\"1112A7A2D7F132A7A277\"}";

void setup() {
  // put your setup code here, to run once:
  Serial.begin(115200);
}

void loop() {
  Serial.println("Starte");
  LedPanelObject testObject;
  try {
  testObject = parseLedPanelJson(testJson);
  Serial.println(testObject.name.c_str());
  Serial.println(testObject.pos_x);
  Serial.println(testObject.pos_y);
  Serial.println(testObject.rotationPoint_x);
  Serial.println(testObject.rotationPoint_y);
  for (uint8_t i = 0; i < testObject.imageData_length; ++i) Serial.println(testObject.imageData[i]);
  } catch (const JsonParseException& e) {
    Serial.println(e.what());
    Serial.println(testJson);
  }
  delay(1000);
}
