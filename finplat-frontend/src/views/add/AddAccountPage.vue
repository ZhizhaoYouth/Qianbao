<template>
  <div id="addAccountPage" style="text-align: center">
    <h2 style="margin-bottom: 32px">创建账户</h2>
    <a-form
      :model="form"
      :style="{ width: '480px', margin: '0 auto' }"
      label-align="left"
      auto-label-width
      @submit="handleSubmit"
    >
      <a-form-item field="accountName" label="账户名称">
        <a-input
          v-model:value="form.accountName"
          placeholder="请输入账户名称"
        />
      </a-form-item>
      <a-form-item label="账户类型">
        <a-radio-group v-model:value="form.accountType">
          <a-radio-button value="0">储蓄卡</a-radio-button>
          <a-radio-button value="1">信用卡</a-radio-button>
          <a-radio-button value="2">电子钱包</a-radio-button>
        </a-radio-group>
      </a-form-item>
      <a-form-item field="accountNumber" label="账户号码">
        <a-input
          v-model:value="form.accountNumber"
          placeholder="请输入账户号码"
          :disabled="form.accountType == -1"
        />
      </a-form-item>
      <a-form-item field="balance" label="账户余额">
        <a-input
          v-model:value="form.balance"
          placeholder="请输入账户余额"
          type="number"
          step="0.01"
          :disabled="props.id == null"
        />
      </a-form-item>
      <a-form-item
        field="bankInfo"
        label="银行信息"
        v-if="form.accountType != 2 && form.accountType != -1"
      >
        <div v-if="bankInfo">
          <p>{{ bankInfo.bankName }}</p>
          <a-image :src="bankInfo.bankLogoURL" alt="暂无图片" />
        </div>
      </a-form-item>
      <a-button
        type="primary"
        html-type="submit"
        :style="{ width: '120px', margin: '0 auto' }"
      >
        提交
      </a-button>
    </a-form>
  </div>
</template>

<script setup lang="ts">
import {
  computed,
  defineProps,
  ref,
  watch,
  watchEffect,
  withDefaults,
} from "vue";
import API from "@/api";
import { useRouter } from "vue-router";
import {
  addAccountUsingPost,
  checkAccountUsingPost,
  editAccountUsingPost,
  getAccountVoByIdUsingGet,
} from "@/api/accountController";
import { ACCOUNT_TYPE_MAP } from "@/constant/account";
import { message } from "ant-design-vue";
import BankInfo = API.BankInfo;

interface Props {
  id: string;
}

const props = withDefaults(defineProps<Props>(), {
  id: () => {
    return "";
  },
});

const router = useRouter();

const form = ref({
  accountName: "",
  accountNumber: "",
  accountType: -1,
  balance: null,
} as API.AccountAddRequest);

const oldAccount = ref<API.AccountVO>();
let bankInfo = ref({
  bankLogoURL: "",
  bankName: "",
});

/**
 * 加载数据
 */
const loadData = async () => {
  if (!props.id) {
    return;
  }
  const res = await getAccountVoByIdUsingGet({
    id: props.id as any,
  });
  if (res.data.code === 0 && res.data.data) {
    oldAccount.value = res.data.data;
    form.value = res.data.data;
  } else {
    message.error("获取数据失败，" + res.data.message);
  }
};

// 监听账户号码变化
watch(
  () => form.value.accountNumber,
  async (newVal) => {
    if (
      form.value.accountType != -1 &&
      form.value.accountType != 2 &&
      newVal.length >= 12
    ) {
      const cardRequest: API.CardRequest = { key: newVal };
      // 假设至少输入 12 位才检查
      const response = await checkAccountUsingPost(cardRequest);
      if (response.data.code === 0) {
        bankInfo.value.bankName = response.data.data.bankName || "";
        bankInfo.value.bankLogoURL = response.data.data.bankLogoURL || "";
      } else {
        bankInfo = null;
      }
    }
  }
);

// 获取旧数据
watchEffect(() => {
  loadData();
});

/**
 * 提交
 */
const handleSubmit = async () => {
  let res: any;
  // 如果是修改
  if (props.id) {
    res = await editAccountUsingPost({
      id: props.id as any,
      ...form.value,
    });
  } else {
    // 创建
    res = await addAccountUsingPost(form.value);
  }
  if (res.data.code === 0) {
    message.success("创建成功，即将跳转到详情页");
    setTimeout(() => {
      router.push(`/account/detail/${props.id || res.data.data}`);
    }, 3000);
  } else {
    message.error("操作失败，" + res.data.message);
  }
};
</script>
