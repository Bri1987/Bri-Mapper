
<template>
  <a-menu v-model:selectedKeys="current" mode="horizontal">
    <a-menu-item key="home" @click="gotoHome">
      首页
    </a-menu-item>
    <a-menu-item key="crud" @click="gotoCRUD">
      数据源增删查改
    </a-menu-item>
    <a-menu-item key="excg">
      数据交换
    </a-menu-item>
  </a-menu>
  <router-view></router-view>
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
    }
  }
}
</script>
<style lang="less">
@import "./assets/global.less";
</style>