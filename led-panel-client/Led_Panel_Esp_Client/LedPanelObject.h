#ifndef LED_PANEL_OBJECT
#define LED_PANEL_OBJECT

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

/* Required capcity for current JSON version */
const size_t LED_PANEL_OBJECT_CAPACITY = 512;

struct LedPanelObject {
  const char* name;
  int8_t pos_x;
  int8_t pos_y;
  int8_t rotationPoint_x;
  int8_t rotationPoint_y;
  byte* imageData;
};

#endif  //LED_PANEL_OBJECT