<template>
  
    <a-layout  class="top" style="position:fixed;right: 0;left:0;bottom: 0%;height:94%;z-index: 999;">   
        <canvass></canvass>
        <a-layout-content style="padding: 0 50px; position:fixed;left:5%;right: 5%;bottom: 5%;top:10%;">
          <a-breadcrumb style="margin: 16px 0">
            <a-breadcrumb-item>HDC</a-breadcrumb-item>
            <a-breadcrumb-item>{{ itemname1 }}</a-breadcrumb-item>

          </a-breadcrumb>
          <a-layout class="box-card" style="padding: 24px 0; background: rgba(255, 255, 255,0.6);height: 86%;width: 80%;left: 10%;position: absolute;">
            <a-layout-sider width="200" style="background: rgba(255, 255, 255,0.3)">
              <a-menu
                v-model:selectedKeys="selectedKeys2"
                v-model:openKeys="openKeys"
                mode="inline"
                style="height: 100%;background: rgba(255, 255, 255,0)"
              >
                  <a-menu-item key="1" @click="gotoIncre">增量同步</a-menu-item>
                  <a-menu-item key="2" @click="gotoFull">全量同步</a-menu-item>
                  <a-popconfirm placement="right" ok-text="Yes" cancel-text="No" @confirm="firstSelect" @cancel="notfirstSelect">
                    <template #title>
                      <p>是否为第一次搜索</p>
                    </template>
                    <a-menu-item key="3" @click="gotoUnified">统一搜索</a-menu-item>
                  </a-popconfirm> 
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
      methods:{
        gotoIncre(){
            this.itemname1='Increment Synchronization'
  
            this.$router.push('/HDC/incre')
          },
        gotoFull(){
          this.itemname1='Full synchronization'

          this.$router.push('/HDC/full')
        },
        gotoUnified(){
            this.itemname1='Unified search'
            //这里的逻辑是
            /**
             * 1.点击后直接弹出一个询问框：是否是一个次搜索
             * 2.是：跳转到填写
             * 3.不是，直接添砖到全局搜索——》搜索出现小卡片
             * 4. 离开时弹窗是否删除es中的所有的数据？？
             */
        },
        notfirstSelect(){
          this.$router.push('/HDC/unified')
        },
        firstSelect(){
          this.$router.push('/HDC/form')
        }
      }
  });
  </script>
  <style scoped>
  #components-layout-demo-top-side .logo {
  float: left;
  width: 120px;
  height: 31px;
  margin: 16px 24px 16px 0;
  }
  
  .ant-row-rtl #components-layout-demo-top-side .logo {
  float: right;
  margin: 16px 0 16px 24px;
  }
  .box-card{
    background: rgba(255,255,255,0.6);
    margin-bottom: 30px;
    height:90%;
    width:100%;
    border-radius:5px
  }
  </style>