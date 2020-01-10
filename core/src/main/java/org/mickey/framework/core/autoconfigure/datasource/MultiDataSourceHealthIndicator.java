package org.mickey.framework.core.autoconfigure.datasource;

import lombok.extern.slf4j.Slf4j;
import org.mickey.framework.common.util.tuple.Tuple2;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.jdbc.DataSourceHealthIndicator;
import org.springframework.boot.jdbc.DatabaseDriver;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.IncorrectResultSetColumnCountException;
import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * description
 *
 * @author mickey
 * 23/07/2019
 */
@Slf4j
public class MultiDataSourceHealthIndicator extends DataSourceHealthIndicator
        implements InitializingBean {
    private static final String DEFAULT_QUERY = "SELECT 1";

    // getE1 -> dataSource    2 -> query
    private List<Tuple2<DataSource,String>> dataSources;


    // getE1 -> jdbcTemplate    2 -> query
    private List<Tuple2<JdbcTemplate,String>> jdbcTemplates = new ArrayList<>();

    /**
     * Create a new {@link DataSourceHealthIndicator} instance.
     */
    public MultiDataSourceHealthIndicator() {
    }

    /**
     * Create a new {@link DataSourceHealthIndicator} using the specified
     * {@link DataSource} and validation query.
     * @param dataSources the data source
     *
     */
    public MultiDataSourceHealthIndicator(List<Tuple2<DataSource,String>> dataSources) {
        this.dataSources = dataSources;
        dataSources.forEach(dataSource -> {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource.getE1());
            String query = dataSource.getE2();
            jdbcTemplates.add(new Tuple2<JdbcTemplate,String>(jdbcTemplate,query));
        });
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.state(this.dataSources != null,
                "DataSource for DataSourceHealthIndicator must be specified");
    }

    @Override
    protected void doHealthCheck(Health.Builder builder) throws Exception {
        if (this.dataSources == null || dataSources.size() == 0) {
            builder.up().withDetail("database", "unknown");
        }
        else {
            doDataSourceHealthCheck(builder);
        }
    }

    private void doDataSourceHealthCheck(Health.Builder builder) throws Exception {
        jdbcTemplates.forEach(jdbcTemplate -> {
            String product = getProduct(jdbcTemplate.getE1());
            builder.up().withDetail("database", product);
            String validationQuery = getValidationQuery(product,jdbcTemplate.getE2());
            if (StringUtils.hasText(validationQuery)) {
                try {
                    // Avoid calling getObject as it breaks MySQL on Java 7
                    List<Object> results = jdbcTemplate.getE1().query(validationQuery,
                            new MultiDataSourceHealthIndicator.SingleColumnRowMapper());
                    Object result = DataAccessUtils.requiredSingleResult(results);
                    builder.withDetail("hello", result);
                }
                catch (Exception ex) {
                    builder.down(ex);
                }
            }
        });
    }

    private String getProduct(JdbcTemplate jdbcTemplate) {
        return jdbcTemplate.execute(new ConnectionCallback<String>() {
            @Override
            public String doInConnection(Connection connection)
                    throws SQLException, DataAccessException {
                return connection.getMetaData().getDatabaseProductName();
            }
        });
    }

    protected String getValidationQuery(String product, String query) {
        if (!StringUtils.hasText(query)) {
            DatabaseDriver specific = DatabaseDriver.fromProductName(product);
            query = specific.getValidationQuery();
        }
        if (!StringUtils.hasText(query)) {
            query = DEFAULT_QUERY;
        }
        return query;
    }

    /**
     * Set the {@link DataSource} to use.
     * @param dataSources the data source
     */
    public void setDataSource(List<Tuple2<DataSource,String>> dataSources) {
        this.dataSources = dataSources;
        dataSources.forEach(dataSource -> {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource.getE1());
            String query = dataSource.getE2();
            jdbcTemplates.add(new Tuple2<JdbcTemplate, String>(jdbcTemplate,query));
        });
    }


    /**
     * {@link RowMapper} that expects and returns results from a single column.
     */
    private static class SingleColumnRowMapper implements RowMapper<Object> {

        @Override
        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
            ResultSetMetaData metaData = rs.getMetaData();
            int columns = metaData.getColumnCount();
            if (columns != 1) {
                throw new IncorrectResultSetColumnCountException(1, columns);
            }
            return JdbcUtils.getResultSetValue(rs, 1);
        }

    }
}
