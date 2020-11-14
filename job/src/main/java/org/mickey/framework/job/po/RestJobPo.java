package org.mickey.framework.job.po;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * description
 *
 * @author wangmeng
 * @version 1.0.0
 * 2020年11月11日 11:28:00
 */
@Data
@Table(name = "rest_job")
public class RestJobPo {
    @ApiModelProperty("primary id")
    @Id
    private String id;
    @ApiModelProperty("应用ID")
    private String appId;
    @ApiModelProperty("job名称")
    private String name;
    @ApiModelProperty("job分组名称")
    private String groupName;
    @ApiModelProperty("job描述")
    private String description;
    @ApiModelProperty("cron表达式；在线生成 http://cron.qqe2.com/ ")
    private String cron;
    @ApiModelProperty("GET 请求参数，拼接路径")
    private String parameter;
    @ApiModelProperty("设置为OPEN/CLOSE，默认：OPEN；且只有OPEN时，才会执行该job")
    private String status;
    @ApiModelProperty("定时回调接口")
    private String url;
    @ApiModelProperty("http 请求方式；默认：GET")
    private String httpMethod;
    @ApiModelProperty("http headers")
    private String httpHeaders;
    @ApiModelProperty("post 参数；JSON格式")
    private String context;
    @ApiModelProperty("是否删除；0，1")
    private String isDeleted;
    @ApiModelProperty("创建时间")
    private Date createTime;
    @ApiModelProperty("更新时间")
    private Date updateTime;
}
