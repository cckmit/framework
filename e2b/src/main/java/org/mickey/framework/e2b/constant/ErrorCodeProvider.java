package org.mickey.framework.e2b.constant;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.mickey.framework.common.dto.ErrorInfo;

/**
 * description
 *
 * @author mickey
 * 2020-02-23
 */
@Slf4j
@Data
public class ErrorCodeProvider {
    public static final ErrorInfo XML_TEMPLATE_ERROR = new ErrorInfo("XML_TEMPLATE_ERROR", "错误的XML模板文件");
    public static final ErrorInfo XML_TEMPLATE_NOT_FOUND = new ErrorInfo("XML_TEMPLATE_NOT_FOUND", "找不到XML模板文件");
    public static final ErrorInfo R3_EXPORT_ERROR = new ErrorInfo("R3_EXPORT_ERROR", "导出R3报告失败");
    public static final ErrorInfo R3_SENDER_DEFAULT_MISS = new ErrorInfo("R3_SENDER_DEFAULT_MISS", "缺少默认的R3发送者");
    public static final ErrorInfo R3_EXPORT_CONFIG_MISS = new ErrorInfo("R3_EXPORT_CONFIG_MISS", "数据库中缺少R3导出配置");

    public static final ErrorInfo QUERY_COUNTRY_CODE_ERROR = new ErrorInfo("QUERY_COUNTRY_CODE_ERROR", "获取国家代码异常");

    public static final ErrorInfo COMPANY_INFO_ERROR = new ErrorInfo("COMPANY_INFO_ERROR", "没有找到相关的公司发送者信息");
}
