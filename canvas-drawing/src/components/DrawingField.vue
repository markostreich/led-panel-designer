<template>
  <div>
    <canvas
      id="drawingCanvas"
      @mousedown="startDrawing"
      @mousemove="draw"
      @mouseup="stopDrawing"
      :width="width"
      :height="height"
    ></canvas>
  </div>
  <div>
    <label for="colorInput">Set Color: </label>
    <input type="color" id="colorInput" v-model="color" />
  </div>
  <div>
    <label for="toggleGrid">Show Grid Pattern: </label>
    <input type="checkbox" id="toggleGrid" checked v-model="isGrid" />
  </div>
  <div>
    <button type="button" id="clearButton" @click="clearBoard">Clear</button>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted, computed } from "vue";

const isDrawing = ref(false);
const color = ref("#ff0000");
const isGrid = ref(true);
let context: CanvasRenderingContext2D | null = null;
let canvas: HTMLCanvasElement | null = null;

const CELL_AMOUNT_X = 30;
const CELL_AMOUNT_Y = 20;
const width = ref(600);
const height = ref(400);
const CELL_WIDTH = width.value / CELL_AMOUNT_X;
const CELL_HEIGHT = height.value / CELL_AMOUNT_Y;
const LINE_WIDTH = 1;

const startDrawing = (event: MouseEvent) => {
  if (!context) return;
  isDrawing.value = true;
  let x = event.clientX - (event.target as HTMLElement).offsetLeft;
  let y = event.clientY - (event.target as HTMLElement).offsetTop;
  console.log("x:" + x + " y:" + y);
  _draw(x, y);
};

const draw = (event: MouseEvent) => {
  if (!isDrawing.value || !context) return;
  let x = event.clientX - (event.target as HTMLElement).offsetLeft;
  let y = event.clientY - (event.target as HTMLElement).offsetTop;
  _draw(x, y);
};

const stopDrawing = () => {
  isDrawing.value = false;
};

const _draw = (x: number, y: number) => {
  if (!context) return;
  x = getXInGrid(x);
  y = getYInGrid(y);
  context.fillStyle = color.value;
  context.fillRect(
    x + LINE_WIDTH,
    y + LINE_WIDTH,
    CELL_WIDTH - LINE_WIDTH - 1,
    CELL_HEIGHT - LINE_WIDTH - 1
  );
  context.fill();
};

const getXInGrid = (x: number): number => {
  return Math.floor(x / CELL_WIDTH) * CELL_WIDTH;
};

const getYInGrid = (y: number): number => {
  return Math.floor(y / CELL_HEIGHT) * CELL_HEIGHT;
};

const clearBoard = () => {
  context?.clearRect(0, 0, width.value, height.value);
  context?.beginPath();
  drawBoard(context, width.value, height.value, CELL_AMOUNT_X, CELL_AMOUNT_Y);
};

onMounted(() => {
  initCanvas();
  drawBoard(context, width.value, height.value, CELL_AMOUNT_X, CELL_AMOUNT_Y);
  color.value = "white";
});

/**
 * Inits canvas and drawing context.
 */
const initCanvas = () => {
  canvas = document.getElementById("drawingCanvas") as HTMLCanvasElement;
  context = canvas.getContext("2d");
  if (context != null) context.imageSmoothingEnabled = false;
};

const drawBoard = (
  drawingContext: CanvasRenderingContext2D | null,
  width: number,
  height: number,
  cellAmountX: number,
  cellAmountY: number
) => {
  if (!drawingContext) return;
  drawingContext.globalAlpha = 1;
  drawingContext.fillStyle = "#000";
  drawingContext.fillRect(0, 0, width - 1, height - 1);
  drawingContext.fill();
  if (isGrid.value)
    drawGrid(drawingContext, width, height, cellAmountX, cellAmountY);
};

const drawGrid = (
  drawingContext: CanvasRenderingContext2D | null,
  width: number,
  height: number,
  cellAmountX: number,
  cellAmountY: number
) => {
  for (let x = 0; x <= width; x += width / cellAmountX) {
    drawingContext?.moveTo(0.5 + x, 0);
    drawingContext?.lineTo(0.5 + x, height);
  }
  for (let y = 0; y <= height; y += height / cellAmountY) {
    drawingContext?.moveTo(0, 0.5 + y);
    drawingContext?.lineTo(width, 0.5 + y);
  }
  if (drawingContext != null) {
    drawingContext.strokeStyle = "rgba(200,200,200,1.0)";
    drawingContext.stroke();
  }
  color.value = "#ff0000";
};
</script>

<style scoped>
#drawingCanvas {
  border: 1px solid #000;
  cursor: cell;
}
</style>
