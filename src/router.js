import {createRouter,createWebHashHistory} from 'vue-router'
import Home from './components/Home.vue'
import CRUD from './components/CRUD.vue'
import selectAll from './components/CRUD/selectAll.vue'
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
        }
    ]
})
export default router