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
public class IE2bR3EstriConfigServiceTest extends BaseSpringTest {

    @Autowired
    private IE2bR3EstriConfigService service;

    @Test
    public void query() {
        print(service.query());
    }
}