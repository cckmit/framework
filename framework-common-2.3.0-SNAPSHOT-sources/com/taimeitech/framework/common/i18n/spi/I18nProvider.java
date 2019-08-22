package com.taimeitech.framework.common.i18n.spi;

import com.taimeitech.framework.common.SystemConstant;
import com.taimeitech.framework.common.SystemContext;
import com.taimeitech.framework.common.dto.ActionResult;
import com.taimeitech.framework.common.dto.ErrorInfo;
import com.taimeitech.framework.service.BusinessException;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.util.NestedServletException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.text.MessageFormat;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


/**
 * @author thomason
 * @version 1.0
 * @since 2018/5/7 下午6:07
 */
public interface I18nProvider {
	ThreadLocal<Map<String, String>> threadLocal = new ThreadLocal<>();
	Logger logger = LoggerFactory.getLogger(I18nProvider.class);

	/**
	 * suit to omp i18n cache
	 *
	 * @param tenantId 租户Id
	 * @param appId    应用Id
	 * @param i18nKey  i18nKey
	 * @param locale   区域和语言
	 * @return i18nKey
	 */
	static String buildI18Key(String tenantId, String appId, String i18nKey, String locale) {
		return "omp:i18n:" + tenantId + "#" + appId + "#" + i18nKey + "#" + locale;
	}

	default ActionResult<Void> resolveException(String appId, Throwable ex) {
		ActionResult<Void> result = new ActionResult<>();
		result.setSuccess(false);
		try {
			if (ex instanceof BusinessException) {
				List<ErrorInfo> errors = ((BusinessException) ex).getErrors();
				if (errors != null) {
					errors.forEach(e -> {
						List<Object> arguments = e.getArguments();
						if (arguments != null) {
							e.setMessage(this.getI18nValueWithDefaultValue(appId, e.getCode() + "", arguments.toArray(new Object[0]), e.getMessage()));
						} else {
							e.setMessage(this.getI18nValueWithDefaultValue(appId, e.getCode() + "", null, e.getMessage()));
						}
					});
				}
				result.setErrors(errors);
			} else if (ex instanceof BindException) {
				BindingResult bindingResult = ((BindException) ex).getBindingResult();
				List<FieldError> fieldErrors = bindingResult.getFieldErrors();
				if (CollectionUtils.isNotEmpty(fieldErrors)) {
					fieldErrors.forEach(fieldError -> {
						ErrorInfo errorInfo = new ErrorInfo();
						String defaultMessage = fieldError.getDefaultMessage();
						errorInfo.setMessage(this.getI18nValueWithDefaultValue(appId, defaultMessage, null, defaultMessage));
						errorInfo.setCode(SystemConstant.SERVER_ERROR);
						result.addError(errorInfo);
					});
				}
			} else if (ex instanceof ConstraintViolationException) {
				Set<ConstraintViolation<?>> violationSet = ((ConstraintViolationException) ex).getConstraintViolations();
				if (CollectionUtils.isNotEmpty(violationSet)) {
					violationSet.forEach(v -> {
						ErrorInfo errorInfo = new ErrorInfo();
						errorInfo.setMessage(this.getI18nValueWithDefaultValue(appId, v.getMessage(), null, v.getMessage()));
						errorInfo.setCode(SystemConstant.SERVER_ERROR);
						result.addError(errorInfo);
					});
				}
			} else if (ex instanceof MethodArgumentNotValidException) {
				BindingResult bindingResult = ((MethodArgumentNotValidException) ex).getBindingResult();
				List<FieldError> errors = bindingResult.getFieldErrors();
				if (CollectionUtils.isNotEmpty(errors)) {
					List<ErrorInfo> errorInfoList = errors.stream().map(err -> {
						ErrorInfo errorInfo = new ErrorInfo();
						errorInfo.setCode(err.getCode());
						errorInfo.setMessage(this.getI18nValueWithDefaultValue(appId, err.getDefaultMessage(), err.getArguments(), err.getDefaultMessage()));
						return errorInfo;
					}).collect(Collectors.toList());
					result.setErrors(errorInfoList);
				}
			} else if (ex instanceof NestedServletException) {
				Throwable cause = ex.getCause();
				return resolveException(appId, cause);
			} else {
				//这里是未知异常，直接报服务器错误
				result.addError(SystemConstant.SERVER_ERROR, SystemConstant.SERVER_ERROR_TEXT);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			//此处已经获取异常信息失败了，直接返回错误消息
			result.addError(SystemConstant.SERVER_ERROR, SystemConstant.SERVER_ERROR_TEXT);
		}
		return result;
	}

	/**
	 * 取得国际化值
	 * 默认取简体中文值
	 *
	 * @param tenantId 租户Id
	 * @param appId    应用Id
	 * @param i18nKey  i18nKey
	 * @param locale   区域和语言
	 * @return 值
	 */
	String getI18nValue(String tenantId, String appId, String i18nKey, String locale);

	/**
	 * 取得国际化值
	 *
	 * @param appId        应用Id
	 * @param i18nKey      i18nKey
	 * @param args         消息的参数
	 * @param defaultValue 默认值
	 * @return 值
	 */
	default String getI18nValueWithDefaultValue(String appId, String i18nKey, Object[] args, String defaultValue) {
		String i18nValue = getI18nValue(SystemContext.getTenantId(), appId, i18nKey, SystemContext.getLocale());
		if (i18nValue != null) {
			if (args != null && args.length > 0) {
				return MessageFormat.format(i18nValue, args);
			}
			return i18nValue;
		}
		if (args != null && args.length > 0) {
			return MessageFormat.format(defaultValue, args);
		}
		return defaultValue;
	}
}
