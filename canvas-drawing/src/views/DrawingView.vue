<template>
  <div class="drawing">
    <DrawingField />
    <ColorPicker />
  </div>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import DrawingField from "@/components/DrawingField.vue"; // @ is an alias to /src
import ColorPicker from "@/components/ColorPicker.vue";

export default defineComponent({
  name: "DrawingView",
  components: {
    DrawingField,
    ColorPicker,
  },
});
</script>
