package org.mickey.framework.e2b.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

/**
 * description
 *
 * @author mickey
 * 2020-02-21
 */
@Slf4j
@Data
public class MeddraFieldInfo {
        private String id;

        private String description;

        private String searchText;

        private String lltName;

        private String lltCode;

        private String ptName;

        private String ptCode;

        private String hltName;

        private String hltCode;

        private String hlgtName;

        private String hlgtCode;

        private String socName;

        private String socCode;

        private String companyId;

        @ApiModelProperty(value = "字段名称")
        private String fieldName;

        @ApiModelProperty(value = "报告主键")
        private String reportId;

        @ApiModelProperty(value = "页面名称")
        private String tableName;

        private Date createTime;

        private Date updateTime;

        @ApiModelProperty(value = "meddra 版本")
        private String meddraVersion;

        @ApiModelProperty(value = "子项id")
        private String itemId;
}
