<template>
  <a-layout id="components-layout-demo-top-side-2">
    <MainViewHeader></MainViewHeader>
    <a-layout>
      <MainViewSider></MainViewSider>
        <a-layout-content
            :style="{ background: '#fff', padding: '24px', margin: 0, minHeight: '280px' }"
        >
          <router-view></router-view>
        </a-layout-content>
    </a-layout>
  </a-layout>
</template>
<script setup>
import MainViewHeader from "@/components/MainViewHeader.vue";
import MainViewSider from "@/components/MainViewSider.vue";

</script>

<style>

</style>