<script setup>
import {onMounted, reactive, ref} from 'vue';
import axios from "axios";
import {notification} from "ant-design-vue";

const open = ref(false);
const showModal = () => {
  open.value = true;
};
const addPassenger = () => {
  axios.post('/member/passenger/save', passenger).then((resp) => {
    let data = resp.data;
    if (data.success) {
      notification.success({
        message: '乘车人添加成功'
      });
      getPassengerList({
        page: pagination.current,
        size: pagination.pageSize
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

// 分页的属性是固定的
const pagination = reactive({
  total: 0,
  current: 1,
  pageSize: 2
})

// 防止用户多次点击
const loading = ref(false);

onMounted(() => {
  getPassengerList({
    page: 1,
    size: pagination.pageSize
  });
});

const getPassengerList = (param) => {
  if (!param) {
    param = {
      page: 1,
      size: pagination.pageSize
    };
  }
  loading.value = true;
  // axios get请求的参数放在params里
  axios.get('/member/passenger/query-list', {
    params: {
      page: param.page,
      size: param.size
    }
  }).then((response) => {
    loading.value = false;
    let data = response.data;
    if (data.success) {
      // ... 用于展开数组和对象 eg: list.push(...data.content.list);
      passengers.value = data.content.list;
      // 设置分页控件的值
      pagination.current = param.page;
      pagination.total = data.content.total;
    } else {
      notification.error({
        message: data.message
      });
    }
  });
};

const handleTableChange = (pagination) => {
  getPassengerList({
    page: pagination.current,
    size: pagination.pageSize
  });
};

</script>

<template>
  <!--  防止表格和按钮重合-->
  <div>
    <div id="button">
      <!--      防止组件贴在一起-->
      <a-space>
        <a-button type="primary" @click="showModal">增加乘车人信息</a-button>
        <a-button type="primary" @click="getPassengerList(null)">刷新</a-button>
      </a-space>
    </div>

    <a-modal v-model:open="open" title="乘车人信息" @ok="addPassenger"
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
  </div>

  <a-table :dataSource="passengers"
           :columns="columns"
           :pagination="pagination"
           :loading="loading"
           @change="handleTableChange"/>

</template>

<style scoped>
#button {
  padding-bottom: 20px;
}

</style>