<template>
  <h2>超支提示：</h2>
  <h3
    v-if="exceedInfo"
    v-for="info in exceedInfo"
    style="margin-bottom: 20px; color: crimson"
  >
    {{ info }}
  </h3>
  <h3 v-else style="margin-bottom: 20px; color: #42b983">
    未发现超支现象，请继续保持！
  </h3>
  <user-budget-table-page />
</template>

<script setup lang="ts">
import { defineProps, ref, watchEffect, withDefaults } from "vue";
import API from "@/api";
import { message } from "ant-design-vue";
import { checkBudgetExceedUsingGet } from "@/api/budgetController";
import UserBudgetTablePage from "@/views/budget/UserBudgetTablePage.vue";

const exceedInfo = ref<API.BaseResponseListString_>(null);

/**
 * 加载超支信息
 */
const loadExceedInfo = async () => {
  const res = await checkBudgetExceedUsingGet();

  if (res.data.code === 0) {
    //如果传回来的列表为空，则不赋值
    if (res.data.data?.length > 0) {
      exceedInfo.value = res.data.data;
    } else {
      exceedInfo.value = null;
    }
  } else {
    message.error("获取超支信息失败，" + res.data.message);
  }
};

/**
 * 监听 searchParams 变量，改变时触发数据的重新加载
 */
watchEffect(() => {
  loadExceedInfo();
});
</script>
