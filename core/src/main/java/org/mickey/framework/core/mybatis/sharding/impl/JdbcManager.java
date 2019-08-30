package org.mickey.framework.core.mybatis.sharding.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mickey.framework.common.database.Table;
import org.mickey.framework.core.mybatis.sharding.ShardingManager;
import org.mickey.framework.core.mybatis.sharding.table.po.IdMapping;
import org.mickey.framework.dbinspector.mybatis.sharding.table.po.ShardingMapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * description
 *
 * @author mickey
 * 23/07/2019
 */
@Slf4j
public class JdbcManager implements ShardingManager {

    private static final String idMappingInsertSql = "insert into t_id_mapping(table_name, id, sharding_key,sharding_value,sharding_table_name) values (?,?,?,?,?)";

    private static final String shardingMappingsInsertSql = "insert into t_sharding_mapping(table_name,sharding_key,sharding_value,sharding_table_name) values (?,?,?,?)";
    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    @SuppressWarnings("Duplicates")
    @Override
    public int saveShardingMappings(ShardingMapping... shardingMappings) {
        if (shardingMappings == null) {
            return 0;
        }
        Set<ShardingMapping> collect = Arrays.stream(shardingMappings)
                .collect(Collectors.toSet());
        try (SqlSession session = sqlSessionFactory.openSession(true)) {
            Connection connection = session.getConnection();
            int[] results = executeSqlShard(shardingMappingsInsertSql, connection, collect);
            boolean originAutoCommit = connection.getAutoCommit();
            log.debug("sharding autocommit {}", originAutoCommit);
            if (originAutoCommit) {
                connection.setAutoCommit(false);
            }
            try {
                connection.commit();
            } finally {
                connection.setAutoCommit(originAutoCommit);
            }
            if (log.isDebugEnabled()) {
                log.debug("save shardingMappings: " + Arrays.stream(results).sum());
            }
            return Arrays.stream(results).sum();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private int[] executeSqlShard(String sql, Connection connection, Set<ShardingMapping> shardingMappings) {
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            for (ShardingMapping shardingMapping : shardingMappings) {
                ps.setString(1, shardingMapping.getTableName());
                ps.setString(2, shardingMapping.getShardingKey());
                ps.setString(3, shardingMapping.getShardingValue());
                ps.setString(4, shardingMapping.getShardingTableName());
                ps.addBatch();
            }
            return ps.executeBatch();
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        return new int[]{0};
    }

    @SuppressWarnings("Duplicates")
    @Override
    public int saveIdMappings(IdMapping... idMappings) {
        if (idMappings == null) {
            return 0;
        }
        List<IdMapping> collect = Arrays.stream(idMappings).distinct().collect(Collectors.toCollection(ArrayList::new));
        try (SqlSession session = sqlSessionFactory.openSession(true)) {
            Connection connection = session.getConnection();
            int rows = 0;
            boolean originAutoCommit = connection.getAutoCommit();
            List<List<IdMapping>> batches = sliceBatch(collect, 500);
            try {
                log.debug("SaveMapping autocommit {}", originAutoCommit);
                if (originAutoCommit) {
                    connection.setAutoCommit(false);
                }
                for (List<IdMapping> batch : batches) {
                    int[] resultArr = executeSqlId(idMappingInsertSql, connection, batch);
                    int sum = Arrays.stream(resultArr).sum();
                    rows = rows + sum;
                }
                connection.commit();
            } finally {
                connection.setAutoCommit(originAutoCommit);
            }
            return rows;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private int[] executeSqlId(String sql, Connection connection, List<IdMapping> idMappings) {
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            for (IdMapping idMapping : idMappings) {
                ps.setString(1, idMapping.getTableName());
                ps.setString(2, idMapping.getId());
                ps.setString(3, idMapping.getShardingKey());
                ps.setString(4, idMapping.getShardingValue());
                ps.setString(5, idMapping.getShardingTableName());
                ps.addBatch();
            }
            return ps.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (log.isDebugEnabled()) {
                log.debug("id mappings , insert sql {}, rows {}", sql, idMappings.size());
            }
        }
        return new int[]{0};
    }

    @Override
    public String getShardingTableNameByValue(Table table, String shardingValue) {
        StringBuilder builder = new StringBuilder();
        builder.append("select table_name, sharding_table_name, sharding_key, sharding_value from t_sharding_mapping where table_name = '").append(table.getSqlName()).append("'").append(" and sharding_value = '").append(shardingValue).append("'");
        try (SqlSession session = sqlSessionFactory.openSession()) {
            Connection connection = session.getConnection();
            try (PreparedStatement prepareStatement = connection.prepareStatement(builder.toString())) {
                ResultSet resultSet = prepareStatement.executeQuery();
                ShardingMapping shardingMapping = new ShardingMapping();
                if (resultSet != null) {
                    if (resultSet.first()) {
                        String shardingTableName = resultSet.getString("sharding_table_name");
                        String shardingKey = resultSet.getString("sharding_key");
                        shardingMapping.setShardingTableName(shardingTableName);
                        shardingMapping.setShardingKey(shardingKey);
                        shardingMapping.setShardingValue(shardingValue);
                        shardingMapping.setTableName(table.getSqlName());
                        return shardingTableName;
                    }
                }
            } catch (SQLException e) {
                log.error(e.getMessage());
            }
        }
        return null;
    }

    @Override
    public String getShardingTableNameById(Table table, String id) {
        StringBuilder builder = new StringBuilder();
        builder.append("select table_name, id, sharding_key, sharding_value,sharding_table_name from t_id_mapping where table_name = '").append(table.getSqlName()).append("'").append(" and id = '").append(id).append("'");
        try (SqlSession session = sqlSessionFactory.openSession()) {
            Connection connection = session.getConnection();
            try (PreparedStatement prepareStatement = connection.prepareStatement(builder.toString())) {
                ResultSet resultSet = prepareStatement.executeQuery();
                if (resultSet != null) {
                    if (resultSet.first()) {
                        String shardingKey = resultSet.getString("sharding_key");
                        String shardingValue = resultSet.getString("sharding_value");
                        return resultSet.getString("sharding_table_name");
                    }
                }
            } catch (SQLException e) {
                log.error(e.getMessage());
            }
        }
        return null;
    }

    @Override
    public Map<String, String> getShardingTableNameByValues(Table table, Set<String> shardingValues) {
        Map<String, String> resultMap = new HashMap<>();
        if (CollectionUtils.isEmpty(shardingValues)) {
            return resultMap;
        }
        StringBuilder builder = new StringBuilder();
        builder.append("select table_name, sharding_table_name, sharding_key, sharding_value from t_sharding_mapping where table_name = '").append(table.getSqlName()).append("'")
                .append(" and sharding_value in (");
        for (String shardingValue : shardingValues) {
            builder.append("'").append(shardingValue).append("'").append(",");
        }
        builder.deleteCharAt(builder.length() - 1);
        builder.append(")");
        try (SqlSession session = sqlSessionFactory.openSession()) {
            Connection connection = session.getConnection();
            try (PreparedStatement prepareStatement = connection.prepareStatement(builder.toString())) {
                ResultSet resultSet = prepareStatement.executeQuery();
                if (resultSet != null) {
                    while (resultSet.next()) {
                        String shardingTableName = resultSet.getString("sharding_table_name");
                        String shardingValue = resultSet.getString("sharding_value");
                        resultMap.put(shardingValue, shardingTableName);
                    }
                }
                return resultMap;
            } catch (SQLException e) {
                log.error(e.getMessage());
            }
        }
        return resultMap;
    }

    @Override
    public Map<String, String> getShardingTableNameByIds(Table table, Set<String> ids) {
        Map<String, String> resultMap = new HashMap<>();
        if (CollectionUtils.isEmpty(ids)) {
            return resultMap;
        }
        StringBuilder builder = new StringBuilder();
        builder.append("select table_name, id, sharding_key, sharding_value,sharding_table_name from t_id_mapping where table_name = '").append(table.getSqlName()).append("'")
                .append(" and id in (");
        for (String id : ids) {
            builder.append("'").append(id).append("'").append(",");
        }
        builder.deleteCharAt(builder.length() - 1);
        builder.append(")");
        try (SqlSession session = sqlSessionFactory.openSession()) {
            Connection connection = session.getConnection();
            try (PreparedStatement prepareStatement = connection.prepareStatement(builder.toString())) {
                ResultSet resultSet = prepareStatement.executeQuery();
                if (resultSet != null) {
                    while (resultSet.next()) {
                        String id = resultSet.getString("id");
                        String shardingTableName = resultSet.getString("sharding_table_name");
                        resultMap.put(id, shardingTableName);
                    }
                    return resultMap;
                }
            } catch (SQLException e) {
                log.error(e.getMessage());
            }
        }
        return resultMap;
    }


    private List<List<IdMapping>> sliceBatch(List<IdMapping> poList, int batchSize) {
        int size = poList.size();
        int numOfBatch = size % batchSize == 0 ? size / batchSize : size / batchSize + 1;
        List<List<IdMapping>> batches = new ArrayList<>(numOfBatch);
        int currentEndIndex = 0;
        for (int i = 0; i < numOfBatch; i++) {
            currentEndIndex = currentEndIndex + batchSize;
            if (size <= currentEndIndex) {
                batches.add(poList.subList(i * batchSize, size));
                break;
            } else {
                batches.add(poList.subList(i * batchSize, currentEndIndex));
            }
        }
        return batches;
    }

    public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }
}