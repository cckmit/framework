package org.mickey.framework.common.mybatis;

import lombok.extern.slf4j.Slf4j;

import java.util.HashSet;
import java.util.Set;

/**
 * description
 *
 * @author mickey
 * 23/07/2019
 */
@Slf4j
public class MybatisStmtIdContext {
    private static final Set<String> ignoreTenantStmtIdSet = new HashSet<>();

    public static void addIgnoreTenantStmtId(String stmtId) {
        ignoreTenantStmtIdSet.add(stmtId);
    }

    public static boolean isIgnoreTenant(String stmtId) {
        return ignoreTenantStmtIdSet.contains(stmtId);
    }
}
