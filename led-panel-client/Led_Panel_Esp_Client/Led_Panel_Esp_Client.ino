#include "JsonAdapter.h"

const char* testJson = "{\"name\": \"testJsonName\",\"pos_x\":10,\"pos_y\":10,\"rotationPoint_x\":5,\"rotationPoint_y\":5,\"imageData\":\"1112A7A2D71112A7A277\"}";

void setup() {
  // put your setup code here, to run once:
  Serial.begin(115200);
}

void loop() {
  Serial.println("Starte");
  LedPanelObject testObject = parseLedPanelJson(testJson);
  Serial.println(testObject.name);
  Serial.println(testObject.pos_x);
  Serial.println(testObject.pos_y);
  Serial.println(testObject.rotationPoint_x);
  Serial.println(testObject.rotationPoint_y);
  for (uint8_t i = 0; i < 10; ++i) Serial.println(testObject.imageData[i]);
  delay(1000);
}
