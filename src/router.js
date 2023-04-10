import {createRouter,createWebHashHistory} from 'vue-router'
import Home from './components/Home.vue'
import HDC from './components/HDC.vue'
import IncreSync from './components/HDC/IncreSync.vue'
import FullSync from './components/HDC/FullSync.vue'
import Unified from './components/HDC/Unified.vue'
import FirstTime from './components/HDC/FirstTime.vue'
import HDB from './components/HDB.vue'
import User from './view/users/Users.vue'
import Map from './components/MAP/MAP.vue'
import Websocket from './components/MAP/Websocket.vue'
import View from './components/MAP/View.vue'
// import addData from './components/bcg1.vue'
//创建路由实例对象
const router=createRouter({
    history:createWebHashHistory(),
    routes:[
        {
            path:'/home',
            name:'home',
            component:Home
        },
        {
            path:'/CRUD',
            component:User,
        },
        {
            path:'/',
            redirect:'/home'
        },
        {
            path:'/CRUD',
            redirect:'/CRUD/selectAll'
        },
        {
            path:'/HDC',
            component:HDC,
            children:[
                {
                    path:'incre',
                    component:IncreSync
                },
                {
                    path:'full',
                    component:FullSync
                },
                {
                    path:'unified',
                    component:Unified
                },
                {
                    path:'form',
                    component:FirstTime
                },  
                {
                    path:'/HDC',
                    redirect:'/HDC/Incre'
                },
            ]
        },
        {
            path:'/HDB',
            component:HDB
        },
        {
            path:'/MAP',
            component:Map,
            children:[
                {
                    path:'Websocket',
                    component:Websocket
                },
                {
                    path:'/MAP',
                    redirect:'/MAP/Websocket'
                },
                {
                    path:'View',
                    component:View
                }
            ]
        }
    ]
})
export default router