<template>
    <a-layout style="position:fixed;right: 0;left:0;bottom: 0%;height:94%;z-index: 999;">
       <!--这里应该强制刷新一下动画-->
        <canvaspage></canvaspage>
        <!-- <a>PSYCHO UNIVERSE</a> -->
        <!--sjfwpijfoefw-->
        <button data-text="Awesome" class="button">
            <span class="actual-text">&nbsp;mapper&nbsp;</span>
            <span class="hover-text" aria-hidden="true">&nbsp;mapper&nbsp;</span>
        </button>
        <!--idfjwopfof-->
    </a-layout>
</template>
<script>
import canvaspage from './homepage.vue'
export default{
  components:{
    canvaspage,
  },
}
</script>
<style scoped>
/* === removing default button style ===*/
.button {
  position: fixed;
  left:40%;
  top:300px;
  bottom: 40%;;
  margin: 0;
  height: fit-content;
  width: fit-content;
  background: transparent;
  padding: 0;
  border: none;
}

/* button styling */
.button {
  --border-right: 6px;
  --text-stroke-color: rgba(255,255,255,0.6);
  --animation-color: #37FF8B;
  --fs-size: 4em;
  letter-spacing: 3px;
  text-decoration: none;
  font-size: var(--fs-size);
  font-family: "Arial";
  position: relative;
  text-transform: uppercase;
  color: transparent;
  -webkit-text-stroke: 1px var(--text-stroke-color);
}
/* this is the text, when you hover on button */
.hover-text {
  position: absolute;
  box-sizing: border-box;
  content: attr(data-text);
  color: var(--animation-color);
  width: 0%;
  inset: 0;
  border-right: var(--border-right) solid var(--animation-color);
  overflow: hidden;
  transition: 0.5s;
  -webkit-text-stroke: 1px var(--animation-color);
}
/* hover */
.button:hover .hover-text {
  width: 100%;
  filter: drop-shadow(0 0 23px var(--animation-color))
}
</style>