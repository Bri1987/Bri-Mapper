import {createRouter,createWebHashHistory} from 'vue-router'
import Home from './components/Home.vue'
import CRUD from './components/CRUD.vue'
import selectAll from './components/CRUD/selectAll.vue'
import addData from './components/CRUD/addData.vue'
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
            path:'/add',
            component:addData
        }
    ]
})
export default router