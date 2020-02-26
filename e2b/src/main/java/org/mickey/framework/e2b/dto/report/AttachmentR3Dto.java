package org.mickey.framework.e2b.dto.report;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.Map;

/**
 * description
 *
 * @author mickey
 * 2020-02-21
 */
@Slf4j
@Data
public class AttachmentR3Dto implements IcsrDtoInterface {
    private String id;

    /**
     * 1, 发送者提交文件  2. 文献参考
     */
    String type;

    String fileDescription;
    /**
     * MediaType
     */
    String mediaType;
    /**
     * Base64 加密后的内容
     */
    String mediaContent;

    /**
     *  保存的文件夹位置
     */
    String folderId;
    /**
     *  原始文件ID  导出时，填充原始资料记录id
     */
    String fileId;

    /**
     * null flavor 信息
     */
    private Map<String, NullFlavorInfoDto> nullFlavorMap;

    @Override
    public String getCompanyId() {
        return null;
    }

    @Override
    public void setCompanyId(String companyId) {

    }

    @Override
    public String getCreateBy() {
        return null;
    }

    @Override
    public void setCreateBy(String createBy) {

    }

    @Override
    public Date getCreateTime() {
        return null;
    }

    @Override
    public void setCreateTime(Date createTime) {

    }

    @Override
    public String getUpdateBy() {
        return null;
    }

    @Override
    public void setUpdateBy(String updateBy) {

    }

    @Override
    public Date getUpdateTime() {
        return null;
    }

    @Override
    public void setUpdateTime(Date updateTime) {

    }

    @Override
    public String getReportId() {
        return null;
    }

    @Override
    public void setReportId(String reportId) {

    }

    @Override
    public Boolean getIsDelete() {
        return null;
    }

    @Override
    public void setIsDelete(Boolean isDelete) {

    }

    @Override
    public Map<String, MeddraFieldInfoDto> getMeddraMap() {
        return null;
    }

    @Override
    public void setMeddraMap(Map<String, MeddraFieldInfoDto> meddraMap) {

    }
}
