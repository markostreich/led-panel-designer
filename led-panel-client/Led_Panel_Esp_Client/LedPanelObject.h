#ifndef LED_PANEL_OBJECT
#define LED_PANEL_OBJECT

/* Required capcity for current JSON version */
const size_t LED_PANEL_OBJECT_CAPACITY = 512;

/*
{
  "name": "",
  "pos_x":"",
  "pos_y":"",
  "rotationPoint_x":"",
  "rotationPoint_y":"",
  "imageData":"" //hexData
}
*/
struct LedPanelObject {
  const char* name;
  int8_t pos_x;
  int8_t pos_y;
  int8_t rotationPoint_x;
  int8_t rotationPoint_y;
  uint8_t imageData_lenght;
  byte* imageData;
};

/**
 * Frees the dynamically allocated memory for the imageData field in a LedPanelObject struct.
 *
 * This function is designed to release the memory that was previously allocated for the imageData
 * field of a LedPanelObject struct.
 *
 * @param ledPanel A reference to a LedPanelObject struct whose imageData field's memory needs to be freed.
 */
void freeLedPanelObject(const LedPanelObject& ledPanel) {
 // Free the dynamically allocated memory for imageData.
 free(ledPanel.imageData);
 // Reset the imageData pointer and length to avoid dangling pointers.
 ledPanel.imageData = nullptr;
 ledPanel.imageData_lenght = 0;
}

#endif  //LED_PANEL_OBJECT