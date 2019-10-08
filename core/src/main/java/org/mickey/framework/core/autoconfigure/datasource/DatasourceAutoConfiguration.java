package org.mickey.framework.core.autoconfigure.datasource;

import lombok.extern.slf4j.Slf4j;
import org.mickey.framework.common.datasource.RoutingDataSource;
import org.mickey.framework.common.util.tuple.Tuple2;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.actuate.autoconfigure.health.CompositeHealthIndicatorConfiguration;
import org.springframework.boot.actuate.autoconfigure.health.ConditionalOnEnabledHealthIndicator;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.jdbc.DataSourceHealthIndicator;
import org.springframework.boot.actuate.metrics.jdbc.DataSourcePoolMetrics;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.jdbc.metadata.DataSourcePoolMetadataProvidersConfiguration;
import org.springframework.boot.jdbc.metadata.CompositeDataSourcePoolMetadataProvider;
import org.springframework.boot.jdbc.metadata.DataSourcePoolMetadata;
import org.springframework.boot.jdbc.metadata.DataSourcePoolMetadataProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.util.*;

/**
 * description
 *
 * @author mickey
 * 23/07/2019
 */
@Slf4j
public class DatasourceAutoConfiguration {

    @Configuration
    @ConditionalOnClass({ JdbcTemplate.class, AbstractRoutingDataSource.class })
    @ConditionalOnBean(DataSource.class)
    @ConditionalOnEnabledHealthIndicator("db")
    public static class DataSourcesHealthIndicatorConfiguration extends
            CompositeHealthIndicatorConfiguration<DataSourceHealthIndicator, DataSource>
            implements InitializingBean {

        private final Map<String, DataSource> dataSources;

        private final Collection<DataSourcePoolMetadataProvider> metadataProviders;

        private DataSourcePoolMetadataProvider poolMetadataProvider;

        public DataSourcesHealthIndicatorConfiguration(
                ObjectProvider<Map<String, DataSource>> dataSources,
                ObjectProvider<Collection<DataSourcePoolMetadataProvider>> metadataProviders) {
            this.dataSources = filterDataSources(dataSources.getIfAvailable());
            this.metadataProviders = metadataProviders.getIfAvailable();
        }

        private Map<String, DataSource> filterDataSources(
                Map<String, DataSource> candidates) {
            if (candidates == null) {
                return null;
            }
            Map<String, DataSource> dataSources = new LinkedHashMap<String, DataSource>();
            for (Map.Entry<String, DataSource> entry : candidates.entrySet()) {
                if (!(entry.getValue() instanceof AbstractRoutingDataSource)) {
                    dataSources.put(entry.getKey(), entry.getValue());
                }
            }
            return dataSources;
        }

        @Override
        public void afterPropertiesSet() throws Exception {
            this.poolMetadataProvider = new CompositeDataSourcePoolMetadataProvider(this.metadataProviders);
        }

        @Bean
        public HealthIndicator dbHealthIndicator() {
            return createHealthIndicator(this.dataSources);
        }

        @Override
        protected DataSourceHealthIndicator createHealthIndicator(DataSource source) {
            DataSourceHealthIndicator dataSourceHealthIndicator;
            if(RoutingDataSource.class.isAssignableFrom(source.getClass())){
                List<DataSource> datasourceList = ((RoutingDataSource)source).getAllDataSource();
                List<Tuple2<DataSource,String>> datasources = new ArrayList<>();
                datasourceList.forEach(dataSource -> {
                    String query = getValidationQuery(dataSource);
                    datasources.add(new Tuple2<DataSource, String>(dataSource,query));
                });
                dataSourceHealthIndicator = new MultiDataSourceHealthIndicator(datasources);
            }else {
                dataSourceHealthIndicator = new DataSourceHealthIndicator(source, getValidationQuery(source));
            }
            return dataSourceHealthIndicator;
        }

        private String getValidationQuery(DataSource source) {
            DataSourcePoolMetadata poolMetadata = this.poolMetadataProvider
                    .getDataSourcePoolMetadata(source);
            return (poolMetadata == null ? null : poolMetadata.getValidationQuery());
        }

    }
}
