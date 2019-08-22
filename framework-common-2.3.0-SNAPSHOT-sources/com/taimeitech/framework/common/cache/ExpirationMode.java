package com.taimeitech.framework.common.cache;

public enum ExpirationMode {
	NONE, //不过期
	SLIDING, //每次动作后刷新
	ABSOLUTE
}
