import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import axios from 'axios'
import ElementPlus from "element-plus";
import 'element-plus/theme-chalk/index.css'
import '@/assets/css/global.css'
// import locale from 'element-plus/lib/locale/lang/zh-cn'
import locale from 'element-plus/lib/locale/lang/en'
import echarts from 'echarts'
const app = createApp(App)
app.use(ElementPlus, {locale, size: 'small'}).use(store). use(router).mount('#app')
app.config.globalProperties.$axios = axios
Vue.prototype.$echarts = echarts
app.prototype.$echarts = echarts