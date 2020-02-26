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
public class CaseSummary {
    @ApiModelProperty(value = "主键")
    private String id;

    @ApiModelProperty(value = "病例总结和报告者的注释语言")
    private String commentLanguage;

    @ApiModelProperty(value = "报告关联字段")
    private String reportId;

    @ApiModelProperty(value = "租户Id")
    private String tenantId;

    @ApiModelProperty(value = "创建者Id")
    private String createBy;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "最后修改人Id")
    private String updateBy;

    @ApiModelProperty(value = "最后修改时间")
    private Date updateTime;

    @ApiModelProperty(value = "删除标记 0 未删除 1 删除")
    private Boolean isDeleted;

    @ApiModelProperty(value = "版本号")
    private Long version;

    @ApiModelProperty(value = "病例总结和报告者评论文本")
    private String comment;
}
