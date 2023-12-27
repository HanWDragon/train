<script setup>
import {onMounted, reactive, ref} from 'vue';
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

// 这里使用ref是因为使用reactive修改内部内容会导致失去响应式
// {
//   "success": true,
//   "message": null,
//   "content": {
//   "total": 7,
//       "list": [
//     {
//       "id": 1738430778796281856,
//       "memberId": 1738366392887021568,
//       "name": "test",
//       "idCard": "123321",
//       "type": "1",
//       "createTime": "2023-12-23 13:26:10",
//       "updateTime": "2023-12-23 13:26:10"
//     },
//     {
//       "id": 1738430800451473408,
//       "memberId": 1738366392887021568,
//       "name": "test",
//       "idCard": "123321",
//       "type": "1",
//       "createTime": "2023-12-23 13:26:15",
//       "updateTime": "2023-12-23 13:26:15"
//     }
//   ]
//  }
// }
let passengers = ref([]);
const columns = ref([
  {
    title: '姓名',
    dataIndex: 'name',
    key: 'name',
  },
  {
    title: '身份证',
    dataIndex: 'idCard',
    key: 'idCard',
  },
  {
    title: '购票人类型',
    dataIndex: 'type',
    key: 'type',
  }
]);

onMounted(() => {
  getPassengerList({
    page: 1,
    size: 10
  });
});

const getPassengerList = (param) => {
  // axios get请求的参数放在params里
  axios.get('/member/passenger/query-list', {
    params: {
      page: param.page,
      size: param.size
    }
  }).then((response) => {
    let data = response.data;
    if (data.success) {
      // ... 用于展开数组和对象
      passengers.value = data.content.list;
    } else {
      notification.error({
        message: data.message
      });
    }
  });
};

</script>

<template>
  <!--  防止表格和按钮重合-->
  <p>
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
  </p>

  <a-table :dataSource="passengers" :columns="columns"/>

</template>

<style scoped>

</style>