import { createRouter, createWebHistory, RouteRecordRaw } from "vue-router";
import { routes } from "@/router/routes";
import { useLoginUserStore } from "@/store/userStore";
import { computed, ref } from "vue";
import API from "@/api";
import { getAccountDetailsPagesUsingPost } from "@/api/accountController";
import { message } from "ant-design-vue";

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
});


export default router;
