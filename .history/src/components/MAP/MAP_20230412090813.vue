<template>
  
    <a-layout  class="top" style="position:fixed;right: 0;left:0;bottom: 0%;height:94%;z-index: 999;">   
        <canvass></canvass>
        <a-layout-content style="padding: 0 50px; position:fixed;left:5%;bottom: 5%;top:10%;width:90%;">
          <a-breadcrumb style="margin: 16px 0">
            <a-breadcrumb-item>MAP</a-breadcrumb-item>
            <a-breadcrumb-item>{{ itemname1 }}</a-breadcrumb-item>

          </a-breadcrumb>
          <a-layout class="box-card" >
            <a-layout-sider width="200" style="background: rgba(255, 255, 255,0.3)">
              <a-menu
                v-model:selectedKeys="selectedKeys2"
                v-model:openKeys="openKeys"
                mode="inline"
                style="margin-top:5px;height: 95%;background: rgba(255, 255, 255,0)"
              >
                <a-menu-item key="1" @click="gotowsocket">多文件异步映射</a-menu-item>
                <a-menu-item key="2" @click="gotoView">元数据描述对象可视化</a-menu-item> 
              </a-menu>
            </a-layout-sider>
            <a-layout-content :style="{ padding: '0 24px', minHeight: '280px' }">
              <!-- <wsocket></wsocket> -->
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
import wsocket from './wsocket.vue'
import canvass from '../bcg1.vue'
import { UserOutlined, LaptopOutlined, NotificationOutlined } from '@ant-design/icons-vue';
import { defineComponent, ref } from 'vue';
export default defineComponent({
      components: {
    UserOutlined,
    LaptopOutlined,
    NotificationOutlined,
    canvass,
    wsocket
},
      data(){
        return {
          itemname1:'',
        }
      },
      setup() {
          return {
          selectedKeys1: ref(['2']),
          selectedKeys2: ref(['1']),
          openKeys: ref(['sub1']),
          };
      },
      methods: {
        gotowsocket(){
          this.itemname1='Asynchronous mapping of multiple files'
          this.$router.push('/MAP/wsocket')
        },
        gotoView(){
          this.itemname1='Metadata describes object visualization'
          this.$router.push('/MAP/View')
        }
      },
  });
  </script>
  <style>
  #components-layout-demo-top-side .logo {
  float: left;
  width: 120px;
  height: 31px;
  margin: 16px 24px 16px 0;
  /* background: rgba(255, 255, 255, 0.3); */
  }
  .ant-row-rtl #components-layout-demo-top-side .logo {
  float: right;
  margin: 16px 0 16px 24px;
  }
  .box-card{
    background: rgba(255,255,255,0.6);
    margin-bottom: 30px;
    height:94%;
    width:100%;
    border-radius:5px
  }
  </style>