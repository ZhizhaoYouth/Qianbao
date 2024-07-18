import {
  NavigationGuardNext,
  RouteLocationNormalized,
  RouteRecordRaw,
} from "vue-router";
import HomePage from "@/views/HomePage.vue";
import UserLayout from "@/layouts/UserLayout.vue";
import UserLoginPage from "@/views/user/UserLoginPage.vue";
import UserRegisterPage from "@/views/user/UserRegisterPage.vue";
import ACCESS_ENUM from "@/access/accessEnum";
import AdminUserPage from "@/views/admin/AdminUserPage.vue";
import AdminAccountPage from "@/views/admin/AdminAccountPage.vue";
import AddAccountPage from "@/views/add/AddAccountPage.vue";
import AccountDetailPage from "@/views/account/AccountDetailPage.vue";
import { useLoginUserStore } from "@/store/userStore";
import {
  getAccountDetailsPagesUsingPost,
  getAccountVoByIdUsingGet,
} from "@/api/accountController";
import { message } from "ant-design-vue";
import { computed, ref } from "vue";
import API from "@/api";
import NoAuth from "@/noAuth.vue";
import UserFinancialRecordPage from "@/views/financialrecord/UserFinancialRecordPage.vue";
import UserBudgetPage from "@/views/budget/UserBudgetPage.vue";
import AdminLoginLogPage from "@/views/admin/AdminLoginLogPage.vue";
import UserLoginLogPage from "@/views/loginlog/UserLoginLogPage.vue";
import AddFinancialRecordPage from "@/views/add/AddFinancialRecordPage.vue";
import AddBudgetPage from "@/views/add/AddBudgetPage.vue";
import UserCategoryPage from "@/views/category/UserCategoryPage.vue";
import { getBudgetVoByIdUsingGet } from "@/api/budgetController";
import EditBudgetPage from "@/views/budget/EditBudgetPage.vue";
import GenerateReportPage from "@/views/report/GenerateReportPage.vue";

const requireAuth = async (
  to: RouteLocationNormalized,
  from: RouteLocationNormalized,
  next: NavigationGuardNext
) => {
  const loginUserStore = useLoginUserStore();
  const userId = loginUserStore.loginUser?.id;
  const data = ref<API.AccountDetailsVO>({});
  if (!userId) {
    // 用户未登录，跳转到无权限页面
    next({ path: "/noAuth" });
  } else {
    // 检查用户是否有权限访问该账户
    const accountId = to.params.id;
    const res = await getAccountDetailsPagesUsingPost({
      id: to.params.id as any,
    });
    if (res.data.code === 0) {
      data.value = res.data.data as any;
    } else {
      message.error("获取数据失败，" + res.data.message);
    }
    const isMy = computed(() => {
      return userId && userId === data.value.userId;
    });

    if (isMy.value) {
      next(); // 有权限，继续访问
    } else {
      next({ path: "/noAuth" }); // 无权限，跳转到无权限页面
    }
  }
};

const EditBudgetForAccountRequireAuth = async (
  to: RouteLocationNormalized,
  from: RouteLocationNormalized,
  next: NavigationGuardNext
) => {
  const loginUserStore = useLoginUserStore();
  const userId = loginUserStore.loginUser?.id;
  const data = ref<API.AccountDetailsVO>({});
  if (!userId) {
    // 用户未登录，跳转到无权限页面
    next({ path: "/noAuth" });
  } else {
    // 检查用户是否有权限访问该账户
    const budgetId = to.params.id;
    const res = await getBudgetVoByIdUsingGet({
      id: to.params.id as any,
    });
    if (res.data.code === 0) {
      data.value = res.data.data as any;
    } else {
      message.error("获取数据失败，" + res.data.message);
    }
    const isMy = computed(() => {
      return userId && userId === data.value.userId;
    });

    if (isMy.value) {
      next(); // 有权限，继续访问
    } else {
      next({ path: "/noAuth" }); // 无权限，跳转到无权限页面
    }
  }
};

