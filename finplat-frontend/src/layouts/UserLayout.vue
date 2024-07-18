<template>
  <div id="userLayout">
    <a-layout :style="{ height: '100vh', background: 'white' }">
      <a-layout-header class="header">
        <a-space style="align-items: center">
          <img class="logo" src="../assets/logo.png" />
          <div style="font-size: 16px">钱宝个人财务管理系统</div>
        </a-space>
      </a-layout-header>
      <a-layout-content class="content">
        <router-view />
      </a-layout-content>
      <a-layout-footer class="footer">
        欢迎使用钱宝财务管理系统！
      </a-layout-footer>
    </a-layout>
  </div>
</template>

<script setup lang="ts"></script>

<style scoped>
#userLayout {
  text-align: center;
  background: url("https://gw.alipayobjects.com/zos/rmsportal/FfdJeJRQWjEeGTpqgBKj.png")
    0% 0% / 100% 100%;
}

#userLayout .logo {
  width: 48px;
  height: 48px;
}

#userLayout .content {
  margin-bottom: 28px;
  padding: 20px;
  background: linear-gradient(to right, #fefefe, #fff);
}

#userLayout .header {
  margin-top: 10px;
  margin-bottom: 16px;
  font-weight: bold;
  font-size: large;
  background: white;
  box-shadow: #eee 5px 1px 5px;
}

#userLayout .footer {
  padding: 16px;
  text-align: center;
  background: #efefef;
}
</style>
