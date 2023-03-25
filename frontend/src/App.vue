<template>
  <div>
    <a-menu v-model:selectedKeys="current" mode="horizontal">
      <a-menu-item key="home" @click="gotoHome">
        首页
      </a-menu-item>
      <a-menu-item key="crud" @click="gotoCRUD">
        数据源增删查改
      </a-menu-item>
      <a-menu-item key="excg" @click="gotoEXCG">
        映射
      </a-menu-item>
      <a-menu-item key="HDC" @click="gotoHDC">
        异构数据功能
      </a-menu-item>
      <a-menu-item key="HDB" @click="gotoHDBHDB">
        异构数据库
      </a-menu-item>
    </a-menu>
    <router-view class="routerview1"></router-view>
    <!-- <canvas ref="canvas" width="700" height="700"></canvas> -->
  </div>
  
  
</template>

<script>

export default {
  name: 'App',
  components: {
  },
  methods:{
    gotoHome(){
      this.$router.push('/home')
    },
    gotoCRUD(){
      this.$router.push('/CRUD')
    },
    gotoCRUD(){
      this.$router.push('/CRUD') //!
    },
    gotoHDC(){
      this.$router.push('/HDC') //!
    },
    gotoHDB(){
      this.$router.push('/HDB')  //!
    },
  }
}
</script>
<style lang="less" scoped>
@import "./assets/global.less";
div{
  position: fixed;
  top:0%;
  bottom: 0%;
  left: 0%;
  right: 0%;
}
a-menu{
  position: fixed;
  top:0%;
}
</style>