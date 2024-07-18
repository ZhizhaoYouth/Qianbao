<template>
  <a-form
    :model="formSearchParams"
    :style="{ marginBottom: '20px' }"
    layout="inline"
    @submit="doSearch"
  >
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
  </a-table>
</template>

<script setup lang="ts">
import { ref, watchEffect } from "vue";
import API from "@/api";
import { message } from "ant-design-vue";
import dayjs from "dayjs";
import { listMyLoginLogVoByPageUsingPost } from "@/api/loginLogController";

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
  const res = await listMyLoginLogVoByPageUsingPost(searchParams.value);
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
];
</script>
