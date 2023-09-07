<template>

  <a-row class="login">
    <a-col :span="8" :offset="8" class="login-main">
      <h1 style="text-align: center">
        <rocket-two-tone/>
        仿12306火车票售票系统
      </h1>
      <a-form
          :model="loginForm"
          name="basic"
          autocomplete="off"
      >
        <a-form-item
            label=""
            name="mobile"
            :rules="[{ required: true, message: '请输入手机号!' }]"
        >
          <a-input v-model:value="loginForm.mobile" placeholder="手机号"/>
        </a-form-item>

        <a-form-item
            label=""
            name="code"
            :rules="[{ required: true, message: '请输入验证码!' }]"
        >
          <a-input v-model:value="loginForm.code">
            <template #addonAfter>
              <a @click="sendCode">获取验证码</a>
            </template>
          </a-input>
          <!--<a-input v-model:value="loginForm.code" placeholder="验证码"/>-->
        </a-form-item>

        <a-form-item>
          <a-button type="primary" block @click="login">登录</a-button>
        </a-form-item>

      </a-form>
    </a-col>
  </a-row>

</template>

<script>
import {defineComponent, reactive} from 'vue';
import axios from "axios";
import {notification} from "ant-design-vue";

export default defineComponent({
  name: "LoginView",
  setup() {
    const loginForm = reactive({
      mobile: '13995653850',
      code: '',
    });

    const sendCode = () => {
      axios.post("/member/member/send_code", {
        mobile: loginForm.mobile
      }).then(response => {
        let data = response.data;
        if (data.success) {
          notification.success({
            message: '发送验证码成功',
          });
          loginForm.code = '8888'
        } else {
          notification.error({
            message: data.message
          });
        }
      });
    };

    const login = () => {
      axios.post("/member/member/login", loginForm).then((response) => {
        let data = response.data;
        if (data.success) {
          notification.success({
            message: '登录成功😊'
          });
        } else {
          notification.error({
            message: data.message
          });
        }
      })
    };


    return {
      loginForm,
      login,
      sendCode
    };
  },
});
</script>

<style>
.login-main h1 {
  font-size: 25px;
  font-weight: bold;
}

.login-main {
  margin-top: 300px;
  padding: 60px 60px 60px;
  border: 2px solid grey;
  border-radius: 10px;
  background-color: #fcfcfc;
}

</style>
