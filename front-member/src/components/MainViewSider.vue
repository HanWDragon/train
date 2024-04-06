<template>
  <a-layout-sider width="200" style="background: #fff">
    <a-menu
        v-model:selectedKeys="selectedKeys"
        mode="inline"
        :style="{ height: '100%', borderRight: 0 }"
    >
      <a-menu-item key="/welcome">
        <router-link to="/welcome">
          <CoffeeOutlined/>&nbsp; 欢迎
        </router-link>
      </a-menu-item>
      <a-menu-item key="/passenger">
        <router-link to="/passenger">
          <UserOutlined/>&nbsp; 乘车人管理
        </router-link>
      </a-menu-item>
      <a-menu-item key="/ticket">
        <router-link to="/ticket">
          <UserOutlined/>&nbsp; 余票查询
        </router-link>
      </a-menu-item>
      <a-menu-item key="/my-ticket">
        <router-link to="/my-ticket">
          <UserOutlined/>&nbsp; 我的车票
        </router-link>
      </a-menu-item>


    </a-menu>
  </a-layout-sider>
</template>

<script>
import {defineComponent, ref, watch} from 'vue';
import router from "@/router";

export default defineComponent({
  name: "MainViewSider",
  setup() {
    const selectedKeys = ref(['/welcome']);

    watch(() => router.currentRoute.value.path ,(newValue)=> {
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
<style>


</style>
