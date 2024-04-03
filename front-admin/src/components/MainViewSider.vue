<template>
  <a-layout-sider width="200" style="background: #fff">
    <a-menu
        v-model:selectedKeys="selectedKeys"
        :open-keys="['base','batch']"
        mode="inline"
        :style="{ height: '100%', borderRight: 0 }"
    >
      <a-menu-item key="/welcome">
        <router-link to="/welcome">
          <CoffeeOutlined/>&nbsp; 欢迎
        </router-link>
      </a-menu-item>
      <a-sub-menu key="base">
        <template #title>
          <span>
            <UnorderedListOutlined/>
            基础数据
          </span>
        </template>
        <a-menu-item key="/base/station">
          <router-link to="/base/station">
            <MenuUnfoldOutlined/> &nbsp; 车站管理
          </router-link>
        </a-menu-item>
        <a-menu-item key="/base/train">
          <router-link to="/base/train">
            <MenuUnfoldOutlined/> &nbsp; 火车管理
          </router-link>
        </a-menu-item>
        <a-menu-item key="/base/train-station">
          <router-link to="/base/train-station">
            <MenuUnfoldOutlined/> &nbsp; 火车车站
          </router-link>
        </a-menu-item>
        <a-menu-item key="/base/train-carriage">
          <router-link to="/base/train-carriage">
            <MenuUnfoldOutlined/> &nbsp; 火车车厢
          </router-link>
        </a-menu-item>
        <a-menu-item key="/base/train-seat">
          <router-link to="/base/train-seat">
            <MenuUnfoldOutlined/> &nbsp; 火车座位
          </router-link>
        </a-menu-item>
      </a-sub-menu>
      <a-sub-menu key="batch">
        <template #title>
          <span>
            <UnorderedListOutlined/>
            跑批管理
          </span>
        </template>
        <a-menu-item key="/batch/job">
          <router-link to="/batch/job">
            <MenuUnfoldOutlined/> &nbsp; 任务管理
          </router-link>
        </a-menu-item>
      </a-sub-menu>
      <a-menu-item key="/about">
        <router-link to="/about">
          <UserOutlined/>&nbsp; 关于
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
<style>


</style>
