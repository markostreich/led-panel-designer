#ifndef LED_PANEL_JSON_ADAPTER
#define LED_PANEL_JSON_ADAPTER

#include <ArduinoJson.h>
#include "LedPanelObject.h"

StaticJsonDocument<512> doc;

// Function to convert a single hex character to a nibble (4 bits)
byte hexCharToNibble(char hexChar) {
  hexChar = toupper(hexChar);  // Convert to uppercase to simplify
  if (hexChar >= '0' && hexChar <= '9') {
    return hexChar - '0';
  } else if (hexChar >= 'A' && hexChar <= 'F') {
    return hexChar - 'A' + 10;
  }
  return 0;  // Not a valid hex char
}

// Function to convert a hex string to a byte array
void hexStringToByteArray(const String& hexString, byte* byteArray, int byteArraySize) {
  for (int i = 0; i < byteArraySize; i++) {
    byteArray[i] = (hexCharToNibble(hexString[i * 2]) << 4) | hexCharToNibble(hexString[i * 2 + 1]);
  }
}

LedPanelObject parseLedPanelJson(const char* json) {
  const DeserializationError error = deserializeJson(doc, json);
  LedPanelObject result;
  if (error) {
    Serial.print(F("deserializeJson() failed: "));
    Serial.println(error.c_str());
    return result;
  }
  result.name = doc["name"];
  result.pos_x = doc["pos_x"];
  result.pos_y = doc["pos_y"];
  result.rotationPoint_x = doc["rotationPoint_x"];
  result.rotationPoint_y = doc["rotationPoint_y"];
  const char* imageData = doc["imageData"];
  // Convert hex string to byte array
  int byteArraySize = strlen(imageData) / 2;  // Size of the byte array
  byte byteArray[byteArraySize];            // Create a byte array to hold the data
  hexStringToByteArray(imageData, byteArray, byteArraySize);
  result.imageData = byteArray;
  return result;
}

#endif  //LED_PANEL_JSON_ADAPTER