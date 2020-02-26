package org.mickey.framework.e2b.service.e2b.helper;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.mickey.framework.e2b.dto.config.HL7ConfigDto;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * description
 *
 * @author mickey
 * 2020-02-24
 */
@Slf4j
@Data
public class E2BTaskDocumentHelper {
    public static Map<String, HL7ConfigDto> e2bR3HL7ConfigMap = new LinkedHashMap<>();
}
