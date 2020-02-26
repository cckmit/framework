package org.mickey.framework.e2b.service.e2b;

import org.mickey.framework.e2b.dto.report.AttachmentR3Dto;
import org.mickey.framework.e2b.dto.report.IcsrEntityDto;

import java.util.List;

/**
 * description
 *
 * @author mickey
 * 2020-02-22
 */
public interface IPostAttachmentR3Service {
    void fillAttachmentInfomation(IcsrEntityDto reportEntityDto) throws Exception;

    List<AttachmentR3Dto> getAttachmentFromSourceFile(String reportId, List<AttachmentR3Dto> list) throws Exception;
}
