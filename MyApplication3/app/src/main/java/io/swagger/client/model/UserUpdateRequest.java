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

/**
 * UserUpdateRequest
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2024-07-10T02:09:28.592Z")
public class UserUpdateRequest {
  @SerializedName("id")
  private Long id = null;

  @SerializedName("userAvatar")
  private String userAvatar = null;

  @SerializedName("userName")
  private String userName = null;

  @SerializedName("userProfile")
  private String userProfile = null;

  @SerializedName("userRole")
  private String userRole = null;

  public UserUpdateRequest id(Long id) {
    this.id = id;
    return this;
  }

   /**
   * Get id
   * @return id
  **/
  @ApiModelProperty(value = "")
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public UserUpdateRequest userAvatar(String userAvatar) {
    this.userAvatar = userAvatar;
    return this;
  }

   /**
   * Get userAvatar
   * @return userAvatar
  **/
  @ApiModelProperty(value = "")
  public String getUserAvatar() {
    return userAvatar;
  }

  public void setUserAvatar(String userAvatar) {
    this.userAvatar = userAvatar;
  }

  public UserUpdateRequest userName(String userName) {
    this.userName = userName;
    return this;
  }

   /**
   * Get userName
   * @return userName
  **/
  @ApiModelProperty(value = "")
  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public UserUpdateRequest userProfile(String userProfile) {
    this.userProfile = userProfile;
    return this;
  }

   /**
   * Get userProfile
   * @return userProfile
  **/
  @ApiModelProperty(value = "")
  public String getUserProfile() {
    return userProfile;
  }

  public void setUserProfile(String userProfile) {
    this.userProfile = userProfile;
  }

  public UserUpdateRequest userRole(String userRole) {
    this.userRole = userRole;
    return this;
  }

   /**
   * Get userRole
   * @return userRole
  **/
  @ApiModelProperty(value = "")
  public String getUserRole() {
    return userRole;
  }

  public void setUserRole(String userRole) {
    this.userRole = userRole;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UserUpdateRequest userUpdateRequest = (UserUpdateRequest) o;
    return Objects.equals(this.id, userUpdateRequest.id) &&
        Objects.equals(this.userAvatar, userUpdateRequest.userAvatar) &&
        Objects.equals(this.userName, userUpdateRequest.userName) &&
        Objects.equals(this.userProfile, userUpdateRequest.userProfile) &&
        Objects.equals(this.userRole, userUpdateRequest.userRole);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, userAvatar, userName, userProfile, userRole);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class UserUpdateRequest {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    userAvatar: ").append(toIndentedString(userAvatar)).append("\n");
    sb.append("    userName: ").append(toIndentedString(userName)).append("\n");
    sb.append("    userProfile: ").append(toIndentedString(userProfile)).append("\n");
    sb.append("    userRole: ").append(toIndentedString(userRole)).append("\n");
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
