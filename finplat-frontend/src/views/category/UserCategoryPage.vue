<template>
  <h2 style="margin-bottom: 20px">添加自定义类别：</h2>
  <a-form
    :model="form"
    :style="{ marginBottom: '20px' }"
    auto-label-width
    layout="inline"
    @submit="handleSubmit"
  >
    <a-form-item field="budgetDesc" label="自定义类别名称">
      <a-input
        v-model:value="form.categoryName"
        placeholder="请输入自定义类别名称"
      />
    </a-form-item>
    <a-form-item v-if="isAdmin" field="isDefault" label="是否系统默认">
      <a-select
        v-model:value="form.isDefault"
        placeholder="请设置是否为系统默认类别"
      >
        <a-select-option value="0">自定义类别</a-select-option>
        <a-select-option value="1">系统默认类别</a-select-option>
      </a-select>
    </a-form-item>
    <a-form-item>
      <a-button type="primary" html-type="submit" :style="{ width: '140px' }">
        添加自定义类别
      </a-button>
    </a-form-item>
  </a-form>
  <h2>可用类别列表：</h2>
  <a-table
    :columns="columns"
    :data-source="categories"
    size="small"
    :pagination="{
      pageSize: searchParams.pageSize,
      current: searchParams.current,
      total,
      onChange: onPageChange,
    }"
  >
    <template #isDefault="{ record }">
      {{ IS_DEFAULT_MAP[record.isDefault] }}
    </template>
    <template #createTime="{ record }">
      {{ dayjs(record.createTime).format("YYYY-MM-DD HH:mm:ss") }}
    </template>
    <template #optional="{ record }">
      <a-button
        v-if="isAdmin || record.isDefault === 0"
        danger
        @click="doDelete(record)"
        >删除
      </a-button>
    </template>
  </a-table>
</template>

<script setup lang="ts">
import { computed, defineProps, ref, watchEffect, withDefaults } from "vue";
import API from "@/api";
import { IS_DEFAULT_MAP } from "@/constant/account";
import { message } from "ant-design-vue";
import dayjs from "dayjs";
import {
  addBudgetCategoryUsingPost,
  deleteBudgetCategoryUsingPost,
  listMyBudgetCategoryVoByPageUsingPost,
} from "@/api/budgetCategoryController";
import { useLoginUserStore } from "@/store/userStore";
import router from "@/router";

const categories = ref<API.BudgetCategoryVO[]>([]); // 存储财务类别

interface Props {
  accountId: string;
}

const props = withDefaults(defineProps<Props>(), {
  accountId: () => {
    return null;
  },
});

// 初始化搜索条件（不应该被修改）
const initSearchParams = {
  current: 1,
  pageSize: 10,
};

const form = ref({
  categoryName: "",
} as API.BudgetCategoryAddRequest);

const searchParams = ref<API.BudgetQueryRequest>({
  ...initSearchParams,
});
const total = ref<number>(0);

/**
 * 加载财务类别
 */
const loadCategories = async () => {
  const res = await listMyBudgetCategoryVoByPageUsingPost({}); // 获取财务类别
  if (res.data.code === 0) {
    categories.value = res.data.data?.records || [];
  } else {
    message.error("获取财务类别失败，" + res.data.message);
  }
};

// 获取登录用户
const loginUserStore = useLoginUserStore();
let loginUserId = loginUserStore.loginUser?.id;
// 是否为本人创建
const isAdmin = computed(() => {
  return loginUserId && loginUserStore.loginUser.userRole === "admin";
});

/**
 * 当分页变化时，改变搜索条件，触发数据加载
 * @param page
 */
const onPageChange = (page: number) => {
  searchParams.value = {
    ...searchParams.value,
    current: page,
  };
};

/**
 * 删除
 * @param record
 */
const doDelete = async (record: API.Budget) => {
  if (!record.id) {
    return;
  }

  const res = await deleteBudgetCategoryUsingPost({
    id: record.id,
  });
  if (res.data.code === 0) {
    loadCategories();
  } else {
    message.error("删除失败，" + res.data.message);
  }
};

/**
 * 监听 searchParams 变量，改变时触发数据的重新加载
 */
watchEffect(() => {
  loadCategories();
});

/**
 * 提交
 */
const handleSubmit = async () => {
  let res: any;
  // 创建
  res = await addBudgetCategoryUsingPost(form.value);
  if (res.data.code === 0) {
    message.success("创建成功");
    loadCategories();
  } else {
    message.error("操作失败，" + res.data.message);
  }
};

// 表格列配置
const columns = [
  {
    title: "类别名称",
    dataIndex: "categoryName",
  },
  {
    title: "是否为系统默认",
    dataIndex: "isDefault",
    slots: { customRender: "isDefault" },
  },
  {
    title: "创建时间",
    dataIndex: "createTime",
    slots: { customRender: "createTime" },
  },
  {
    title: "操作",
    slots: { customRender: "optional" },
  },
];
</script>
