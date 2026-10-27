import { createApp } from 'vue'
import { createPinia } from 'pinia'
import App from './App.vue'
import router from './router'
import './style.css'

// 应用入口：把路由和状态管理挂到 Vue 上
createApp(App).use(createPinia()).use(router).mount('#app')
