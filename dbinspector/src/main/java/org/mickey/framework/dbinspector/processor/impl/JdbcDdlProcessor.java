package org.mickey.framework.dbinspector.processor.impl;

import lombok.extern.slf4j.Slf4j;
import org.mickey.framework.dbinspector.processor.DdlProcessor;

import java.sql.Connection;
import java.sql.Statement;
import java.util.List;

/**
 * description
 *
 * @author mickey
 * 23/07/2019
 */
@Slf4j
public class JdbcDdlProcessor implements DdlProcessor {
    @Override
    public void execute(Connection connection, List<String> ddlList) throws Exception {
        log.info(" _______  .______           __  .__   __.      _______..______    _______   ______ .___________.  ______   .______      \n" +
                "|       \\ |   _  \\         |  | |  \\ |  |     /       ||   _  \\  |   ____| /      ||           | /  __  \\  |   _  \\     \n" +
                "|  .--.  ||  |_)  |  ______|  | |   \\|  |    |   (----`|  |_)  | |  |__   |  ,----'`---|  |----`|  |  |  | |  |_)  |    \n" +
                "|  |  |  ||   _  <  |______|  | |  . `  |     \\   \\    |   ___/  |   __|  |  |         |  |     |  |  |  | |      /     \n" +
                "|  '--'  ||  |_)  |        |  | |  |\\   | .----)   |   |  |      |  |____ |  `----.    |  |     |  `--'  | |  |\\  \\----.\n" +
                "|_______/ |______/         |__| |__| \\__| |_______/    | _|      |_______| \\______|    |__|      \\______/  | _| `._____|\n" +
//				"                                                                                                                        \n");
                "");
        log.info("DB-INFO:" + connection.getMetaData().getURL());
        Statement statement = connection.createStatement();
        for (String ddl : ddlList) {
            statement.addBatch(ddl);
            log.info(ddl + ";");
        }
        statement.executeBatch();
        log.info("==========================================END OF DB-INSPECTOR==========================================");
    }
}
