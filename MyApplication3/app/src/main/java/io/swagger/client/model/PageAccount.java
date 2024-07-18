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
import io.swagger.client.model.Account;
import io.swagger.client.model.OrderItem;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * PageAccount
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2024-07-10T02:09:28.592Z")
public class PageAccount {
  @SerializedName("countId")
  private String countId = null;

  @SerializedName("current")
  private Long current = null;

  @SerializedName("maxLimit")
  private Long maxLimit = null;

  @SerializedName("optimizeCountSql")
  private Boolean optimizeCountSql = null;

  @SerializedName("orders")
  private List<OrderItem> orders = null;

  @SerializedName("pages")
  private Long pages = null;

  @SerializedName("records")
  private List<Account> records = null;

  @SerializedName("searchCount")
  private Boolean searchCount = null;

  @SerializedName("size")
  private Long size = null;

  @SerializedName("total")
  private Long total = null;

  public PageAccount countId(String countId) {
    this.countId = countId;
    return this;
  }

   /**
   * Get countId
   * @return countId
  **/
  @ApiModelProperty(value = "")
  public String getCountId() {
    return countId;
  }

  public void setCountId(String countId) {
    this.countId = countId;
  }

  public PageAccount current(Long current) {
    this.current = current;
    return this;
  }

   /**
   * Get current
   * @return current
  **/
  @ApiModelProperty(value = "")
  public Long getCurrent() {
    return current;
  }

  public void setCurrent(Long current) {
    this.current = current;
  }

  public PageAccount maxLimit(Long maxLimit) {
    this.maxLimit = maxLimit;
    return this;
  }

   /**
   * Get maxLimit
   * @return maxLimit
  **/
  @ApiModelProperty(value = "")
  public Long getMaxLimit() {
    return maxLimit;
  }

  public void setMaxLimit(Long maxLimit) {
    this.maxLimit = maxLimit;
  }

  public PageAccount optimizeCountSql(Boolean optimizeCountSql) {
    this.optimizeCountSql = optimizeCountSql;
    return this;
  }

   /**
   * Get optimizeCountSql
   * @return optimizeCountSql
  **/
  @ApiModelProperty(value = "")
  public Boolean isOptimizeCountSql() {
    return optimizeCountSql;
  }

  public void setOptimizeCountSql(Boolean optimizeCountSql) {
    this.optimizeCountSql = optimizeCountSql;
  }

  public PageAccount orders(List<OrderItem> orders) {
    this.orders = orders;
    return this;
  }

  public PageAccount addOrdersItem(OrderItem ordersItem) {
    if (this.orders == null) {
      this.orders = new ArrayList<OrderItem>();
    }
    this.orders.add(ordersItem);
    return this;
  }

   /**
   * Get orders
   * @return orders
  **/
  @ApiModelProperty(value = "")
  public List<OrderItem> getOrders() {
    return orders;
  }

  public void setOrders(List<OrderItem> orders) {
    this.orders = orders;
  }

  public PageAccount pages(Long pages) {
    this.pages = pages;
    return this;
  }

   /**
   * Get pages
   * @return pages
  **/
  @ApiModelProperty(value = "")
  public Long getPages() {
    return pages;
  }

  public void setPages(Long pages) {
    this.pages = pages;
  }

  public PageAccount records(List<Account> records) {
    this.records = records;
    return this;
  }

  public PageAccount addRecordsItem(Account recordsItem) {
    if (this.records == null) {
      this.records = new ArrayList<Account>();
    }
    this.records.add(recordsItem);
    return this;
  }

   /**
   * Get records
   * @return records
  **/
  @ApiModelProperty(value = "")
  public List<Account> getRecords() {
    return records;
  }

  public void setRecords(List<Account> records) {
    this.records = records;
  }

  public PageAccount searchCount(Boolean searchCount) {
    this.searchCount = searchCount;
    return this;
  }

   /**
   * Get searchCount
   * @return searchCount
  **/
  @ApiModelProperty(value = "")
  public Boolean isSearchCount() {
    return searchCount;
  }

  public void setSearchCount(Boolean searchCount) {
    this.searchCount = searchCount;
  }

  public PageAccount size(Long size) {
    this.size = size;
    return this;
  }

   /**
   * Get size
   * @return size
  **/
  @ApiModelProperty(value = "")
  public Long getSize() {
    return size;
  }

  public void setSize(Long size) {
    this.size = size;
  }

  public PageAccount total(Long total) {
    this.total = total;
    return this;
  }

   /**
   * Get total
   * @return total
  **/
  @ApiModelProperty(value = "")
  public Long getTotal() {
    return total;
  }

  public void setTotal(Long total) {
    this.total = total;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PageAccount pageAccount = (PageAccount) o;
    return Objects.equals(this.countId, pageAccount.countId) &&
        Objects.equals(this.current, pageAccount.current) &&
        Objects.equals(this.maxLimit, pageAccount.maxLimit) &&
        Objects.equals(this.optimizeCountSql, pageAccount.optimizeCountSql) &&
        Objects.equals(this.orders, pageAccount.orders) &&
        Objects.equals(this.pages, pageAccount.pages) &&
        Objects.equals(this.records, pageAccount.records) &&
        Objects.equals(this.searchCount, pageAccount.searchCount) &&
        Objects.equals(this.size, pageAccount.size) &&
        Objects.equals(this.total, pageAccount.total);
  }

  @Override
  public int hashCode() {
    return Objects.hash(countId, current, maxLimit, optimizeCountSql, orders, pages, records, searchCount, size, total);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PageAccount {\n");
    
    sb.append("    countId: ").append(toIndentedString(countId)).append("\n");
    sb.append("    current: ").append(toIndentedString(current)).append("\n");
    sb.append("    maxLimit: ").append(toIndentedString(maxLimit)).append("\n");
    sb.append("    optimizeCountSql: ").append(toIndentedString(optimizeCountSql)).append("\n");
    sb.append("    orders: ").append(toIndentedString(orders)).append("\n");
    sb.append("    pages: ").append(toIndentedString(pages)).append("\n");
    sb.append("    records: ").append(toIndentedString(records)).append("\n");
    sb.append("    searchCount: ").append(toIndentedString(searchCount)).append("\n");
    sb.append("    size: ").append(toIndentedString(size)).append("\n");
    sb.append("    total: ").append(toIndentedString(total)).append("\n");
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

