package com.taimeitech.framework.common.web;

import org.apache.commons.io.IOUtils;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author thomason
 * @version 1.0
 * @since 2018/4/29 下午12:14
 */
public final class MutableHttpServletRequest extends HttpServletRequestWrapper {
	//原始的请求body
	private final byte[] _body;
	//原始的请求参数
	private final Map<String, String[]> _parameterMap;
	//原始的请求url
	private final String _requestURI;

	//改变后的请求body
	private boolean bodyChanged = false;
	private byte[] body;
	//改变后的请求参数
	private boolean parameterChanged = false;
	private Map<String, String[]> parameterMap;
	//改变后的请求url
	private boolean uriChanged = false;
	private String requestURI;

	private ResettableServletInputStream servletStream;

	private Lock lock = new ReentrantLock();

	public MutableHttpServletRequest(HttpServletRequest request) throws IOException {
		super(request);
		ServletInputStream inputStream = request.getInputStream();
		if (inputStream != null) {
			this._body = IOUtils.toByteArray(inputStream);
			this.servletStream = new ResettableServletInputStream();
			this.servletStream.stream = new ByteArrayInputStream(_body);
		} else {
			this._body = null;
		}
		this._parameterMap = request.getParameterMap();
		this._requestURI = request.getRequestURI();
	}

	@Override
	public Map<String, String[]> getParameterMap() {
		if (parameterChanged) {
			return parameterMap;
		} else {
			return _parameterMap;
		}
	}

	public final void setParameterMap(Map<String, String[]> parameterMap) {
		this.parameterChanged = true;
		prepareParameter();
		this.parameterMap.putAll(parameterMap);
	}

	@Override
	public String getParameter(String name) {
		if (parameterChanged) {
			String[] values = parameterMap.get(name);
			if (values != null) {
				return values[0];
			}
			return null;
		} else {
			return super.getParameter(name);
		}
	}

	@Override
	public String[] getParameterValues(String name) {
		if (parameterChanged) {
			return parameterMap.get(name);
		} else {
			return super.getParameterValues(name);
		}
	}

	@Override
	public String getRequestURI() {
		if (uriChanged) {
			return requestURI;
		} else {
			return super.getRequestURI();
		}
	}

	public final void setRequestURI(String requestURI) {
		this.requestURI = requestURI;
		this.uriChanged = true;
	}

	@Override
	public StringBuffer getRequestURL() {
		StringBuffer url = new StringBuffer();
		String scheme = getScheme();
		int port = getServerPort();
		if (port < 0) {
			port = 80; // Work around java.net.URL bug
		}

		url.append(scheme);
		url.append("://");
		url.append(getServerName());
		if (("http".equals(scheme) && (port != 80))
				|| ("https".equals(scheme) && (port != 443))) {
			url.append(':');
			url.append(port);
		}
		url.append(getRequestURI());

		return url;
	}

	@Override
	public ServletInputStream getInputStream() throws IOException {
		return servletStream;
	}

	public InputStream getInputStreamRepeatedly() {
		if (bodyChanged) {
			return new ByteArrayInputStream(body);
		} else {
			return new ByteArrayInputStream(_body);
		}
	}

	public final void setBody(byte[] body) {
		this.body = body;
		this.bodyChanged = true;
		this.servletStream.stream = new ByteArrayInputStream(body);
	}

	public final void setParameter(String name, String value) {
		this.parameterChanged = true;
		prepareParameter();
		parameterMap.put(name, new String[]{value});
	}

	public final void setParameter(String name, String[] value) {
		this.parameterChanged = true;
		prepareParameter();
		parameterMap.put(name, value);
	}

	private void prepareParameter() {
		try {
			if (parameterMap != null) {
				return;
			}
			lock.lock();
			if (parameterMap == null) {
				parameterMap = new HashMap<>();
				parameterMap.putAll(_parameterMap);
			}
		} finally {
			lock.unlock();
		}
	}

	@Override
	public BufferedReader getReader() throws IOException {
		return new BufferedReader(new InputStreamReader(servletStream));
	}

	private class ResettableServletInputStream extends ServletInputStream {

		private InputStream stream;

		@Override
		public int read() throws IOException {
			return stream.read();
		}

		@Override
		public boolean isFinished() {
			return true;
		}

		@Override
		public boolean isReady() {
			return true;
		}

		@Override
		public void setReadListener(ReadListener listener) {
		}
	}
}
