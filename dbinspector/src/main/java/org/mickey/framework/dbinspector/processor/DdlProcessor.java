package org.mickey.framework.dbinspector.processor;

import java.sql.Connection;
import java.util.List;

/**
 * description
 *
 * @author mickey
 * 23/07/2019
 */
public interface DdlProcessor {
    void execute(Connection connection, List<String> ddlList) throws Exception;
}
