package org.mickey.framework.filemanager.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * description
 *
 * @author mickey
 * 2020-02-12
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UploadCallbackDto implements Serializable {
    private static final long serialVersionUID = 3622312527888460974L;

    private String bucket;
    private String etag;
    private String fileId;
    private String filePath;
    private long size;
    private String mimeType;
    private String type;
    private String fileName;

}
