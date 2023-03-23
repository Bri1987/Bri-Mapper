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
        数据交换
      </a-menu-item>
      <a-menu-item key="key3" @click="gotoASYN">
        增量同步
      </a-menu-item>
      <a-menu-item key="key4" @click="gotoEXCG">
        功能4
      </a-menu-item>
    </a-menu>
    <router-view class="routerview1"></router-view>
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
    gotoCRUD(){
      this.$router.push('/CRUD') //!
    },
    gotoCRUD(){
      this.$router.push('/CRUD')  //!
    }
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