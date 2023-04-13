<template>
    <canvas ref="canvas" ></canvas>
</template>
<script>
export default{
    props: {
      colors: {
        type: Array,
        default: () => ['#4CB1EF', '#424959', '#FF4B44']
        // default: () => ['#0b0b04','#66a12b','#bae33f','#66a12b','#0b0b04']
      },
      triangleSize: {
        type: Number,
        default: 90
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