const AddForAccountRequireAuth = async (
  to: RouteLocationNormalized,
  from: RouteLocationNormalized,
  next: NavigationGuardNext
) => {
  const loginUserStore = useLoginUserStore();
  const userId = loginUserStore.loginUser?.id;
  const data = ref<API.AccountDetailsVO>({});
  if (!userId) {
    // 用户未登录，跳转到无权限页面
    next({ path: "/noAuth" });
  } else {
    // 检查用户是否有权限访问该账户
    const accountId = to.params.accountId;
    const res = await getAccountVoByIdUsingGet({
      id: accountId as any,
    });
    if (res.data.code === 0) {
      data.value = res.data.data as any;
    } else {
      message.error("获取数据失败，" + res.data.message);
    }
    const isMy = computed(() => {
      return userId && userId === data.value.userId;
    });

    if (isMy.value) {
      next(); // 有权限，继续访问
    } else {
      next({ path: "/noAuth" }); // 无权限，跳转到无权限页面
    }
  }
};

export const routes: Array<RouteRecordRaw> = [
  {
    path: "/",
    name: "我的账户",
    component: HomePage,
  },
  {
    path: "/noAuth",
    name: "无权限页面",
    component: NoAuth,
    meta: {
      hideInMenu: true,
    },
  },
  {
    path: "/add/account",
    name: "创建账户",
    component: AddAccountPage,
    meta: {
      hideInMenu: true,
    },
  },
  {
    path: "/account/:accountId/add-record",
    name: "创建财务记录",
    props: true,
    component: AddFinancialRecordPage,
    meta: {
      hideInMenu: true,
    },
    beforeEnter: AddForAccountRequireAuth,
  },
  {
    path: "/account/:accountId/add-budget",
    name: "创建预算",
    props: true,
    component: AddBudgetPage,
    meta: {
      hideInMenu: true,
    },
    beforeEnter: AddForAccountRequireAuth,
  },
  {
    path: "/edit/budget/:id",
    name: "修改预算",
    props: true,
    component: EditBudgetPage,
    meta: {
      hideInMenu: true,
    },
    beforeEnter: EditBudgetForAccountRequireAuth,
  },
  {
    path: "/account/detail/:id",
    name: "应用详情页",
    props: true,
    component: AccountDetailPage,
    meta: {
      hideInMenu: true,
    },
    beforeEnter: requireAuth,
  },
  {
    path: "/record/list",
    name: "我的财务记录",
    component: UserFinancialRecordPage,
  },
  {
    path: "/budget/list",
    name: "我的预算",
    component: UserBudgetPage,
  },
  {
    path: "/category/list",
    name: "自定义财务类别",
    component: UserCategoryPage,
  },
  {
    path: "/report",
    name: "生成报表",
    component: GenerateReportPage,
  },
  {
    path: "/loginlog/list",
    name: "登录日志",
    component: UserLoginLogPage,
  },
  {
    path: "/admin/user",
    name: "用户管理",
    component: AdminUserPage,
    meta: {
      access: ACCESS_ENUM.ADMIN,
    },
  },
  {
    path: "/admin/account",
    name: "账户管理",
    component: AdminAccountPage,
    meta: {
      access: ACCESS_ENUM.ADMIN,
    },
  },
  {
    path: "/admin/loginlog",
    name: "登录日志管理",
    component: AdminLoginLogPage,
    meta: {
      access: ACCESS_ENUM.ADMIN,
    },
  },
  {
    path: "/user",
    name: "用户",
    component: UserLayout,
    children: [
      {
        path: "/user/login",
        name: "登录",
        component: UserLoginPage,
      },
      {
        path: "/user/register",
        name: "注册",
        component: UserRegisterPage,
      },
    ],
    meta: {
      hideInMenu: true,
    },
  },
];
