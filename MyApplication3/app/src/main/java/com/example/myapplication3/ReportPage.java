package com.example.myapplication3;

import static com.example.myapplication3.MainActivity.myURL;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.swagger.client.ApiClient;
import io.swagger.client.ApiException;
import io.swagger.client.ApiResponse;
import io.swagger.client.api.ReportControllerApi;
import io.swagger.client.model.BaseResponseReportVO;
import io.swagger.client.model.ReportQueryRequest;
import io.swagger.client.model.ReportVO;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;

public class ReportPage extends AppCompatActivity {
    private String sessionId;
    private BarChart barChart;
    private PieChart incomePieChart;
    private PieChart expensePieChart;
    private Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reportpage);
        sessionId = getIntent().getStringExtra("SESSION_ID");

        barChart = findViewById(R.id.barChart);
        incomePieChart = findViewById(R.id.incomePieChart);
        expensePieChart = findViewById(R.id.expensePieChart);
//        btnBack = findViewById(R.id.btnBack);

//        btnBack.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish(); // 返回上一页
//            }
//        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                ApiClient apiClient = new ApiClient();
                apiClient.setBasePath(myURL);
                apiClient.addDefaultHeader("Cookie", "JSESSIONID=" + sessionId);

                ReportQueryRequest reportQueryRequest = new ReportQueryRequest();
                ReportControllerApi reportControllerApi = new ReportControllerApi(apiClient);

                try {
                    ApiResponse response = reportControllerApi.getReportUsingPOSTWithHttpInfo(reportQueryRequest);
                    BaseResponseReportVO baseResponse = (BaseResponseReportVO) response.getData();
                    ReportVO reportVO = baseResponse.getData();

                    // 处理数据并更新图表
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            // 设置柱状图数据和颜色
                            List<BarEntry> barEntries = new ArrayList<>();
                            barEntries.add(new BarEntry(1f, reportVO.getTotalIncome().floatValue()));
                            barEntries.add(new BarEntry(2f, reportVO.getTotalExpenses().floatValue()));
                            BarDataSet barDataSet = new BarDataSet(barEntries, "总收入与总支出");
                            barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
                            BarData barData = new BarData(barDataSet);
                            barChart.setData(barData);
                            barChart.invalidate();

                            // 设置收入饼图数据和颜色
                            List<PieEntry> incomeEntries = new ArrayList<>();
                            for (Map.Entry<String, BigDecimal> entry : reportVO.getCategoryIncomeSummary().entrySet()) {
                                incomeEntries.add(new PieEntry(entry.getValue().floatValue(), entry.getKey()));
                            }
                            PieDataSet incomeDataSet = new PieDataSet(incomeEntries, "收入类别");
                            incomeDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
                            PieData incomeData = new PieData(incomeDataSet);
                            incomePieChart.setData(incomeData);
                            incomePieChart.invalidate();

                            // 设置支出饼图数据和颜色
                            List<PieEntry> expenseEntries = new ArrayList<>();
                            for (Map.Entry<String, BigDecimal> entry : reportVO.getCategoryExpenseSummary().entrySet()) {
                                expenseEntries.add(new PieEntry(entry.getValue().floatValue(), entry.getKey()));
                            }
                            PieDataSet expenseDataSet = new PieDataSet(expenseEntries, "支出类别");
                            expenseDataSet.setColors(ColorTemplate.JOYFUL_COLORS);
                            PieData expenseData = new PieData(expenseDataSet);
                            expensePieChart.setData(expenseData);
                            expensePieChart.invalidate();
                        }
                    });

                } catch (ApiException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
