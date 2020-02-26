package org.mickey.framework.e2b.service.config;

import org.junit.Test;
import org.mickey.framework.core.test.BaseSpringTest;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

/**
 * description
 *
 * @author mickey
 * 2020-02-21
 */
public class IE2bR3hl7ConfigServiceTest extends BaseSpringTest {

    @Autowired
    private IE2bR3hl7ConfigService service;

    @Test
    public void query() {
        print(service.query());
    }
}