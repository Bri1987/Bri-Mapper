<template>
  
  <a-layout  class="top" style="position:fixed;right: 0;left:0;bottom: 0%;height:94%;z-index: 999;">   
      <canvass></canvass>
      <a-layout-content style="padding: 0 50px; position:fixed;left:10%;right: 10%;bottom: 10%;top:10%">
        <a-breadcrumb style="margin: 16px 0">
          <a-breadcrumb-item>CRUD</a-breadcrumb-item>
          <a-breadcrumb-item>{{ itemname }}</a-breadcrumb-item>
        </a-breadcrumb>
        <a-layout style="padding: 24px 0; background: #fff;height: 86%;">
          <a-layout-sider width="200" style="background: #fff">
            <a-menu
              v-model:selectedKeys="selectedKeys2"
              v-model:openKeys="openKeys"
              mode="inline"
              style="height: 100%"
            >
                <a-menu-item key="1" @click="gotoSelectAll">查看所有数据源</a-menu-item>
                <a-menu-item key="2" @click="gotoAdd">增加数据源</a-menu-item>
                <a-menu-item key="3">option3</a-menu-item>
                <a-menu-item key="4">option4</a-menu-item>
            </a-menu>
          </a-layout-sider>
          <a-layout-content :style="{ padding: '0 24px', minHeight: '280px' }">
            <router-view></router-view>
          </a-layout-content>
        </a-layout>
      </a-layout-content>
      <a-layout-footer style="text-align: center;">
        ##计设项目小组
      </a-layout-footer>
    
    </a-layout>
  </template>
  <script>
  import canvass from './bcg1.vue'
import { UserOutlined, LaptopOutlined, NotificationOutlined } from '@ant-design/icons-vue';
import { defineComponent, ref } from 'vue';
export default defineComponent({
    components: {
        UserOutlined,
        LaptopOutlined,
        NotificationOutlined,
        canvass
    },
    setup() {
        //============================================================================
        
        //============================================================================

        
        return {
        selectedKeys1: ref(['2']),
        selectedKeys2: ref(['1']),
        openKeys: ref(['sub1']),
        };
    },
    methods:{
        gotoSelectAll(){
            this.$router.push('/CRUD/selectAll')
        },
        gotoAdd(){
          this.$router.push('/CRUD/add')
        }
    }
});
</script>
<style>
#components-layout-demo-top-side .logo {
float: left;
width: 120px;
height: 31px;
margin: 16px 24px 16px 0;
background: rgba(255, 255, 255, 0.3);
}

.ant-row-rtl #components-layout-demo-top-side .logo {
float: right;
margin: 16px 0 16px 24px;
}

.site-layout-background {
background: #fff;
}
.top{
  background: rbga(0, 0, 0,0.1)
}
</style>