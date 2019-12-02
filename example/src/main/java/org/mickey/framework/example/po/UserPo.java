package org.mickey.framework.example.po;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.mickey.framework.common.groups.Groups;
import org.mickey.framework.common.po.BasePo;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * description
 *
 * @author mickey
 * 23/07/2019
 */
@Slf4j
@Data
@Table(name = "mick_user")
public class UserPo extends BasePo {
    @ApiModelProperty(value = "length: 50 ; user name", required = true)
    @NotNull(message = "用户名不能为空", groups = {Groups.Insert.class, Groups.Update.class})
    @Column(columnDefinition = "varchar(50) comment 'user name'")
    private String userName;

    @ApiModelProperty(value = "length: 50 ; password", required = true, notes = "MD5加密后传输")
    @NotNull(message = "密码不能为空", groups = {Groups.Insert.class, Groups.Update.class})
    @Column(columnDefinition = "varchar(50) comment 'password'")
    private String password;

    @Column(columnDefinition = "varchar(50) comment 'nick name'")
    private String nickname;

    @ApiModelProperty(value = "length: 20 ; 电话", required = true, notes = "需要验证手机格式")
    @NotNull(message = "电话不能为空", groups = {Groups.Insert.class, Groups.Update.class})
    @Column(columnDefinition = "varchar(20) comment 'phone'")
    private String phone;

    @ApiModelProperty(value = "年龄", required = true, notes = "年龄不能为负数; 年龄不能大于150岁")
    @NotNull(message = "年龄不能为空", groups = {Groups.Insert.class, Groups.Update.class})
    @Max(value = 150, message = "年龄不能大于150岁", groups = {Groups.Insert.class, Groups.Update.class})
    @Min(value = 0, message = "年龄不能为负数", groups = {Groups.Insert.class, Groups.Update.class})
    @Column()
    private int age;
}
