<template>
  <div id="accountDetailPage">
    <a-card style="align-items: center; display: flex; justify-content: center">
      <a-row style="margin-bottom: 16px; width: 100%">
        <a-col flex="auto" class="content-wrapper" style="text-align: left">
          <h2>{{ data.accountName }}</h2>
          <a-form
            :style="{ width: '350px', margin: '0 auto' }"
            label-align="left"
            auto-label-width
            @submit="handleSubmit"
          >
            <a-row>
              <a-col flex="1">
                <a-form-item field="accountName" label="账户名称">
                  <a-input
                    v-model:value="data.accountName"
                    placeholder="请输入账户名称"
                  />
                </a-form-item>
              </a-col>
              <a-col>
                <a-button
                  type="primary"
                  html-type="submit"
                  size="middle"
                  :style="{ marginLeft: '20px' }"
                >
                  修改
                </a-button>
              </a-col>
            </a-row>
          </a-form>
          <a-row
            ><p>银行名称：{{ data.bankName }}</p>
            <a-image
              :src="bankInfo.bankLogoURL"
              style="margin-left: 10px"
            ></a-image>
          </a-row>
          <p>账户类型：{{ ACCOUNT_TYPE_MAP[data.accountType] }}</p>
          <p>
            账户余额：<span :style="{ color: balanceColor }"
              >{{ data.balance }}元</span
            >
          </p>
          <p>审核状态：{{ REVIEW_STATUS_MAP[data.reviewStatus] }}</p>
          <p>审核信息：{{ data.reviewMessage }}</p>
          <p>
            创建时间：{{ dayjs(data.createTime).format("YYYY-MM-DD HH:mm:ss") }}
          </p>
          <p>
            最后更新时间：{{
              dayjs(data.updateTime).format("YYYY-MM-DD HH:mm:ss")
            }}
          </p>
          <a-space size="medium">
            <a-button :href="`/account/${id}/add-record`">添加记录</a-button>
            <a-button
              :href="`/account/${id}/add-budget`"
              style="margin-left: 10px"
              >设置预算
            </a-button>
            <a-button
              danger
              @click="doDelete(id as number)"
              style="margin-left: 10px"
              >删除账户
            </a-button>
          </a-space>
        </a-col>
      </a-row>
    </a-card>
  </div>
  <h2 style="text-align: center; margin: 10px 0 18px 0">此账户的财务记录</h2>
  <user-financial-record-page :accountId="id" />
  <h2 style="text-align: center; margin: 10px 0 18px 0">此账户设定的预算</h2>
  <user-budget-table-page :accountId="id" />
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
import {
  checkAccountUsingPost,
  deleteAccountUsingPost,
  editAccountUsingPost,
  getAccountDetailsPagesUsingPost,
  getAccountVoByIdUsingGet,
} from "@/api/accountController";
import { useRouter } from "vue-router";
import { useLoginUserStore } from "@/store/userStore";
import {
  ACCOUNT_TYPE_MAP,
  REVIEW_STATUS_ENUM,
  REVIEW_STATUS_MAP,
} from "../../constant/account";
import { message } from "ant-design-vue";
import dayjs from "dayjs";
import checkAccess from "@/access/checkAccess";
import UserFinancialRecordPage from "@/views/financialrecord/UserFinancialRecordPage.vue";
import UserBudgetPage from "@/views/budget/UserBudgetPage.vue";
import UserBudgetTablePage from "@/views/budget/UserBudgetTablePage.vue";

interface Props {
  id: string;
}

const props = withDefaults(defineProps<Props>(), {
  id: () => {
    return "";
  },
});

const router = useRouter();

const data = ref<API.AccountDetailsVO>({});

const bankInfo = ref({
  bankLogoURL: "",
  bankName: "",
});

// 获取登录用户
const loginUserStore = useLoginUserStore();
let loginUserId = loginUserStore.loginUser?.id;

const balanceColor = computed(() => {
  return data.value.balance < 0
    ? "red"
    : data.value.balance > 0
    ? "green"
    : "black";
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
  if (res.data.code === 0) {
    data.value = res.data.data as any;
  } else {
    message.error("获取数据失败，" + res.data.message);
  }
  if (data.value.accountType != 2) {
    const cardRequest: API.CardRequest = { key: data.value.accountNumber };
    const response = await checkAccountUsingPost(cardRequest);
    if (response.data.code === 0) {
      bankInfo.value.bankName = response.data.data.bankName || "";
      bankInfo.value.bankLogoURL = response.data.data.bankLogoURL || "";
    } else {
      message.error("获取银行图标数据失败");
    }
  }
};

/**
 * 删除
 * @param record
 */
const doDelete = async (delId: number) => {
  if (!delId) {
    return;
  }
  const res = await deleteAccountUsingPost({
    id: delId,
  });
  if (res.data.code === 0) {
    message.success("删除成功，即将回到主页");
    setTimeout(() => {
      router.push(`/`);
    }, 3000);
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

/**
 * 提交
 */
const handleSubmit = async () => {
  let res: any;
  res = await editAccountUsingPost({
    id: props.id as any,
    ...data.value,
  });

  if (res.data.code === 0) {
    message.success("修改成功");
    loadData();
  } else {
    message.error("操作失败，" + res.data.message);
  }
};
</script>

<style scoped>
#accountDetailPage {
}

#accountDetailPage .content-wrapper > * {
  margin-bottom: 20px;
}
</style>
