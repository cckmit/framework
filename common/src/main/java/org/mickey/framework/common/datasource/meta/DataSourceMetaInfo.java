package org.mickey.framework.common.datasource.meta;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.mickey.framework.common.SystemConstant;

import java.io.Serializable;
import java.util.Objects;

/**
 * description
 *
 * @author mickey
 * 23/07/2019
 */
@Slf4j
@Data
public class DataSourceMetaInfo implements Serializable {
    private String dataBaseName;
    private String driverClass;
    private String jdbcUrl;
    private String userName;
    private String password;
    private int weight;

    public void autoDetectDriverClass() {
        if (StringUtils.isBlank(this.driverClass)) {
            if (jdbcUrl.toLowerCase().startsWith(SystemConstant.jdbcUrlWithPostgresql)) {
                this.driverClass = SystemConstant.jdbcDriverClass4Postgresql;
            } else if (jdbcUrl.toLowerCase().startsWith(SystemConstant.jdbcUrlWithMysql)) {
                this.driverClass = SystemConstant.jdbcDriverClass4Mysql;
            } else if (jdbcUrl.toLowerCase().startsWith(SystemConstant.jdbcUrlWithOracle)) {
                this.driverClass = SystemConstant.jdbcDriverClass4Oracle;
            } else if (jdbcUrl.toLowerCase().startsWith(SystemConstant.jdbcUrlWithJtdsSqlServer)) {
                this.driverClass = SystemConstant.jdbcDriverClass4JtdsSqlServer;
            }
        }
    }

    public String getDatabaseKey() {
        return driverClass + ";" + jdbcUrl + ";" + userName;
    }

    public void setJdbcUrl(String jdbcUrl) {
        this.jdbcUrl = jdbcUrl;
        autoDetectDriverClass();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DataSourceMetaInfo that = (DataSourceMetaInfo) o;
        return Objects.equals(dataBaseName, that.dataBaseName)
                && Objects.equals(driverClass, that.driverClass)
                && Objects.equals(jdbcUrl, that.jdbcUrl)
                && Objects.equals(userName, that.userName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dataBaseName, driverClass, jdbcUrl, userName);
    }

    @Override
    public String toString() {
        return "DataSourceMetaInfo{" +
                ", appId='" + dataBaseName + '\'' +
                ", driverClass='" + driverClass + '\'' +
                ", jdbcUrl='" + jdbcUrl + '\'' +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
