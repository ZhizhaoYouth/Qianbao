<template>
  <div id="generateReportPage" style="text-align: center">
    <h2 style="margin-bottom: 32px">生成财务报表</h2>
    <h3 style="margin-bottom: 32px">请输入需要计算的时间段</h3>
    <a-form
      :model="form"
      :style="{ width: '480px', margin: '0 auto' }"
      label-align="left"
      auto-label-width
      @submit="handleSubmit"
    >
      <a-form-item field="startDate" label="开始时间">
        <a-date-picker
          showTime
          showNow
          v-model:value="form.startDate"
          placeholder="请输入开始时间"
          allow-clear
        ></a-date-picker>
      </a-form-item>
      <a-form-item field="endDate" label="结束时间">
        <a-date-picker
          showTime
          showNow
          v-model:value="form.endDate"
          placeholder="请输入结束时间"
          allow-clear
        ></a-date-picker>
      </a-form-item>
      <a-button
        type="primary"
        html-type="submit"
        :style="{ width: '120px', margin: '0 auto' }"
      >
        生成
      </a-button>
    </a-form>
    <div v-show="showReport">
      <a-card style="margin-top: 20px">
        <h2 style="text-align: center">账户报表</h2>
        <p>开始时间: {{ formatDate(reportData.startDate) }}</p>
        <p>结束时间: {{ formatDate(reportData.endDate) }}</p>
        <p>总收入: {{ reportData.totalIncome }}元</p>
        <p>总支出: {{ reportData.totalExpenses }}元</p>
        <p>净收入: {{ reportData.netIncome }}元</p>
      </a-card>
      <div class="chart-container">
        <a-row>
          <a-col flex="50%">
            <div ref="incomeBarChart" style="width: 100%; height: 400px"></div>
          </a-col>
          <a-col flex="50%">
            <div
              ref="incomePieChart"
              style="width: 100%; height: 400px; margin-top: 20px"
            ></div>
          </a-col>
        </a-row>
        <a-row>
          <a-col flex="50%">
            <div ref="expenseBarChart" style="width: 100%; height: 400px"></div>
          </a-col>
          <a-col flex="50%">
            <div
              ref="expensePieChart"
              style="width: 100%; height: 400px; margin-top: 20px"
            ></div>
          </a-col>
        </a-row>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { onMounted, ref, watch } from "vue";
import * as echarts from "echarts";
import { getReportUsingPost } from "@/api/reportController";
import dayjs from "dayjs";
import { message } from "ant-design-vue";
import { time } from "echarts";
import API from "@/api";

const form = ref({
  startDate: "",
  endDate: "",
});

const reportData = ref({
  categoryExpenseSummary: null,
  categoryIncomeSummary: null,
  endDate: "",
  netIncome: null,
  startDate: "",
  totalExpenses: null,
  totalIncome: null,
} as API.ReportVO);

const incomeBarChart = ref();
const incomePieChart = ref();
const expenseBarChart = ref();
const expensePieChart = ref();
// 格式化日期函数
const formatDate = (date) => dayjs(date).format("YYYY-MM-DD HH:mm:ss");

// 将 Map 转换为 ECharts 需要的数据格式
const formatMapToDataSource = (map) => {
  return Object.keys(map).map((key) => ({
    value: map[key],
    name: key,
  }));
};

const showReport = ref(false);
// 渲染图表函数
const renderCharts = () => {
  if (!showReport.value) return;

  // 渲染收入柱状图
  const incomeBarChartInstance = echarts.init(incomeBarChart.value);
  incomeBarChartInstance.setOption({
    title: {
      text: "收入分类汇总",
      left: "center",
    },
    xAxis: {
      type: "category",
      data: Object.keys(reportData.value.categoryIncomeSummary || {}),
    },
    yAxis: {
      type: "value",
    },
    series: [
      {
        name: "金额",
        type: "bar",
        data: Object.values(reportData.value.categoryIncomeSummary || {}),
      },
    ],
    tooltip: {},
    emphasis: {
      itemStyle: {
        shadowBlur: 10,
        shadowOffsetX: 0,
        shadowColor: "rgba(0, 0, 0, 0.5)",
      },
    },
  });

  // 渲染收入饼状图
  const incomePieChartInstance = echarts.init(incomePieChart.value);
  incomePieChartInstance.setOption({
    title: {
      text: "收入分类汇总",
      left: "center",
    },
    tooltip: {
      trigger: "item",
    },
    series: [
      {
        type: "pie",
        data: formatMapToDataSource(reportData.value.categoryIncomeSummary),
      },
    ],
    emphasis: {
      itemStyle: {
        shadowBlur: 10,
        shadowOffsetX: 0,
        shadowColor: "rgba(0, 0, 0, 0.5)",
      },
    },
  });

  // 渲染支出柱状图
  const expenseBarChartInstance = echarts.init(expenseBarChart.value);
  expenseBarChartInstance.setOption({
    title: {
      text: "支出分类汇总",
      left: "center",
    },
    xAxis: {
      type: "category",
      data: Object.keys(reportData.value.categoryExpenseSummary || {}),
    },
    yAxis: {
      type: "value",
    },
    series: [
      {
        data: Object.values(reportData.value.categoryExpenseSummary || {}),
        type: "bar",
      },
    ],
    tooltip: {},
    emphasis: {
      itemStyle: {
        shadowBlur: 10,
        shadowOffsetX: 0,
        shadowColor: "rgba(0, 0, 0, 0.5)",
      },
    },
  });

  // 渲染支出饼状图
  const expensePieChartInstance = echarts.init(expensePieChart.value);
  expensePieChartInstance.setOption({
    title: {
      text: "支出分类汇总",
      left: "center",
    },
    tooltip: {
      trigger: "item",
    },
    series: [
      {
        type: "pie",
        data: formatMapToDataSource(reportData.value.categoryExpenseSummary),
      },
    ],
    emphasis: {
      itemStyle: {
        shadowBlur: 10,
        shadowOffsetX: 0,
        shadowColor: "rgba(0, 0, 0, 0.5)",
      },
    },
  });
};

// 处理表单提交事件
const handleSubmit = async () => {
  const res = await getReportUsingPost({ ...form.value });
  if (res.data.code === 0) {
    message.success("生成报表成功！");
    reportData.value = res.data.data;
    showReport.value = true;
    setTimeout(() => {
      renderCharts();
    }, 200);
  } else {
    message.error("操作失败，" + res.data.message);
  }
};
</script>

<style scoped>
.chart-container {
  width: 100%; /* 确保容器宽度充满其父元素 */
  margin: 0 auto; /* 居中显示 */
}
</style>
