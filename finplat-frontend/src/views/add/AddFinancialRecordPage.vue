<template>
  <div id="addFinancialRecordPage" style="text-align: center">
    <h2 style="margin-bottom: 32px">
      为账户“{{ oldAccount?.accountName }}”添加财务记录
    </h2>
    <a-form
      :model="form"
      :style="{ width: '480px', margin: '0 auto' }"
      label-align="left"
      auto-label-width
      @submit="handleSubmit"
    >
      <a-form-item field="transactionDesc" label="交易描述">
        <a-input
          v-model:value="form.transactionDesc"
          placeholder="请输入交易描述"
        />
      </a-form-item>
      <a-form-item label="交易类型">
        <a-radio-group v-model:value="form.transactionType">
          <a-radio-button value="0">收入</a-radio-button>
          <a-radio-button value="1">支出</a-radio-button>
        </a-radio-group>
      </a-form-item>
      <a-form-item field="category" label="财务类别">
        <a-select
          v-model:value="form.category"
          placeholder="请选择财务类别"
          allow-clear
          style="width: 200px"
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
      <a-form-item field="amount" label="交易金额">
        <a-input
          v-model:value="form.amount"
          placeholder="请输入交易金额"
          type="number"
          step="0.01"
          :disabled="props.id == null"
        />
      </a-form-item>
      <a-form-item field="transactionTime" label="交易时间">
        <a-date-picker
          showTime
          showNow
          v-model:value="form.transactionTime"
          placeholder="请输入交易时间"
          allow-clear
        ></a-date-picker>
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
  editAccountUsingPost,
  getAccountVoByIdUsingGet,
} from "@/api/accountController";
import { message } from "ant-design-vue";
import {
  addFinancialRecordUsingPost,
  editFinancialRecordUsingPost,
  getFinancialRecordVoByIdUsingGet,
} from "@/api/financialRecordController";
import { listMyBudgetCategoryVoByPageUsingPost } from "@/api/budgetCategoryController";

interface Props {
  id: string;
  accountId: string;
}

const props = withDefaults(defineProps<Props>(), {
  id: () => {
    return "";
  },
});

const router = useRouter();

const form = ref({
  accountId: props.accountId,
  transactionDesc: "",
  transactionType: 0,
  category: "",
  amount: null,
} as API.FinancialRecordAddRequest);

const oldAccount = ref<API.AccountVO>();
const oldFinancialRecord = ref<API.FinancialRecordVO>();
const categories = ref<API.BudgetCategoryVO[]>([]); // 存储财务类别

/**
 * 加载数据
 */
const loadData = async () => {
  if (!props.id) {
    return;
  }
  const res = await getFinancialRecordVoByIdUsingGet({
    id: props.id as any,
  });
  if (res.data.code === 0 && res.data.data) {
    oldFinancialRecord.value = res.data.data;
    form.value = res.data.data;
  } else {
    message.error("获取数据失败，" + res.data.message);
  }
};

const loadOldAccount = async () => {
  const accountRes = await getAccountVoByIdUsingGet({
    id: props.accountId as any,
  });
  if (accountRes.data.code === 0 && accountRes.data.data) {
    oldAccount.value = accountRes.data.data;
  } else {
    message.error("获取数据失败，" + accountRes.data.message);
  }
};

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

// 获取旧数据
watchEffect(() => {
  loadData();
  loadOldAccount();
  loadCategories();
});

/**
 * 提交
 */
const handleSubmit = async () => {
  let res: any;
  // 如果是修改
  if (props.id) {
    res = await editFinancialRecordUsingPost({
      id: props.id as any,
      ...form.value,
    });
  } else {
    // 创建
    res = await addFinancialRecordUsingPost(form.value);
  }
  if (res.data.code === 0) {
    message.success("创建成功，即将跳转到账户详情页");
    setTimeout(() => {
      router.push(`/account/detail/${props.accountId}`);
    }, 3000);
  } else {
    message.error("操作失败，" + res.data.message);
  }
};
</script>
