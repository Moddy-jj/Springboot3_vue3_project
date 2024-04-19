import './assets/main.scss'
import locale from 'element-plus/dist/locale/zh-cn.js'


import { createApp } from 'vue'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import App from './App.vue'
import router from '@/router'
import { createPinia } from 'pinia'
import {createPersistedState} from'pinia-persistedstate-plugin'

const app = createApp(App)
//pinia使用持久化插件
const pinia = createPinia()
const persist = createPersistedState()

pinia.use(persist)
app.use(ElementPlus,{locale})
app.use(pinia)
app.use(router)
app.use(ElementPlus);
app.mount('#app')
