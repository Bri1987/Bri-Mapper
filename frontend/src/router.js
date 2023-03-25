import {createRouter,createWebHashHistory} from 'vue-router'
import Home from './components/Home.vue'
import CRUD from './components/CRUD.vue'
import selectAll from './components/CRUD/selectAll.vue'
import HDC from './components/HDC.vue'
import IncreSync from './components/HDC/IncreSync.vue'
import FullSync from './components/HDC/FullSync.vue'
import Unified from './components/HDC/Unified.vue'
import FirstTime from './components/HDC/FirstTime.vue'
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
            name:'CRUD',
            component:CRUD,
            children:[
                {
                    path:'selectAll',
                    component:selectAll
                }
            ]
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
            ]
        },
        {
            path:'/HDC',
            redirect:'/HDC/incre'
        }
    ]
})
export default router