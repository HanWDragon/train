<script>
import {defineComponent} from 'vue'

export default defineComponent({
  name: 'OrderView',
  setup() {
    const dailyTrainTicket = SessionStorage.get("dailyTrainTicket") || {};
    console.log('下单的次车次信息', dailyTrainTicket);

    return {
      dailyTrainTicket,
    };
  }
})
</script>

<template>
  <div>{{ dailyTrainTicket }}</div>
</template>

<style scoped>

</style>