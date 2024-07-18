<template>
  <div id="homePage">
    <h1 class="title">我的账户</h1>
    <h2>
      总余额：<span :style="{ color: balanceColor }">{{ myBalance }}元</span>
    </h2>
    <div class="searchBar">
      <a-input-search
        v-model:value="inputSearchText"
        :style="{ width: '320px' }"
        placeholder="搜索我的账户"
        button-text="搜索"
        size="large"
        @search="doSearch"
      />
      <a-button
        type="link"
        href="/add/account"
        size="large"
        style="margin-left: 20px"
        >添加账户
      </a-button>
    </div>
    <a-list
      class="list-demo-action-layout"
      :grid="{
        gutter: [20, 20],
        xs: 1,
        sm: 1,
        md: 2,
        lg: 3,
        xl: 3,
        xxl: 4,
        xxxl: 4,
        warp: true,
      }"
      :bordered="false"
      :data-source="dataList"
      size="small"
      :pagination="{
        pageSize: searchParams.pageSize,
        current: searchParams.current,
        total,
        onChange: onPageChange,
      }"
    >
      <template #renderItem="{ item }">
        <account-card :account="item" style="margin: 0 10px 0 10px" />
      </template>
    </a-list>
  </div>
</template>

<script setup lang="ts">
import { computed, ref, watchEffect } from "vue";
import API from "@/api";
import { REVIEW_STATUS_ENUM } from "@/constant/account";
import {
  getAllMyBalanceUsingGet,
  listMyAccountVoByPageUsingPost,
} from "@/api/accountController";
import { message } from "ant-design-vue";
import AccountCard from "@/components/AccountCard.vue";
import UAParser from "ua-parser-js";

// 初始化搜索条件（不应该被修改）
const initSearchParams = {
  current: 1,
  pageSize: 12,
};

const parser = new UAParser();
const result = parser.setUA(navigator.userAgent).getResult();

const searchParams = ref<API.AccountQueryRequest>({
  ...initSearchParams,
});
const inputSearchText = ref<string>("");
const dataList = ref<API.AccountVO[]>([]);
const total = ref<number>(0);

const myBalance = ref<number>(0);

/**
 * 加载数据
 */
const loadData = async () => {
  const params = {
    ...searchParams.value,
  };
  // eslint-disable-next-line no-undef
  const res = await listMyAccountVoByPageUsingPost(params);
  if (res.data.code === 0) {
    dataList.value = res.data.data?.records || [];
    total.value = res.data.data?.total || 0;
  } else {
    message.error("获取数据失败，" + res.data.message);
  }
  const balRes = await getAllMyBalanceUsingGet();
  if (balRes.data.code === 0) {
    myBalance.value = balRes.data.data;
  } else {
    message.error("获取余额数据失败，" + res.data.message);
  }
};

/**
 * 当分页变化时，改变搜索条件，触发数据加载
 * @param page
 */
const onPageChange = (page: number, pageSize: number) => {
  searchParams.value = {
    ...searchParams.value,
    current: page,
  };
};

/**
 * 执行搜索
 */
const doSearch = () => {
  searchParams.value = {
    ...initSearchParams,
    searchText: inputSearchText.value,
  };
};

/**
 * 监听 searchParams 变量，改变时触发数据的重新加载
 */
watchEffect(() => {
  loadData();
});

const balanceColor = computed(() => {
  return myBalance.value < 0
    ? "#c90000"
    : myBalance.value > 0
    ? "#56be00"
    : "black";
});
</script>

<style scoped>
#homePage {
}

.searchBar {
  margin-bottom: 28px;
  text-align: center;
}

#homePage .title {
  margin-bottom: 18px;
  font-size: 28px;
  text-align: center;
}

.list-demo-action-layout {
  width: 100%;
  height: 100%;
  margin-bottom: 18px;
}

.list-demo-action-layout .image-area img {
  width: 100%;
}
</style>
