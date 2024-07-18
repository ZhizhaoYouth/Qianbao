<template>
  <a-form
    :model="formSearchParams"
    :style="{ marginBottom: '20px' }"
    layout="inline"
    @submit="doSearch"
  >
    <a-form-item field="transactionDesc" label="交易描述">
      <a-input
        v-model:value="formSearchParams.transactionDesc"
        placeholder="请输入交易描述"
        allow-clear
      />
    </a-form-item>
    <a-form-item field="category" label="财务类别">
      <a-select
        v-model:value="formSearchParams.category"
        placeholder="请选择财务类别"
        allow-clear
      >
        <a-select-option
          v-for="category in categories"
          :key="category.categoryName"
          :value="category.categoryName"
        >
          {{ category.categoryName }}
        </a-select-option>
      </a-select>
    </a-form-item>
    <a-form-item>
      <a-button type="primary" html-type="submit" style="width: 100px">
        搜索
      </a-button>
    </a-form-item>
  </a-form>
  <a-table
    :columns="columns"
    :data-source="dataList"
    size="small"
    :pagination="{
      pageSize: searchParams.pageSize,
      current: searchParams.current,
      total,
      onChange: onPageChange,
    }"
  >
    <template #transactionType="{ record }">
      {{ RECORD_TYPE_MAP[record.transactionType] }}
    </template>
    <template #transactionTime="{ record }">
      {{ dayjs(record.transactionTime).format("YYYY-MM-DD HH:mm:ss") }}
    </template>
    <template #createTime="{ record }">
      {{ dayjs(record.createTime).format("YYYY-MM-DD HH:mm:ss") }}
    </template>
    <template #updateTime="{ record }">
      {{ dayjs(record.updateTime).format("YYYY-MM-DD HH:mm:ss") }}
    </template>
    <template #optional="{ record }">
      <a-button danger @click="doDelete(record)">删除</a-button>
    </template>
  </a-table>
</template>

<script setup lang="ts">
import { defineProps, ref, watchEffect, withDefaults } from "vue";
import API from "@/api";
import {
  ACCOUNT_TYPE_MAP,
  RECORD_TYPE_MAP,
  REVIEW_STATUS_ENUM,
  REVIEW_STATUS_MAP,
} from "@/constant/account";
import { message } from "ant-design-vue";
import dayjs from "dayjs";
import {
  deleteFinancialRecordUsingPost,
  listMyFinancialRecordVoByPageUsingPost,
} from "@/api/financialRecordController";
import { listMyBudgetCategoryVoByPageUsingPost } from "@/api/budgetCategoryController";

const formSearchParams = ref<API.FinancialRecordQueryRequest>({});
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
  accountId: props.accountId,
};

const searchParams = ref<API.FinancialRecordQueryRequest>({
  ...initSearchParams,
});
const dataList = ref<API.FinancialRecordVO[]>([]);
const total = ref<number>(0);
const categoryRequest = ref<API.BudgetCategoryQueryRequest>({});

/**
 * 加载财务类别
 */
const loadCategories = async () => {
  const res = await listMyBudgetCategoryVoByPageUsingPost(categoryRequest); // 获取财务类别
  if (res.data.code === 0) {
    categories.value = res.data.data?.records || [];
  } else {
    message.error("获取财务类别失败，" + res.data.message);
  }
};

/**
 * 加载数据
 */
const loadData = async () => {
  const res = await listMyFinancialRecordVoByPageUsingPost(searchParams.value);
  if (res.data.code === 0) {
    dataList.value = res.data.data?.records || [];
    total.value = res.data.data?.total || 0;
  } else {
    message.error("获取数据失败，" + res.data.message);
  }
};

/**
 * 执行搜索
 */
const doSearch = () => {
  searchParams.value = {
    ...initSearchParams,
    ...formSearchParams.value,
  };
};

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
const doDelete = async (record: API.FinancialRecord) => {
  if (!record.id) {
    return;
  }

  const res = await deleteFinancialRecordUsingPost({
    id: record.id,
  });
  if (res.data.code === 0) {
    loadData();
  } else {
    message.error("删除失败，" + res.data.message);
  }
};

/**
 * 监听 searchParams 变量，改变时触发数据的重新加载
 */
watchEffect(() => {
  loadData();
});
loadCategories();

// 表格列配置
const columns = [
  {
    title: "id",
    dataIndex: "id",
  },
  {
    title: "交易描述",
    dataIndex: "transactionDesc",
  },
  {
    title: "账户id",
    dataIndex: "accountId",
  },
  {
    title: "金额",
    dataIndex: "amount",
  },
  {
    title: "交易类型",
    dataIndex: "transactionType",
    slots: { customRender: "transactionType" },
  },
  {
    title: "财务类别",
    dataIndex: "category",
  },
  {
    title: "交易时间",
    dataIndex: "transactionTime",
    slots: { customRender: "transactionTime" },
  },
  {
    title: "创建时间",
    dataIndex: "createTime",
    slots: { customRender: "createTime" },
  },
  {
    title: "更新时间",
    dataIndex: "updateTime",
    slots: { customRender: "updateTime" },
  },
  {
    title: "操作",
    slots: { customRender: "optional" },
  },
];
</script>
