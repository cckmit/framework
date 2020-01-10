package org.mickey.framework.common.database;

/**
 * db 类型常量
 *
 * @author mickey
 * 05/07/2019
 */
public enum DbType {
    /**
     * mysql db
     */
    mysql("com.mysql.jdbc.Driver") {
        @Override
        public String getJdbcUrl(String host, int port, String name, String connParam) {
            return "jdbc:mysql://" + host + ":" + port + "/" + name + (connParam == null ? "" : "?" + connParam);
        }
    },
    /**
     * postgres db(未经过测试)
     */
    postgres("org.postgresql.Driver") {
        @Override
        public String getJdbcUrl(String host, int port, String name, String connParam) {
            return "jdbc:postgresql://" + host + ":" + port + "/" + name;
        }
    },
    /**
     * oracle db(未经过测试)
     */
    oracle("oracle.jdbc.driver.OracleDriver") {
        @Override
        public String getJdbcUrl(String host, int port, String name, String connParam) {
            return "jdbc:oracle:thin:@//" + host + ":" + port + "/" + name;
        }
    };
    private final String driverClass;

    DbType(String driverClass) {
        this.driverClass = driverClass;
    }

    public String getDriverClass() {
        return driverClass;
    }

    public abstract String getJdbcUrl(String host, int port, String name, String connParam);
}
