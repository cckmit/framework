package org.mickey.framework.example.po;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.mickey.framework.common.po.BasePo;

import javax.persistence.Column;
import javax.persistence.Table;

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
    @Column(columnDefinition = "varchar(50) comment 'user name'")
    private String userName;
    @Column(columnDefinition = "varchar(50) comment 'password'")
    private String password;
    @Column(columnDefinition = "varchar(50) comment 'nick name'")
    private String nickname;
    @Column(columnDefinition = "varchar(50) comment 'phone'")
    private String phone;
    @Column()
    private int age;
}
