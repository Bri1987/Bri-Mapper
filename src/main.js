import { createApp } from 'vue'
import App from './App.vue'
import './index.css'
import router from './router'
import Antd from 'ant-design-vue'
import Elem from 'element-plus'
import 'element-plus/dist/index.css'
import axios from 'axios'
import piniaPluginPersistedstate from 'pinia-plugin-persistedstate'
import { createPinia } from 'pinia'

const pinia = createPinia()
pinia.use(piniaPluginPersistedstate)

const app=createApp(App)
app.config.globalProperties.$axios = axios
// app.prototype.$axios = axios;
// app.use(axios)
app.use(pinia)
app.use(router)
app.use(Antd)
app.use(Elem)
app.mount('#app')