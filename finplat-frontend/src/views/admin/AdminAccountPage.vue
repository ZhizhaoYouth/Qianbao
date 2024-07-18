<template>
  <a-form
    :model="formSearchParams"
    :style="{ marginBottom: '20px' }"
    layout="inline"
    @submit="doSearch"
  >
    <a-form-item field="accountName" label="账户名称">
      <a-input
        v-model:value="formSearchParams.accountName"
        placeholder="请输入应用名称"
        allow-clear
      />
    </a-form-item>
    <a-form-item field="bankName" label="银行名称">
      <a-input
        v-model:value="formSearchParams.bankName"
        placeholder="请输入应用描述"
        allow-clear
      />
    </a-form-item>
    <a-form-item label="审核状态">
      <a-select
        v-model:value="formSearchParams.reviewStatus"
        placeholder="请选择审核状态"
        allow-clear
      >
        <a-select-option value="0">未审核</a-select-option>
        <a-select-option value="1">已过审</a-select-option>
        <a-select-option value="2">未过审</a-select-option>
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
    <template #accountType="{ record }">
      {{ ACCOUNT_TYPE_MAP[record.accountType] }}
    </template>
    <template #reviewStatus="{ record }">
      {{ REVIEW_STATUS_MAP[record.reviewStatus] }}
    </template>
    <template #reviewTime="{ record }">
      {{
        record.reviewTime &&
        dayjs(record.reviewTime).format("YYYY-MM-DD HH:mm:ss")
      }}
    </template>
    <template #createTime="{ record }">
      {{ dayjs(record.createTime).format("YYYY-MM-DD HH:mm:ss") }}
    </template>
    <template #updateTime="{ record }">
      {{ dayjs(record.updateTime).format("YYYY-MM-DD HH:mm:ss") }}
    </template>
    <template #optional="{ record }">
      <a-space>
        <a-button
          v-if="record.reviewStatus !== REVIEW_STATUS_ENUM.PASS"
          style="background: #42b983"
          @click="doReview(record, REVIEW_STATUS_ENUM.PASS, '审核通过')"
        >
          通过
        </a-button>
        <a-button
          v-if="record.reviewStatus !== REVIEW_STATUS_ENUM.REJECT"
          style="background: #ffa850"
          @click="doReview(record, REVIEW_STATUS_ENUM.REJECT, '不符合上架要求')"
        >
          拒绝
        </a-button>
        <a-button danger @click="doDelete(record)">删除</a-button>
      </a-space>
    </template>
  </a-table>
</template>

<script setup lang="ts">
import { ref, watchEffect } from "vue";
import {
  deleteAccountUsingPost,
  doAccountReviewUsingPost,
  listAccountByPageUsingPost,
} from "@/api/accountController";
import API from "@/api";
import {
  ACCOUNT_TYPE_MAP,
  REVIEW_STATUS_ENUM,
  REVIEW_STATUS_MAP,
} from "@/constant/account";
import { message } from "ant-design-vue";
import dayjs from "dayjs";

const formSearchParams = ref<API.AccountQueryRequest>({});

// 初始化搜索条件（不应该被修改）
const initSearchParams = {
  current: 1,
  pageSize: 10,
};

const searchParams = ref<API.AccountQueryRequest>({
  ...initSearchParams,
});
const dataList = ref<API.Account[]>([]);
const total = ref<number>(0);

/**
 * 加载数据
 */
const loadData = async () => {
  const res = await listAccountByPageUsingPost(searchParams.value);
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
const doDelete = async (record: API.Account) => {
  if (!record.id) {
    return;
  }

  const res = await deleteAccountUsingPost({
    id: record.id,
  });
  if (res.data.code === 0) {
    loadData();
  } else {
    message.error("删除失败，" + res.data.message);
  }
};

/**
 * 审核
 * @param record
 * @param reviewStatus
 * @param reviewMessage
 */
const doReview = async (
  record: API.Account,
  reviewStatus: number,
  reviewMessage?: string
) => {
  if (!record.id) {
    return;
  }

  const res = await doAccountReviewUsingPost({
    id: record.id,
    reviewStatus,
    reviewMessage,
  });
  if (res.data.code === 0) {
    loadData();
  } else {
    message.error("审核失败，" + res.data.message);
  }
};

/**
 * 监听 searchParams 变量，改变时触发数据的重新加载
 */
watchEffect(() => {
  loadData();
});

// 表格列配置
const columns = [
  {
    title: "id",
    dataIndex: "id",
  },
  {
    title: "账户名称",
    dataIndex: "accountName",
  },
  {
    title: "账户号码",
    dataIndex: "accountNumber",
  },
  {
    title: "银行名称",
    dataIndex: "bankName",
  },
  {
    title: "账户类型",
    dataIndex: "accountType",
    slots: { customRender: "accountType" },
  },
  {
    title: "余额",
    dataIndex: "balance",
  },
  {
    title: "审核状态",
    dataIndex: "reviewStatus",
    slots: { customRender: "reviewStatus" },
  },
  {
    title: "审核信息",
    dataIndex: "reviewMessage",
  },
  {
    title: "审核时间",
    dataIndex: "reviewTime",
    slots: { customRender: "reviewTime" },
  },
  {
    title: "审核人 id",
    dataIndex: "reviewerId",
  },
  {
    title: "用户 id",
    dataIndex: "userId",
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
