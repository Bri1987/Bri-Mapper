import { createApp } from 'vue'
import App from './App.vue'
import './index.css'
import router from './router'
import Antd from 'ant-design-vue'
import axios from 'axios'
const app=createApp(App)

// app.use(axios)

app.use(router)
app.use(Antd)
app.mount('#app')