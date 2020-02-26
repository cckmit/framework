package org.mickey.framework.e2b.service.common;

import org.mickey.framework.e2b.dto.e2b.E2BR3ExportDto;

import java.io.OutputStreamWriter;

/**
 * description
 *
 * @author mickey
 * 2020-02-22
 */
public interface ITemplateService {
    void process(String s, E2BR3ExportDto report, OutputStreamWriter outputStreamWriter);
}
