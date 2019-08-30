package org.mickey.framework.core;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * description
 *
 * @author mickey
 * 23/07/2019
 */
@Slf4j
@Data
public class ThreadPoolProperties {
    private int coreSize = 50;
    private int maxSize = 300;
    private int queueCapacity = Integer.MAX_VALUE;
    private int keepAliveSeconds = Integer.MAX_VALUE;
}
