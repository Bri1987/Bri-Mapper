<template>
  
    <a-layout  class="top" style="position:fixed;right: 0;left:0;bottom: 0%;height:94%;z-index: 999;">   
        <canvass></canvass>
        <a-layout-content style="padding: 0 50px; position:fixed;left:10%;right: 10%;bottom: 5%;top:10%;">
          <a-breadcrumb style="margin: 16px 0">
            <a-breadcrumb-item>MAP</a-breadcrumb-item>
            <a-breadcrumb-item>{{ itemname1 }}</a-breadcrumb-item>

          </a-breadcrumb>
          <a-layout style="padding: 24px 0; background: rgba(255, 255, 255,0.6);height: 86%;">

            <a-layout-content :style="{ padding: '0 24px', minHeight: '280px' }">
              <Websocket></Websocket>
            </a-layout-content>
          </a-layout>
        </a-layout-content>
        <a-layout-footer style="text-align: center;">
          ##计设项目小组
        </a-layout-footer>
      
      </a-layout>
    </template>
    <script>
import websocket from './websocket.vue'
import canvass from '../bcg1.vue'
import { UserOutlined, LaptopOutlined, NotificationOutlined } from '@ant-design/icons-vue';
import { defineComponent, ref } from 'vue';
import Websocket from './websocket.vue';
export default defineComponent({
      components: {
    UserOutlined,
    LaptopOutlined,
    NotificationOutlined,
    canvass,
    Websocket
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

  </style>