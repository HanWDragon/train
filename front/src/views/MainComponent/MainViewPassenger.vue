<script setup>
import {reactive, ref} from 'vue';
import axios from "axios";
import {notification} from "ant-design-vue";

const open = ref(false);
const showModal = () => {
  open.value = true;
};
const handleOk = () => {
  axios.post('/member/passenger/save', passenger).then((resp) => {
    let data = resp.data;
    if (data.success) {
      notification.success({
        message: '乘车人添加成功'
      });
      open.value = false;
    } else {
      notification.error({
        message: data.message
      });
    }
  });
};


const passenger = reactive({
  id: undefined,
  memberId: undefined,
  name: undefined,
  idCard: undefined,
  type: undefined,
  createTime: undefined,
  updateTime: undefined
});
const onFinish = values => {
  console.log('Success:', values);
};
const onFinishFailed = errorInfo => {
  console.log('Failed:', errorInfo);
};
</script>

<template>
  <a-button type="primary" @click="showModal">增加乘车人信息</a-button>
  <a-modal v-model:open="open" title="乘车人信息" @ok="handleOk"
           ok-text="确认" cancel-text="取消"
  >
    <a-form
        :model="passenger"
        name="basic"
        :label-col="{ span: 4 }"
        :wrapper-col="{ span: 8 }"
        autocomplete="off"
        @finish="onFinish"
        @finishFailed="onFinishFailed"
    >
      <a-form-item
          label="姓名："
          name="name"
          :rules="[{ required: true, message: '姓名不能为空!' }]"
      >
        <a-input v-model:value="passenger.name"/>
      </a-form-item>

      <a-form-item
          label="身份证："
          name="idCard"
          :rules="[{ required: true, message: '身份证不能为空!' }]"
      >
        <a-input v-model:value="passenger.idCard"/>
      </a-form-item>

      <a-form-item label="乘客类型："
                   :rules="[{ required: true, message: '乘客类型不能为空!' }]"
      >
        <a-select v-model:value="passenger.type" placeholder="请选择乘客类型">
          <a-select-option value="1">成人</a-select-option>
          <a-select-option value="2">儿童</a-select-option>
          <a-select-option value="3">学生</a-select-option>
        </a-select>
      </a-form-item>


    </a-form>
  </a-modal>
</template>

<style scoped>

</style>