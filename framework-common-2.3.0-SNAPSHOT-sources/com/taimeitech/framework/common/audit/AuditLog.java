package com.taimeitech.framework.common.audit;

import java.io.Serializable;

/**
 * @author thomason
 * @version 1.0
 * @since 2018/5/2 下午5:33
 */
public class AuditLog implements Serializable {
	/**
	 * 应用Id
	 */
	private String appId;
	/**
	 * 租户Id
	 */
	private String tenantId;
	//------------who----------------------//
	/**
	 * token值
	 */
	private String token;
	/**
	 * 用户Id
	 */
	private String userId;
	/**
	 * 账户Id
	 */
	private String accountId;
	/**
	 * 用户名称
	 */
	private String userName;
	/**
	 * 账号名称
	 */
	private String accountName;
	/**
	 * 客户端IP
	 */
	private String userIp;
	/**
	 * 浏览器信息
	 */
	private String userAgent;
	/**
	 * 平台
	 */
	private String platform;
	/**
	 * 设备信息
	 */
	private String device;

	//--------------what----------------//
	/**
	 * 当前平台的域名
	 */
	private String serverHost;
	/**
	 * http请求method
	 */
	private String requestMethod;
	/**
	 * 请求地址
	 */
	private String requestUrl;
	/**
	 * http请求header
	 */
	private String requestHeaders;
	/**
	 * http请求参数
	 */
	private String requestParams;
	/**
	 * 是否文件上传
	 */
	private boolean isMultipartContent;
	/**
	 * 请求body
	 */
	private String requestBody;

	//----------when-----------//
	/**
	 * 请求开始时间
	 */
	private long startTime;
	/**
	 * 请求结束时间
	 */
	private long endTime;
	/**
	 * 请求耗时
	 */
	private long costInMillSeconds;

	//-----------result--------------//
	/**
	 * 请求是否成功
	 */
	private boolean successful;
	/**
	 * 错误信息
	 */
	private String error;

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getUserIp() {
		return userIp;
	}

	public void setUserIp(String userIp) {
		this.userIp = userIp;
	}

	public String getUserAgent() {
		return userAgent;
	}

	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public String getDevice() {
		return device;
	}

	public void setDevice(String device) {
		this.device = device;
	}

	public String getServerHost() {
		return serverHost;
	}

	public void setServerHost(String serverHost) {
		this.serverHost = serverHost;
	}

	public String getRequestMethod() {
		return requestMethod;
	}

	public void setRequestMethod(String requestMethod) {
		this.requestMethod = requestMethod;
	}

	public String getRequestUrl() {
		return requestUrl;
	}

	public void setRequestUrl(String requestUrl) {
		this.requestUrl = requestUrl;
	}

	public String getRequestHeaders() {
		return requestHeaders;
	}

	public void setRequestHeaders(String requestHeaders) {
		this.requestHeaders = requestHeaders;
	}

	public String getRequestParams() {
		return requestParams;
	}

	public void setRequestParams(String requestParams) {
		this.requestParams = requestParams;
	}

	public boolean isMultipartContent() {
		return isMultipartContent;
	}

	public void setMultipartContent(boolean multipartContent) {
		isMultipartContent = multipartContent;
	}

	public String getRequestBody() {
		return requestBody;
	}

	public void setRequestBody(String requestBody) {
		this.requestBody = requestBody;
	}

	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public long getEndTime() {
		return endTime;
	}

	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}

	public long getCostInMillSeconds() {
		return costInMillSeconds;
	}

	public void setCostInMillSeconds(long costInMillSeconds) {
		this.costInMillSeconds = costInMillSeconds;
	}

	public boolean isSuccessful() {
		return successful;
	}

	public void setSuccessful(boolean successful) {
		this.successful = successful;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	@Override
	public String toString() {
		return "AuditLog{" +
				"appId='" + appId + '\'' +
				", tenantId='" + tenantId + '\'' +
				", token='" + token + '\'' +
				", userId='" + userId + '\'' +
				", accountId='" + accountId + '\'' +
				", userName='" + userName + '\'' +
				", accountName='" + accountName + '\'' +
				", userIp='" + userIp + '\'' +
				", userAgent='" + userAgent + '\'' +
				", platform='" + platform + '\'' +
				", device='" + device + '\'' +
				", serverHost='" + serverHost + '\'' +
				", requestMethod='" + requestMethod + '\'' +
				", requestUrl='" + requestUrl + '\'' +
				", requestHeaders='" + requestHeaders + '\'' +
				", requestParams='" + requestParams + '\'' +
				", isMultipartContent=" + isMultipartContent +
				", requestBody='" + requestBody + '\'' +
				", startTime=" + startTime +
				", endTime=" + endTime +
				", costInMillSeconds=" + costInMillSeconds +
				", successful=" + successful +
				", error='" + error + '\'' +
				'}';
	}
}
