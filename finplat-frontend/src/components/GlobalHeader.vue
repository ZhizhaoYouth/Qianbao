<template>
  <a-row id="globalHeader" align="center" :wrap="false">
    <a-col flex="auto">
      <a-menu
        mode="horizontal"
        :selectedKeys="selectedKeys"
        @click="doMenuClick"
      >
        <a-menu-item
          key="0"
          :style="{ padding: 0, marginRight: '38px' }"
          disabled
        >
          <div class="titleBar">
            <img class="logo" src="../assets/logo.png" />
            <div class="title">钱宝</div>
          </div>
        </a-menu-item>
        <a-menu-item v-for="item in visibleRoutes" :key="item.path"
          >{{ item.name }}
        </a-menu-item>
      </a-menu>
    </a-col>
    <a-col flex="180px">
      <div v-if="loginUserStore.loginUser.id">
        <a-image height="48px" :src="avatarSrc" style="padding-right: 10px" />
        {{ loginUserStore.loginUser.userName ?? "无名" }}
        <a-button
          type="primary"
          @click="handleLogout"
          size="middle"
          style="margin-left: 10px"
          >退出
        </a-button>
      </div>
      <div v-else>
        <a-button type="primary" href="/user/login">登录</a-button>
      </div>
    </a-col>
  </a-row>
</template>

<script setup lang="ts">
import { routes } from "@/router/routes";
import { useRouter } from "vue-router";
import { computed, ref } from "vue";
import { useLoginUserStore } from "@/store/userStore";
import checkAccess from "@/access/checkAccess";
import { getFinancialRecordVoByIdUsingGet } from "@/api/financialRecordController";
import { userLogoutUsingPost } from "@/api/userController";
import { message } from "ant-design-vue";

const loginUserStore = useLoginUserStore();

const router = useRouter();
//当前选中的菜单项
const selectedKeys = ref(["/"]);

// 默认头像路径
const defaultAvatar = require("@/assets/defaultAvatar.png");

// 计算属性，用于决定使用哪个头像 URL
const avatarSrc = computed(() => {
  return loginUserStore.loginUser.userAvatar
    ? loginUserStore.loginUser.userAvatar
    : defaultAvatar;
});

//路由跳转时自动更新选中菜单项
router.afterEach((to, from, failure) => {
  selectedKeys.value = [to.path];
});
//展示在菜单中的路由数组
const visibleRoutes = computed(() => {
  return routes.filter((item) => {
    if (item.meta?.hideInMenu) {
      return false;
    }
    // 根据权限过滤菜单
    if (!checkAccess(loginUserStore.loginUser, item.meta?.access as string)) {
      return false;
    }
    return true;
  });
});

//点击菜单跳转到对应页面
const doMenuClick = (item) => {
  router.push({
    path: item.key,
  });
};

const handleLogout = async () => {
  const res = await userLogoutUsingPost({});
  await loginUserStore.fetchLoginUser();
  if (res.data.code === 0) {
    message.success("退出登录成功");
    //跳转到登录页面
    router.push("/user/login");
  } else {
    message.error("退出登录失败");
  }
};
</script>

<style scoped>
#globalHeader {
}

.titleBar {
  display: flex;
  align-items: center;
}

.title {
  margin-left: 16px;
  color: black;
  font-weight: 600;
  font-size: 15px;
}

.logo {
  height: 48px;
}
</style>
