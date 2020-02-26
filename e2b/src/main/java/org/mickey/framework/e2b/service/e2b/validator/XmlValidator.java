package org.mickey.framework.e2b.service.e2b.validator;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import javax.xml.validation.SchemaFactory;

/**
 * description
 *
 * @author mickey
 * 23/07/2019
 */
@Data
@Slf4j
public class XmlValidator {

    public static boolean validate(String xmlPath, String xsdPaht) {
        boolean flag = false;
        String basePath = Thread.currentThread().getContextClassLoader().getResource("").getPath();

        return false;
    }
}
