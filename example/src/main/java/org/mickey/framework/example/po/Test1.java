package org.mickey.framework.example.po;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.mickey.framework.common.groups.Groups;
import org.mickey.framework.common.po.BasePo;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * description
 *
 * @author mickey
 * 23/07/2019
 */
@Slf4j
@Data
@Table(name = "mick_test_1")
public class Test1 extends BasePo {
    @ApiModelProperty(value = "length: 50 ; user name", required = true)
    @NotNull(message = "用户名不能为空", groups = {Groups.Insert.class, Groups.Update.class})
    @Column(columnDefinition = "varchar(50) comment 'user name'")
    private String userName;
}
