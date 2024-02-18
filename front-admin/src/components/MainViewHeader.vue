<template xmlns:color="http://www.w3.org/1999/xhtml">
  <a-layout-header class="header">
    <div class="logo"/>
    <div style="float: right; color: white">
      你好欢迎使用管理员页面
      &nbsp;&nbsp;&nbsp;&nbsp;
    </div>
    <a-menu
        v-model:selectedKeys="selectedKeys"
        theme="dark"
        mode="horizontal"
        :style="{ lineHeight: '64px' }"
    >
      <a-menu-item key="/welcome">
        <router-link to="/welcome">
          <CoffeeOutlined/>&nbsp; 欢迎
        </router-link>
      </a-menu-item>
      <a-menu-item key="/about">
        <router-link to="/about">
          <UserOutlined/>&nbsp; 关于
        </router-link>
      </a-menu-item>
      <a-menu-item key="3">nav 3</a-menu-item>
    </a-menu>
  </a-layout-header>
</template>

<script>
import {defineComponent, ref, watch} from 'vue';
import store from "@/store";
import router from "@/router";

export default defineComponent({
  name: "MainViewHeader",
  computed: {
    store() {
      return store
    }
  },
  setup() {
    const selectedKeys = ref(['/welcome']);

    watch(() => router.currentRoute.value.path, (newValue) => {
          console.log('watch', newValue);
          selectedKeys.value = [];
          selectedKeys.value.push(newValue);
        },
        // 这个是组件创建完成是否后立即执行
        {immediate: false})
    return {
      selectedKeys,
    };
  },
});
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.logo {
  float: left;
  height: 31px;
  width: 150px;
  color: white;
  font-size: 20px;
}
</style>
