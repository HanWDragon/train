import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import * as Icons from '@ant-design/icons-vue'

// 引入Ant Design
import Antd from 'ant-design-vue';
import 'ant-design-vue/dist/reset.css';

const app = createApp(App)
app.use(Antd).use(store).use(router).mount('#app')

// 全局使用图标
const icons = Icons
for (let iconsKey in icons) {
    app.component(iconsKey,icons[iconsKey])
}
