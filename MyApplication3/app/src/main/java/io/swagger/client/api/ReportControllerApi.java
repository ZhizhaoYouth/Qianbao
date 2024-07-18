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


package io.swagger.client.api;

import io.swagger.client.ApiCallback;
import io.swagger.client.ApiClient;
import io.swagger.client.ApiException;
import io.swagger.client.ApiResponse;
import io.swagger.client.Configuration;
import io.swagger.client.Pair;
import io.swagger.client.ProgressRequestBody;
import io.swagger.client.ProgressResponseBody;

import com.google.gson.reflect.TypeToken;

import java.io.IOException;


import io.swagger.client.model.BaseResponseReportVO;
import io.swagger.client.model.ReportQueryRequest;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReportControllerApi {
    private ApiClient apiClient;

    public ReportControllerApi() {
        this(Configuration.getDefaultApiClient());
    }

    public ReportControllerApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Build call for getReportUsingPOST
     * @param reportQueryRequest reportQueryRequest (required)
     * @param progressListener Progress listener
     * @param progressRequestListener Progress request listener
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public okhttp3.Call getReportUsingPOSTCall(ReportQueryRequest reportQueryRequest, final ProgressResponseBody.ProgressListener progressListener, final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {
        Object localVarPostBody = reportQueryRequest;

        // create path and map variables
        String localVarPath = "/api/report/generate";

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();

        Map<String, String> localVarHeaderParams = new HashMap<String, String>();

        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        final String[] localVarAccepts = {
            "*/*"
        };
        final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) localVarHeaderParams.put("Accept", localVarAccept);

        final String[] localVarContentTypes = {
            "application/json"
        };
        final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);

        if(progressListener != null) {
            apiClient.getHttpClient().networkInterceptors().add(new okhttp3.Interceptor() {
                @Override
                public okhttp3.Response intercept(okhttp3.Interceptor.Chain chain) throws IOException {
                    okhttp3.Response originalResponse = chain.proceed(chain.request());
                    return originalResponse.newBuilder()
                    .body(new ProgressResponseBody(originalResponse.body(), progressListener))
                    .build();
                }
            });
        }

        String[] localVarAuthNames = new String[] {  };
        return apiClient.buildCall(localVarPath, "POST", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAuthNames, progressRequestListener);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call getReportUsingPOSTValidateBeforeCall(ReportQueryRequest reportQueryRequest, final ProgressResponseBody.ProgressListener progressListener, final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {
        
        // verify the required parameter 'reportQueryRequest' is set
        if (reportQueryRequest == null) {
            throw new ApiException("Missing the required parameter 'reportQueryRequest' when calling getReportUsingPOST(Async)");
        }
        

        okhttp3.Call call = getReportUsingPOSTCall(reportQueryRequest, progressListener, progressRequestListener);
        return call;

    }

    /**
     * getReport
     * 
     * @param reportQueryRequest reportQueryRequest (required)
     * @return BaseResponseReportVO
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public BaseResponseReportVO getReportUsingPOST(ReportQueryRequest reportQueryRequest) throws ApiException {
        ApiResponse<BaseResponseReportVO> resp = getReportUsingPOSTWithHttpInfo(reportQueryRequest);
        return resp.getData();
    }

    /**
     * getReport
     * 
     * @param reportQueryRequest reportQueryRequest (required)
     * @return ApiResponse&lt;BaseResponseReportVO&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public ApiResponse<BaseResponseReportVO> getReportUsingPOSTWithHttpInfo(ReportQueryRequest reportQueryRequest) throws ApiException {
        okhttp3.Call call = getReportUsingPOSTValidateBeforeCall(reportQueryRequest, null, null);
        Type localVarReturnType = new TypeToken<BaseResponseReportVO>(){}.getType();
        return apiClient.execute(call, localVarReturnType);
    }

    /**
     * getReport (asynchronously)
     * 
     * @param reportQueryRequest reportQueryRequest (required)
     * @param callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     */
    public okhttp3.Call getReportUsingPOSTAsync(ReportQueryRequest reportQueryRequest, final ApiCallback<BaseResponseReportVO> callback) throws ApiException {

        ProgressResponseBody.ProgressListener progressListener = null;
        ProgressRequestBody.ProgressRequestListener progressRequestListener = null;

        if (callback != null) {
            progressListener = new ProgressResponseBody.ProgressListener() {
                @Override
                public void update(long bytesRead, long contentLength, boolean done) {
                    callback.onDownloadProgress(bytesRead, contentLength, done);
                }
            };

            progressRequestListener = new ProgressRequestBody.ProgressRequestListener() {
                @Override
                public void onRequestProgress(long bytesWritten, long contentLength, boolean done) {
                    callback.onUploadProgress(bytesWritten, contentLength, done);
                }
            };
        }

        okhttp3.Call call = getReportUsingPOSTValidateBeforeCall(reportQueryRequest, progressListener, progressRequestListener);
        Type localVarReturnType = new TypeToken<BaseResponseReportVO>(){}.getType();
        apiClient.executeAsync(call, localVarReturnType, callback);
        return call;
    }
}
