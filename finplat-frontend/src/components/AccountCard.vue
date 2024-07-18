<template>
  <a-card class="accountCard" hoverable @click="doCardClick">
    <template #cover>
      <div
        v-if="account.bankInfo?.bankLogoURL"
        :style="{
          height: '204px',
          overflow: 'hidden',
          alignItems: 'center',
          display: 'flex',
        }"
      >
        <img
          :style="{
            width: '100%',
            margin: '0 auto',
          }"
          :alt="account.accountName"
          :src="account.bankInfo?.bankLogoURL"
        />
      </div>
      <div
        v-else
        :style="{
          height: '204px',
          overflow: 'hidden',
          alignItems: 'center',
          display: 'flex',
        }"
      >
        <img
          :style="{
            width: '50%',
            margin: '0 auto',
          }"
          :alt="account.accountName"
          src="../assets/eWallet.png"
        />
      </div>
    </template>
    <a-card-meta
      :title="account.accountName"
      :description="ACCOUNT_TYPE_MAP[account.accountType]"
    >
    </a-card-meta>
  </a-card>
</template>

<script setup lang="ts">
import API from "@/api";
import { defineProps, withDefaults } from "vue";
import { useRouter } from "vue-router";
import { ACCOUNT_TYPE_MAP } from "@/constant/account";
import { createFromIconfontCN } from "@ant-design/icons-vue";

interface Props {
  account: API.AccountVO;
}

const props = withDefaults(defineProps<Props>(), {
  account: () => {
    return {};
  },
});

const router = useRouter();
const doCardClick = () => {
  router.push(`/account/detail/${props.account.id}`);
};
</script>
<style scoped>
.accountCard {
  cursor: pointer;
}
</style>
