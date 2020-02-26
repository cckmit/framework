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
public class BabyInfo {
    private String id;

    @ApiModelProperty(value = "关联报告id主键")
    private String reportId;

    @ApiModelProperty(value = "新生儿性别")
    private String babySex;

    @ApiModelProperty(value = "新生儿身高(cm)")
    private String babyHeight;

    @ApiModelProperty(value = "出生体重")
    private String birthWeight;

    @ApiModelProperty(value = "APGAR评分1分钟")
    private String aminuteOne;

    @ApiModelProperty(value = "APGAR评分5分钟")
    private String aminuteFive;

    @ApiModelProperty(value = "APGAR评分10分钟")
    private String aminuteTen;

    @ApiModelProperty(value = "母乳喂养")
    private String breastFeeding;

    @ApiModelProperty(value = "创建者Id")
    private String createBy;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "最后修改人Id")
    private String updateBy;

    @ApiModelProperty(value = "最后修改时间")
    private Date updateTime;

    @ApiModelProperty(value = "删除标记 0 未删除 1 删除")
    private Boolean isDelete;

    private String companyId;
}
