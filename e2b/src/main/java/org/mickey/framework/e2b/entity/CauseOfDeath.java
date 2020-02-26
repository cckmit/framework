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
public class CauseOfDeath {
    private String id;

    @ApiModelProperty(value = "术语类型")
    private String type;

    @ApiModelProperty(value = "序号")
    private Integer sequence;

    private Boolean isDelete;

    private String reportId;

    @ApiModelProperty(value = "死亡原因")
    private String causeOfDeath;

    private Date createTime;

    private Date lastUpdateTime;
}
