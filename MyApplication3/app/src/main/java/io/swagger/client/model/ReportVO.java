/*
 * 接口文档
 * No description provided (generated by Swagger Codegen https://github.com/swagger-api/swagger-codegen)
 *
 * OpenAPI spec version: 1.0
 * 
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */


package io.swagger.client.model;

import java.util.Objects;
import java.util.Arrays;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.threeten.bp.OffsetDateTime;

/**
 * ReportVO
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2024-07-10T02:09:28.592Z")
public class ReportVO {
  @SerializedName("categoryExpenseSummary")
  private Map<String, BigDecimal> categoryExpenseSummary = null;

  @SerializedName("categoryIncomeSummary")
  private Map<String, BigDecimal> categoryIncomeSummary = null;

  @SerializedName("endDate")
  private OffsetDateTime endDate = null;

  @SerializedName("netIncome")
  private BigDecimal netIncome = null;

  @SerializedName("startDate")
  private OffsetDateTime startDate = null;

  @SerializedName("totalExpenses")
  private BigDecimal totalExpenses = null;

  @SerializedName("totalIncome")
  private BigDecimal totalIncome = null;

  public ReportVO categoryExpenseSummary(Map<String, BigDecimal> categoryExpenseSummary) {
    this.categoryExpenseSummary = categoryExpenseSummary;
    return this;
  }

  public ReportVO putCategoryExpenseSummaryItem(String key, BigDecimal categoryExpenseSummaryItem) {
    if (this.categoryExpenseSummary == null) {
      this.categoryExpenseSummary = new HashMap<String, BigDecimal>();
    }
    this.categoryExpenseSummary.put(key, categoryExpenseSummaryItem);
    return this;
  }

   /**
   * Get categoryExpenseSummary
   * @return categoryExpenseSummary
  **/
  @ApiModelProperty(value = "")
  public Map<String, BigDecimal> getCategoryExpenseSummary() {
    return categoryExpenseSummary;
  }

  public void setCategoryExpenseSummary(Map<String, BigDecimal> categoryExpenseSummary) {
    this.categoryExpenseSummary = categoryExpenseSummary;
  }

  public ReportVO categoryIncomeSummary(Map<String, BigDecimal> categoryIncomeSummary) {
    this.categoryIncomeSummary = categoryIncomeSummary;
    return this;
  }

  public ReportVO putCategoryIncomeSummaryItem(String key, BigDecimal categoryIncomeSummaryItem) {
    if (this.categoryIncomeSummary == null) {
      this.categoryIncomeSummary = new HashMap<String, BigDecimal>();
    }
    this.categoryIncomeSummary.put(key, categoryIncomeSummaryItem);
    return this;
  }

   /**
   * Get categoryIncomeSummary
   * @return categoryIncomeSummary
  **/
  @ApiModelProperty(value = "")
  public Map<String, BigDecimal> getCategoryIncomeSummary() {
    return categoryIncomeSummary;
  }

  public void setCategoryIncomeSummary(Map<String, BigDecimal> categoryIncomeSummary) {
    this.categoryIncomeSummary = categoryIncomeSummary;
  }

  public ReportVO endDate(OffsetDateTime endDate) {
    this.endDate = endDate;
    return this;
  }

   /**
   * Get endDate
   * @return endDate
  **/
  @ApiModelProperty(value = "")
  public OffsetDateTime getEndDate() {
    return endDate;
  }

  public void setEndDate(OffsetDateTime endDate) {
    this.endDate = endDate;
  }

  public ReportVO netIncome(BigDecimal netIncome) {
    this.netIncome = netIncome;
    return this;
  }

   /**
   * Get netIncome
   * @return netIncome
  **/
  @ApiModelProperty(value = "")
  public BigDecimal getNetIncome() {
    return netIncome;
  }

  public void setNetIncome(BigDecimal netIncome) {
    this.netIncome = netIncome;
  }

  public ReportVO startDate(OffsetDateTime startDate) {
    this.startDate = startDate;
    return this;
  }

   /**
   * Get startDate
   * @return startDate
  **/
  @ApiModelProperty(value = "")
  public OffsetDateTime getStartDate() {
    return startDate;
  }

  public void setStartDate(OffsetDateTime startDate) {
    this.startDate = startDate;
  }

  public ReportVO totalExpenses(BigDecimal totalExpenses) {
    this.totalExpenses = totalExpenses;
    return this;
  }

   /**
   * Get totalExpenses
   * @return totalExpenses
  **/
  @ApiModelProperty(value = "")
  public BigDecimal getTotalExpenses() {
    return totalExpenses;
  }

  public void setTotalExpenses(BigDecimal totalExpenses) {
    this.totalExpenses = totalExpenses;
  }

  public ReportVO totalIncome(BigDecimal totalIncome) {
    this.totalIncome = totalIncome;
    return this;
  }

   /**
   * Get totalIncome
   * @return totalIncome
  **/
  @ApiModelProperty(value = "")
  public BigDecimal getTotalIncome() {
    return totalIncome;
  }

  public void setTotalIncome(BigDecimal totalIncome) {
    this.totalIncome = totalIncome;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ReportVO reportVO = (ReportVO) o;
    return Objects.equals(this.categoryExpenseSummary, reportVO.categoryExpenseSummary) &&
        Objects.equals(this.categoryIncomeSummary, reportVO.categoryIncomeSummary) &&
        Objects.equals(this.endDate, reportVO.endDate) &&
        Objects.equals(this.netIncome, reportVO.netIncome) &&
        Objects.equals(this.startDate, reportVO.startDate) &&
        Objects.equals(this.totalExpenses, reportVO.totalExpenses) &&
        Objects.equals(this.totalIncome, reportVO.totalIncome);
  }

  @Override
  public int hashCode() {
    return Objects.hash(categoryExpenseSummary, categoryIncomeSummary, endDate, netIncome, startDate, totalExpenses, totalIncome);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ReportVO {\n");
    
    sb.append("    categoryExpenseSummary: ").append(toIndentedString(categoryExpenseSummary)).append("\n");
    sb.append("    categoryIncomeSummary: ").append(toIndentedString(categoryIncomeSummary)).append("\n");
    sb.append("    endDate: ").append(toIndentedString(endDate)).append("\n");
    sb.append("    netIncome: ").append(toIndentedString(netIncome)).append("\n");
    sb.append("    startDate: ").append(toIndentedString(startDate)).append("\n");
    sb.append("    totalExpenses: ").append(toIndentedString(totalExpenses)).append("\n");
    sb.append("    totalIncome: ").append(toIndentedString(totalIncome)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }

}
