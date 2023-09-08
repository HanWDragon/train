<template xmlns:color="http://www.w3.org/1999/xhtml">
  <a-layout-header class="header">
    <div class="logo"/>
    <div style="float: right; color: white">
      你好：{{ member.mobile }}
      &nbsp;&nbsp;&nbsp;&nbsp;
      <router-link to="/login" style="color: deepskyblue">
        退出登录
      </router-link>
    </div>
    <a-menu
        v-model:selectedKeys="selectedKeys"
        theme="dark"
        mode="horizontal"
        :style="{ lineHeight: '64px' }"
    >
      <a-menu-item key="1">nav 1</a-menu-item>
      <a-menu-item key="2">nav 2</a-menu-item>
      <a-menu-item key="3">nav 3</a-menu-item>
    </a-menu>
  </a-layout-header>
</template>

<script>
import {defineComponent, ref} from 'vue';
import store from "@/store";

export default defineComponent({
  name: "MainViewHeader",
  setup() {
    const selectedKeys = ref(['2']);
    let member = store.state.member;
    return {
      selectedKeys,
      member
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
