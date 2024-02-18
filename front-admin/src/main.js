import {createApp} from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import * as Icons from '@ant-design/icons-vue'

// 引入Ant Design
import Antd, from 'ant-design-vue';
import 'ant-design-vue/dist/reset.css';

// 引入Axios
import axios from "axios";

import './assets/js/enums';

const app = createApp(App)
app.use(Antd).use(store).use(router).mount('#app')

// 全局使用图标
const icons = Icons
for (let iconsKey in icons) {
    app.component(iconsKey, icons[iconsKey])
}

/**
 * axios拦截器
 */
axios.interceptors.request.use(function (config) {
    console.log('请求参数：', config);
    return config;
}, error => {
    return Promise.reject(error);
});
axios.interceptors.response.use(function (response) {
    console.log('返回结果：', response);
    return response;
}, error => {
    console.log('返回错误：', error);
    return Promise.reject(error);
});

axios.defaults.baseURL = process.env.VUE_APP_SERVER;
console.log('环境：', process.env.NODE_ENV);
console.log('服务端：', process.env.VUE_APP_SERVER);