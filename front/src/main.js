import {createApp} from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import * as Icons from '@ant-design/icons-vue'

// 引入Ant Design
import Antd, {notification} from 'ant-design-vue';
import 'ant-design-vue/dist/reset.css';

// 引入Axios
import axios from "axios";

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
    const _token = store.state.member.token;
    if (_token) {
        config.headers.token = _token;
        console.log("为请求头添加token:", _token);

    }
    return config;
}, error => {
    return Promise.reject(error);
});
axios.interceptors.response.use(function (response) {
    console.log('返回结果：', response);
    return response;
}, error => {
    console.log('返回错误：', error);
    const status = error.response.status;
    if (status === 401) {
        console.log("未登录或者登录超时，跳到登录页面");
        // 将前端的会员信息设置为空，防止空指针异常
        store.commit("setMember", {});
        notification.error({
            message: "未登录或者登录超时"
        });
        router.push("/login");
    }
    return Promise.reject(error);
});

axios.defaults.baseURL = process.env.VUE_APP_SERVER;
console.log('环境：', process.env.NODE_ENV);
console.log('服务端：', process.env.VUE_APP_SERVER);