<template>
    <canvas ref="canvas" ></canvas>
</template>
<script>
export default{
    props: {
      colors: {
        type: Array,
        default: () => ['#5ac1ed', '#56b3a0', '#158479']
        // default: () => ['#0b0b04','#66a12b','#bae33f','#66a12b','#0b0b04']
      },
      triangleSize: {
        type: Number,
        default: 20
      }
    },
    mounted() {
      const canvas = this.$refs.canvas;
      const settings = {
        colors: this.colors,
        triangleSize: this.triangleSize,
      };
      ThpaceGL.create(canvas, settings);
    }
}
import { ThpaceGL } from 'thpace';

</script>