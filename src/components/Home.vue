<template>
    <a-layout>
        <a href="#">假装这里有一个炫酷主页</a>
        <!--在这里抄的：https://wweb.dev/resources 感觉有很多开源的炫酷动画-->
    </a-layout>
</template>

<style>
a {
  font-size: 5em;
  font-weight: bold;
  font-family: sans-serif;
  text-decoration: none;
  background: rgba(0, 0, 0, 0) -webkit-gradient(linear, left top, right top, from(#90f3b3), color-stop(#90e9f3), color-stop(#909ff3), color-stop(#cc90f3), color-stop(#f390d1), color-stop(#f39a90), color-stop(#f3e590), to(#b8f390)) repeat scroll 0% 0%/200% 200%;
  background: rgba(0, 0, 0, 0) linear-gradient(90deg, #90f3b3, #90e9f3, #909ff3, #cc90f3, #f390d1, #f39a90, #f3e590, #b8f390) repeat scroll 0% 0%/200% 200%;
      background-clip: border-box;
  color: transparent;
  -webkit-background-clip: text;
  background-clip: text;
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
</style>