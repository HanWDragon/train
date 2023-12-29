<script setup>
import {onMounted, ref} from 'vue';
import axios from "axios";
import {notification} from "ant-design-vue";

const open = ref(false);
const onAdd = () => {
  passenger.value = {};
  open.value = true;
};

const onEdit = (record) => {
  passenger.value = window.Tool.copy(record);
  open.value = true;
}

const onDelete = (record) => {
  axios.delete('/member/passenger/delete/' + record.id).then(
      (response) => {
        const data = response.data;
        if (data.success) {
          notification.success({
            message: "删除成功"
          })
          getPassengerList({
            page: pagination.value.current,
            size: pagination.value.pageSize
          })
        } else {
          notification.error({
            message: data.message
          });
        }
      }
  );
}

const addPassenger = () => {
  axios.post('/member/passenger/save', passenger.value).then((resp) => {
    let data = resp.data;
    if (data.success) {
      notification.success({
        message: '保存成功'
      });
      getPassengerList({
        page: pagination.value.current,
        size: pagination.value.pageSize
      });
      open.value = false;
    } else {
      notification.error({
        message: data.message
      });
    }
  });
};

const passenger = ref({
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
  },
  {
    title: '操作',
    dataIndex: 'operation',
    key: 'operation',
  }
]);

// 分页的属性是固定的
const pagination = ref({
  total: 0,
  current: 1,
  pageSize: 2
})

// 防止用户多次点击
const loading = ref(false);

const PASSENGER_TYPE_ARRAY = [
  {
    key: '1',
    value: '成人'
  },
  {
    key:'2',
    value: '儿童'
  },
  {
    key: '3',
    value: '学生'
  },

]

onMounted(() => {
  getPassengerList({
    page: 1,
    size: pagination.value.pageSize
  });
});

const getPassengerList = (param) => {
  if (!param) {
    param = {
      page: 1,
      size: pagination.value.pageSize
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
      pagination.value.current = param.page;
      pagination.value.total = data.content.total;
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
        <a-button type="primary" @click="getPassengerList(null)">刷新</a-button>
        <a-button type="primary" @click="onAdd">增加乘车人信息</a-button>
      </a-space>
    </div>

    <a-modal v-model:open="open"
             title="乘车人信息"
             @ok="addPassenger"
             ok-text="确认"
             cancel-text="取消"
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
            <a-select-option v-for="item in PASSENGER_TYPE_ARRAY" :key="item.key" :value="item.key">
              {{ item.value }}
            </a-select-option>
          </a-select>
        </a-form-item>
      </a-form>
    </a-modal>
  </div>

  <a-table :dataSource="passengers"
           :columns="columns"
           :pagination="pagination"
           :loading="loading"
           @change="handleTableChange">

    <!--
      这里通过解构得到了对应的子组件列的信息和里面的数据信息，这样就指导点击的哪一列
      eg:

      column = {
        title: '身份证',
        dataIndex: 'idCard',
        key: 'idCard',
      }

      record = {
         "id": 1738430778796281856,
         "memberId": 1738366392887021568,
         "name": "test",
         "idCard": "123321",
         "type": "1",
         "createTime": "2023-12-23 13:26:10",
         "updateTime": "2023-12-23 13:26:10"
      }
    -->
    <template #bodyCell="{ column, record }">
      <template v-if="column.dataIndex === 'operation'">
        <a-space>
          <a @click="onEdit(record)">编辑</a>
          <a-divider type="vertical"/>
          <a-popconfirm
              title="删除后不可恢复，确认删除?"
              @confirm="onDelete(record)"
              ok-text="确认" cancel-text="取消">
            <a style="color: red">删除</a>
          </a-popconfirm>
        </a-space>
      </template>
    </template>
  </a-table>

</template>

<style scoped>
#button {
  padding-bottom: 20px;
}

</style>