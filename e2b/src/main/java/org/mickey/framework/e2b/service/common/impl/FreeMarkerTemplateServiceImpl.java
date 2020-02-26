package org.mickey.framework.e2b.service.common.impl;

import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.mickey.framework.common.exception.BusinessException;
import org.mickey.framework.e2b.constant.ErrorCodeProvider;
import org.mickey.framework.e2b.dto.e2b.E2BR3ExportDto;
import org.mickey.framework.e2b.service.common.ITemplateService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * description
 *
 * @author mickey
 * 2020-02-23
 */
@Slf4j
@Data
@Service
public class FreeMarkerTemplateServiceImpl implements ITemplateService {
    @Resource
    private Configuration configuration;

    @Override
    public void process(String templateFileName, E2BR3ExportDto dto, OutputStreamWriter writer) {
        Template template = getTemplate(templateFileName);
        try {
            template.process(dto, writer);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException(ErrorCodeProvider.XML_TEMPLATE_ERROR);
        }
    }

    private Template getTemplate(String templateFileName) {
        try {
            return configuration.getTemplate(templateFileName);
        } catch (IOException e) {
            e.printStackTrace();
            throw new BusinessException(ErrorCodeProvider.XML_TEMPLATE_NOT_FOUND);
        }
    }
}
