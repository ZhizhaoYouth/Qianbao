<template>
  <div id="basicLayout">
    <a-layout style="background: white">
      <a-layout-header class="header">
        <GlobalHeader />
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

<script setup lang="ts">
import GlobalHeader from "@/components/GlobalHeader.vue";
</script>

<style scoped>
#basicLayout {
}

#basicLayout .header {
  margin-bottom: 16px;
  background: white;
  box-shadow: #eee 5px 1px 5px;
}

#basicLayout .content {
  box-sizing: border-box;
  width: 100%;
  max-width: 1300px;
  margin: 0 auto 28px;
  padding: 20px;
  background: linear-gradient(to bottom, #f5f5f5, #fff);
}

#basicLayout .footer {
  position: fixed;
  right: 0;
  bottom: 0;
  left: 0;
  padding: 16px;
  text-align: center;
  background: #efefef;
}
</style>
