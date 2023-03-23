<template>
    <a-layout style="position:fixed;right: 0;left:0;bottom: 0%;height:92%;">   
      <a-layout-content style="padding: 0 50px;">
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
                <a-menu-item key="2">option2</a-menu-item>
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
import { UserOutlined, LaptopOutlined, NotificationOutlined } from '@ant-design/icons-vue';
import { defineComponent, ref } from 'vue';
export default defineComponent({
    components: {
        UserOutlined,
        LaptopOutlined,
        NotificationOutlined,
    },
    setup() {
        //============================================================================
        const ws=new WebSocket('ws://localhost:8119/datasource/list')
        ws.onopen=function(){
            console.log('连接成功')
        }
        ws.onerror=function(){
            console.log('连接失败')
        }
        ws.onmessage=function(e){
            data=JSON.parse(e.data)
        }
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
</style>