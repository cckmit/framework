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
public class DocumentRetrieval {
    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "报告ID")
    private String reportId;

    @ApiModelProperty(value = "公司ID")
    private String companyId;

    @ApiModelProperty(value = "是否删除")
    private Boolean isDelete;

    private String createBy;

    private Date createTime;

    private String updateBy;

    private Date updateTime;

    @ApiModelProperty(value = "文献标题")
    private String drTitle;

    @ApiModelProperty(value = "文献作者")
    private String drAuthor;

    @ApiModelProperty(value = "文献的期刊名称")
    private String drPeriodicalName;

    @ApiModelProperty(value = "文献的发表期")
    private String drVolume;

    @ApiModelProperty(value = "文献的发表年限")
    private String drYear;

    @ApiModelProperty(value = "文献所在期刊中的页数")
    private String drPage;
}
