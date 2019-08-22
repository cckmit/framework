package com.taimeitech.framework.util;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class RequestUtils {
	private final static String[] AGENT = {"Android", "iPhone", "iPod", "iPad", "Windows Phone", "MQQBrowser"};
	private static Logger logger = LoggerFactory.getLogger(RequestUtils.class);

	public static boolean isMobile(HttpServletRequest request) {
		String ua = request.getHeader("user-agent");
		if (StringUtils.isBlank(ua)) {
			return false;
		}
		boolean flag = false;
		if (!ua.contains("Windows NT") || (ua.contains("Windows NT") && ua.contains("compatible; MSIE 9.0;"))) {
			// 排除 苹果桌面系统
			if (!ua.contains("Windows NT") && !ua.contains("Macintosh")) {
				for (String item : AGENT) {
					if (ua.contains(item)) {
						flag = true;
						break;
					}
				}
			}
		}
		return flag;
	}

	public static boolean isMultipartContent(HttpServletRequest request) {
		try {
			if (!"POST".equalsIgnoreCase(request.getMethod())) {
				return false;
			}
			String contentType = request.getContentType();
			if (contentType == null) {
				return false;
			}
			return contentType.toLowerCase(Locale.ENGLISH).startsWith("multipart/");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return false;
		}
	}

	public static String getRequestParamter(HttpServletRequest request) {
		Map<String, String[]> parameterMap = request.getParameterMap();
		if (parameterMap != null && parameterMap.size() > 0) {
			StringBuilder builder = new StringBuilder();
			builder.append("{");
			for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
				String k = entry.getKey();
				String[] v = entry.getValue();
				builder.append("\"").append(k).append("\"").append(":");
				if (v != null && v.length > 1) {
					builder.append("[");
					for (String s : v) {
						builder.append("\"").append(s).append("\"");
						builder.append(",");
					}
					builder.deleteCharAt(builder.length() - 1);
					builder.append("]");
				} else {
					builder.append("\"").append(v == null || v.length == 0 ? "" : v[0]).append("\"");
				}
				builder.append(",");
			}
			if (builder.length() > 1) {
				builder.deleteCharAt(builder.length() - 1);
			}
			builder.append("}");
			return builder.toString();
		}
		return null;
	}

	public static String getRequestHeader(HttpServletRequest request) {
		Enumeration<String> headerNames = request.getHeaderNames();
		if (headerNames != null) {
			StringBuilder builder = new StringBuilder();
			builder.append("{");
			while (headerNames.hasMoreElements()) {
				String header = headerNames.nextElement();
				builder.append("\"").append(header).append("\"").append(":")
						.append("\"")
						.append(request.getHeader(header))
						.append("\"")
						.append(",");
			}
			if (builder.length() > 1) {
				builder.deleteCharAt(builder.length() - 1);
			}
			builder.append("}");
			return builder.toString();
		}
		return null;
	}

	/**
	 * 获取客户端的ip地址
	 *
	 * @param request 请求对象
	 * @return ip地址
	 */
	public static String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	/**
	 * 构建查询条件
	 *
	 * @return
	 */
	public static Map<String, String> buildQueryParam(HttpServletRequest request) {
		Map<String, String> map = new HashMap<String, String>();
		for (Object o : request.getParameterMap().keySet()) {
			String key = o.toString();
			String[] parameterValues = request.getParameterValues(key);
			if (parameterValues.length == 0) {
				map.put(key, parameterValues[0]);
			} else {
				StringBuilder builder = new StringBuilder();
				for (String parameterValue : parameterValues) {
					builder.append(",");
					builder.append(parameterValue);
				}
				builder.deleteCharAt(0);
				map.put(key, builder.toString());
			}
		}
		return map;
	}

	public static String getRequestURI(HttpServletRequest request) {
		String requestURI = request.getRequestURI();
		if (requestURI.contains("?")) {
			requestURI = requestURI.substring(0, requestURI.indexOf("?"));
		}
		return requestURI;
	}


//	public static String getInputStreamAsString(HttpServletRequest request) throws IOException {
//		byte[] bytes = new byte[1024 * 1024];
//		InputStream is = request.getInputStream();
//		int nRead = 1;
//		int nTotalRead = 0;
//		while (nRead > 0) {
//			nRead = is.read(bytes, nTotalRead, bytes.length - nTotalRead);
//			if (nRead > 0)
//				nTotalRead = nTotalRead + nRead;
//		}
//		String str = new String(bytes, 0, nTotalRead, "utf-8");
//		if (StringUtils.isEmpty(str)) {
//			return null;
//		}
//		return URLDecoder.decode(str, "utf-8");
//	}

//	public static byte[] getInputStream(HttpServletRequest request) throws IOException {
//		ServletInputStream inputStream = request.getInputStream();
//		ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
//		byte[] buffer = new byte[1024];
//		int len = -1;
//		try {
//			while ((len = inputStream.read(buffer)) != -1) {
//				outSteam.write(buffer, 0, len);
//			}
//		} finally {
//			outSteam.close();
//			inputStream.close();
//		}
//		return outSteam.toByteArray();
//	}

//	public static byte[] getRequestFile(HttpServletRequest request, String fileName) {
//		DiskFileItemFactory fac = new DiskFileItemFactory();
//		ServletFileUpload upload = new ServletFileUpload(fac);
//		try {
//			List<FileItem> fileItems = upload.parseRequest(request);
//			if (fileItems != null) {
//				for (FileItem fileItem : fileItems) {
//					if (fileItem.isFormField()) {
//						continue;
//					}
//					String name = fileItem.getName();
//					if (StringUtils.isBlank(name)) {
//						continue;
//					}
//					if (StringUtils.isNotEmpty(fileName) && fileName.equals(fileItem.getFieldName())) {
//						return fileItem.get();
//					}
//					return fileItem.get();
//				}
//			}
//		} catch (FileUploadException e) {
//			e.printStackTrace();
//		}
//		return null;
//	}

//	/**
//	 * Convenience method to get the application's URL based on request
//	 * variables.
//	 */
//	public static String getAppURL(HttpServletRequest request) {
//		StringBuilder url = new StringBuilder();
//		int port = request.getServerPort();
//		if (port < 0) {
//			port = 80; // Work around java.net.URL bug
//		}
//		String scheme = request.getScheme();
//		url.append(scheme);
//		url.append("://");
//		url.append(request.getServerName());
//		if ((scheme.equals("http") && (port != 80))
//				|| (scheme.equals("https") && (port != 443))) {
//			url.append(':');
//			url.append(port);
//		}
//		url.append(request.getContextPath());
//		return url.toString();
//	}
}
