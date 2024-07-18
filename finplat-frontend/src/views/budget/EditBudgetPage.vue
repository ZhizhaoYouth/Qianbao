<template>
  <div id="editBudgetPage" style="text-align: center">
    <h2 style="margin-bottom: 32px">修改预算</h2>
    <a-form
      :model="form"
      :style="{ width: '480px', margin: '0 auto' }"
      label-align="left"
      auto-label-width
      @submit="handleSubmit"
    >
      <a-form-item field="budgetDesc" label="预算描述">
        <a-input v-model:value="form.budgetDesc" placeholder="请输入预算描述" />
      </a-form-item>
      <a-form-item field="category" label="预算类别">
        <a-select
          v-model:value="form.category"
          placeholder="请选择预算类别"
          allow-clear
          style="width: 200px"
          disabled="true"
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
      <a-form-item field="amount" label="预算金额">
        <a-input
          v-model:value="form.amount"
          placeholder="请输入预算金额"
          type="number"
          step="0.01"
          :disabled="props.id == null"
        />
      </a-form-item>
      <a-form-item field="startDate" label="旧的开始时间">
        {{ dayjs(oldBudget?.startDate).format("YYYY-MM-DD HH:mm:ss") }}
      </a-form-item>
      <a-form-item field="startDate" label="新的开始时间">
        <a-date-picker
          showTime
          showNow
          v-model:value="form.startDate"
          placeholder="请输入预算开始时间"
          allow-clear
        ></a-date-picker>
      </a-form-item>
      <a-form-item field="endDate" label="旧的结束时间">
        {{ dayjs(oldBudget?.endDate).format("YYYY-MM-DD HH:mm:ss") }}
      </a-form-item>
      <a-form-item field="endDate" label="新的结束时间">
        <a-date-picker
          showTime
          showNow
          v-model:value="form.endDate"
          placeholder="请输入预算结束时间"
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
import { message } from "ant-design-vue";
import { listMyBudgetCategoryVoByPageUsingPost } from "@/api/budgetCategoryController";
import {
  addBudgetUsingPost,
  editBudgetUsingPost,
  getBudgetVoByIdUsingGet,
} from "@/api/budgetController";
import dayjs from "dayjs";
import moment from "moment";

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
  budgetDesc: "",
  category: "",
  amount: null,
  startDate: "",
  endDate: "",
} as API.BudgetVO);

const oldBudget = ref<API.BudgetVO>();
const categories = ref<API.BudgetCategoryVO[]>([]); // 存储财务类别

/**
 * 加载数据
 */
const loadData = async () => {
  if (!props.id) {
    return;
  }
  const res = await getBudgetVoByIdUsingGet({
    id: props.id as any,
  });
  if (res.data.code === 0 && res.data.data) {
    oldBudget.value = res.data.data;
    form.value.budgetDesc = res.data.data.budgetDesc;
    form.value.category = res.data.data.category;
    form.value.amount = res.data.data.amount;
  } else {
    message.error("获取数据失败，" + res.data.message);
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
  loadCategories();
});

/**
 * 提交
 */
const handleSubmit = async () => {
  let res: any;
  // 如果是修改
  res = await editBudgetUsingPost({
    id: props.id as any,
    ...form.value,
  });
  console.log(res);
  console.log(res);
  console.log(res);
  console.log(res);
  if (res.data.code === 0) {
    message.success("修改成功，即将跳转回到预算列表");
    setTimeout(() => {
      router.push(`/budget/list`);
    }, 3000);
  } else {
    message.error("操作失败，" + res.data.message);
  }
};
</script>
