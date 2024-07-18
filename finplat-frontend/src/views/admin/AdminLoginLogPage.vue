<template>
  <a-form
    :model="formSearchParams"
    :style="{ marginBottom: '20px' }"
    layout="inline"
    @submit="doSearch"
  >
    <a-form-item field="userId" label="用户ID">
      <a-input
        v-model:value="formSearchParams.userId"
        placeholder="请输入用户ID"
        allow-clear
        style="width: 180px"
      />
    </a-form-item>
    <a-form-item field="deviceType" label="设备类型">
      <a-input
        v-model:value="formSearchParams.deviceType"
        placeholder="请输入设备类型"
        allow-clear
        style="width: 100px"
      />
    </a-form-item>
    <a-form-item field="location" label="登录地点">
      <a-input
        v-model:value="formSearchParams.location"
        placeholder="请输入登录地点"
        allow-clear
        style="width: 100px"
      />
    </a-form-item>
    <a-form-item field="browserInfo" label="浏览器信息">
      <a-input
        v-model:value="formSearchParams.browserInfo"
        placeholder="请输入浏览器信息"
        allow-clear
        style="width: 100px"
      />
    </a-form-item>
    <a-form-item field="operatingSystem" label="操作系统">
      <a-input
        v-model:value="formSearchParams.operatingSystem"
        placeholder="请输入操作系统"
        allow-clear
        style="width: 100px"
      />
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
    <template #loginTime="{ record }">
      {{ dayjs(record.loginTime).format("YYYY-MM-DD HH:mm:ss") }}
    </template>
    <template #createTime="{ record }">
      {{ dayjs(record.createTime).format("YYYY-MM-DD HH:mm:ss") }}
    </template>
    <template #updateTime="{ record }">
      {{ dayjs(record.updateTime).format("YYYY-MM-DD HH:mm:ss") }}
    </template>
    <template #optional="{ record }">
      <a-space>
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
import {
  deleteLoginLogUsingPost,
  listLoginLogByPageUsingPost,
} from "@/api/loginLogController";

const formSearchParams = ref<API.LoginLogQueryRequest>({});

// 初始化搜索条件（不应该被修改）
const initSearchParams = {
  current: 1,
  pageSize: 10,
};

const searchParams = ref<API.LoginLogQueryRequest>({
  ...initSearchParams,
});
const dataList = ref<API.LoginLog[]>([]);
const total = ref<number>(0);

/**
 * 加载数据
 */
const loadData = async () => {
  const res = await listLoginLogByPageUsingPost(searchParams.value);
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

  const res = await deleteLoginLogUsingPost({
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

// 表格列配置
const columns = [
  {
    title: "id",
    dataIndex: "id",
  },
  {
    title: "用户ID",
    dataIndex: "userId",
  },
  {
    title: "登录时间",
    dataIndex: "loginTime",
    slots: { customRender: "loginTime" },
  },
  {
    title: "设备类型",
    dataIndex: "deviceType",
  },
  {
    title: "ip地址",
    dataIndex: "ipAddress",
  },
  {
    title: "登录地点",
    dataIndex: "location",
  },
  {
    title: "浏览器信息",
    dataIndex: "browserInfo",
  },
  {
    title: "操作系统",
    dataIndex: "operatingSystem",
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
