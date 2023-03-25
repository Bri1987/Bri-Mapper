<template>
    <a-layout>
        <!--在这里抄的：https://wweb.dev/resources 感觉有很多开源的炫酷动画-->
        <canvaspage></canvaspage>
        <a href="#">PSYCHO UNIVERSE</a>
    </a-layout>
</template>
<script>
import canvaspage from './homepage.vue'
export default{
  components:{
    canvaspage,
  }
}
</script>
<style>
a{
  position: fixed;
  z-index:inherit;
  color:white;
  top:50%;
  left: 0;
  right: 0;
  font-size: xx-large;

}
</style>