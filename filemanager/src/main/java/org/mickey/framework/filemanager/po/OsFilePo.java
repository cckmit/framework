package org.mickey.framework.filemanager.po;

import lombok.Data;
import org.mickey.framework.common.po.BasePo;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * description
 *
 * @author mickey
 * 2020-02-06
 */
@Table(name = "os_file")
@Data
public class OsFilePo extends BasePo {
    @Column(columnDefinition = "varchar(500) null comment '文件原始名称'")
    private String fileName;
    @Column(columnDefinition = "varchar(500) null comment '文件服务器保存名称'")
    private String serverName;
    @Column(columnDefinition = "varchar(500) null comment '文件保存所在路径（基于文档服务root路径）'")
    private String filePath;
    @Column(columnDefinition = "varchar(1000) null comment '文件链接'")
    private String url;
    @Column(columnDefinition = "varchar(20) null comment '文件后缀'")
    private String suffix;
    @Column(columnDefinition = "varchar(50) null comment '文件media type'")
    private String mediaType;
    @Column(columnDefinition = "bigint null comment '文件大小'")
    private Long fileSize;

    @Column(columnDefinition = "varchar(50) null comment '存储库标记(COS、OSS)'")
    private String bucketFlag;
    @Column(columnDefinition = "varchar(50) null comment '存储库'")
    private String bucket;
    @Column(columnDefinition = "varchar(50) null comment '文件md5标记'")
    private String etag;
    @Column(columnDefinition = "varchar(50) null comment '存储库所在地区'")
    private String region;
}
