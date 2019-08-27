package org.mickey.framework.dbinspector.autoconfigure;

import lombok.extern.slf4j.Slf4j;
import org.mickey.framework.dbinspector.processor.DdlProcessor;
import org.mickey.framework.dbinspector.processor.impl.ConsoleDdlProcessor;
import org.mickey.framework.dbinspector.processor.impl.JdbcDdlProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * description
 *
 * @author mickey
 * 23/07/2019
 */
@Configuration
public class DdlProcessorConfigure {
    @Bean
    public DdlProcessor jdbcDdlProcessor() {
        return new JdbcDdlProcessor();
    }

    @Bean
    public DdlProcessor consoleDdlProcessor() {
        return new ConsoleDdlProcessor();
    }
}
