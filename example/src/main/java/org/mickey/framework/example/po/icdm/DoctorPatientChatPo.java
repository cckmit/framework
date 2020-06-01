package org.mickey.framework.example.po.icdm;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.mickey.framework.common.po.AbstractCommonPo;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * @author wangmeng
 * @date 2020-05-27
 */
@Data
@Table(name = "doctor_patient_chat")
public class DoctorPatientChatPo extends AbstractCommonPo {
    @ApiModelProperty("相关随访id")
    @Column(length = 50)
    private String flupId;
    @ApiModelProperty("发送者")
    @Column(length = 40)
    private String sender;
    @ApiModelProperty("接收者")
    @Column(length = 40)
    private String receiver;
    @ApiModelProperty("消息头（暂时没用）")
    @Column()
    private String title;
    @ApiModelProperty("消息内容")
    @Column()
    private String message;
    @ApiModelProperty("状态（已读 1 / 未读 0）")
    @Column(length = 1)
    private Integer status;
}
