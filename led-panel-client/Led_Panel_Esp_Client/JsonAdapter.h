#ifndef LED_PANEL_JSON_ADAPTER
#define LED_PANEL_JSON_ADAPTER

#include <ArduinoJson.h>
#include "LedPanelObject.h"

StaticJsonDocument<512> doc;

// Function to convert a single hex character to a nibble (4 bits)
byte hexCharToNibble(char hexChar) {
  hexChar = toupper(hexChar);  // Convert to uppercase to simplify
  if (hexChar >= '0' && hexChar <= '9') {
    byte result = hexChar - '0';
    return hexChar - '0';
  } else if (hexChar >= 'A' && hexChar <= 'F') {
    byte result = hexChar - 'A' + 10;
    return result;
  }
  return 0;  // Not a valid hex char
}

// Function to convert a hex string to a byte array
void hexStringToByteArray(const String& hexString, byte* byteArray, int byteArraySize) {
  for (int i = 0; i < byteArraySize; i++) {
    byteArray[i] = (hexCharToNibble(hexString[i * 2]) << 4) | hexCharToNibble(hexString[i * 2 + 1]);
  }
}
/**
 * Parses a JSON string to populate a LedPanelObject struct.
 *
 * This function takes a JSON string as input, attempts to deserialize it, and then
 * extracts the relevant fields to populate a LedPanelObject struct. The JSON string
 * is expected to contain fields for the name, position (pos_x, pos_y), rotation point
 * (rotationPoint_x, rotationPoint_y), and image data (imageData). The image data is
 * expected to be a hexadecimal string representing byte values, which is then
 * converted to a byte array.
 *
 * @param json A null-terminated string containing the JSON data to parse.
 * @return A LedPanelObject struct populated with the parsed data. If the JSON
 *         parsing fails, the function returns an empty LedPanelObject struct.
 *
 * @note The function uses dynamic memory allocation for the imageData field in
 *       the LedPanelObject struct. It is the caller's responsibility to free this
 *       memory when it is no longer needed to avoid memory leaks.
 */
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
  result.imageData_lenght = strlen(imageData) / 2; // Size of the byte array
  result.imageData = (byte*)malloc(result.imageData_lenght * sizeof(byte)); // Create a byte array to hold the data
  hexStringToByteArray(imageData, result.imageData, result.imageData_lenght);
  return result;
}

#endif  //LED_PANEL_JSON_ADAPTER