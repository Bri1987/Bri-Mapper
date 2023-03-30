<template>
    <a-layout>
       <!--这里应该强制刷新一下动画-->
        <canvaspage></canvaspage>
        <!-- <a>PSYCHO UNIVERSE</a> -->
        <!--sjfwpijfoefw-->
        <button data-text="Awesome" class="button">
            <span class="actual-text">&nbsp;psycho&nbsp;</span>
            <span class="hover-text" aria-hidden="true">&nbsp;psycho&nbsp;</span>
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
a{
  position: fixed;
  z-index:inherit;
  color:white;
  top:45%;
  left: 0;
  right: 0;
  font-size: 5em;
  font-weight: bold;
  text-decoration-line: underline;
  /* text-decoration-line: overline; */
  text-underline-offset: 10px;
  background: rgba(0, 0, 0, 0) ;
  background:-webkit-gradient(linear, left top, right top, from(#90f3b3), color-stop(#90e9f3), color-stop(#909ff3), color-stop(#cc90f3), color-stop(#f390d1), color-stop(#f39a90), color-stop(#f3e590), to(#b8f390)) repeat scroll 0% 0%/200% 200%;
  background: rgba(0, 0, 0, 0) linear-gradient(90deg, #90f3b3, #90e9f3, #909ff3, #cc90f3, #f390d1, #f39a90, #f3e590, #b8f390) repeat scroll 0% 0%/200% 200%;
  background-clip: border-box;
  color: transparent;
  text-decoration-color: transparent;
  -webkit-background-clip: text;
  background-clip: text ;

}
a:hover {
  -webkit-animation: GradientAnimation 2s ease infinite;
  animation: GradientAnimation 2s ease infinite;
}

@keyframes GradientAnimation {
  0% { background-position: 0% 50% }
  50% { background-position: 100% 50% }
  100% { background-position: 0% 50% }
}


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