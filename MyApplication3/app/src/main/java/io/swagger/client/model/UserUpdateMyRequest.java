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
 * UserUpdateMyRequest
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2024-07-10T02:09:28.592Z")
public class UserUpdateMyRequest {
  @SerializedName("userAvatar")
  private String userAvatar = null;

  @SerializedName("userName")
  private String userName = null;

  @SerializedName("userProfile")
  private String userProfile = null;

  public UserUpdateMyRequest userAvatar(String userAvatar) {
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

  public UserUpdateMyRequest userName(String userName) {
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

  public UserUpdateMyRequest userProfile(String userProfile) {
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


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UserUpdateMyRequest userUpdateMyRequest = (UserUpdateMyRequest) o;
    return Objects.equals(this.userAvatar, userUpdateMyRequest.userAvatar) &&
        Objects.equals(this.userName, userUpdateMyRequest.userName) &&
        Objects.equals(this.userProfile, userUpdateMyRequest.userProfile);
  }

  @Override
  public int hashCode() {
    return Objects.hash(userAvatar, userName, userProfile);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class UserUpdateMyRequest {\n");
    
    sb.append("    userAvatar: ").append(toIndentedString(userAvatar)).append("\n");
    sb.append("    userName: ").append(toIndentedString(userName)).append("\n");
    sb.append("    userProfile: ").append(toIndentedString(userProfile)).append("\n");
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
