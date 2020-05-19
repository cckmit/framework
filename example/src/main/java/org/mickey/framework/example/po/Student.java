package org.mickey.framework.example.po;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.mickey.framework.common.po.AbstractCommonPo;
import org.mickey.framework.example.constant.GenderEnum;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author wangmeng
 * @date 2020-05-18
 */
@Data
@Table(name = "student")
public class Student extends AbstractCommonPo {
    @ApiModelProperty(value = "姓名", required = true)
    @NotBlank(message = "姓名不可以为空")
    @Length(min = 1, max = 4, message = "姓名不少于一个字，不多于4个字")
    @Column(length = 4)
    private String name;
    @ApiModelProperty("年龄")
    @Column()
    private Integer age;
    @ApiModelProperty(value = "性别", required = true)
    @NotNull(message = "性别不可以为空")
    @Column(length = 3)
    private GenderEnum gender;
    @ApiModelProperty("身高")
    @Column()
    private Double height;
    @ApiModelProperty("体重")
    @Column()
    private Double weight;
}
