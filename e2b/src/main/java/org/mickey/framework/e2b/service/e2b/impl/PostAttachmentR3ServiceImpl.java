package org.mickey.framework.e2b.service.e2b.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.mickey.framework.e2b.dto.report.AttachmentR3Dto;
import org.mickey.framework.e2b.dto.report.IcsrEntityDto;
import org.mickey.framework.e2b.service.e2b.IPostAttachmentR3Service;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * description
 *
 * @author mickey
 * 2020-02-24
 */
@Slf4j
@Service
public class PostAttachmentR3ServiceImpl implements IPostAttachmentR3Service {


    @Override
    public void fillAttachmentInfomation(IcsrEntityDto reportEntityDto) throws Exception {
        getAttachmentFromSourceFile(reportEntityDto.getReportBaseDto().getId(), reportEntityDto.getAttachmentR3DtoList());
    }

    @Override
    public List<AttachmentR3Dto> getAttachmentFromSourceFile(String reportId, List<AttachmentR3Dto> list) throws Exception {
        // 根据传递过来的原始资料的id 找到对应的文件，填充到E2B R3的导出model中
        if (CollectionUtils.isNotEmpty(list)) {
        }

        return list;
    }
}
