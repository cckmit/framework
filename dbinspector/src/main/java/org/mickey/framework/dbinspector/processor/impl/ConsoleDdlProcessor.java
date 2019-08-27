package org.mickey.framework.dbinspector.processor.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.mickey.framework.dbinspector.processor.DdlProcessor;

import java.sql.Connection;
import java.util.List;

/**
 * description
 *
 * @author mickey
 * 23/07/2019
 */
@Slf4j
public class ConsoleDdlProcessor implements DdlProcessor {
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
        System.out.println(" _______  .______           __  .__   __.      _______..______    _______   ______ .___________.  ______   .______      \n" +
                "|       \\ |   _  \\         |  | |  \\ |  |     /       ||   _  \\  |   ____| /      ||           | /  __  \\  |   _  \\     \n" +
                "|  .--.  ||  |_)  |  ______|  | |   \\|  |    |   (----`|  |_)  | |  |__   |  ,----'`---|  |----`|  |  |  | |  |_)  |    \n" +
                "|  |  |  ||   _  <  |______|  | |  . `  |     \\   \\    |   ___/  |   __|  |  |         |  |     |  |  |  | |      /     \n" +
                "|  '--'  ||  |_)  |        |  | |  |\\   | .----)   |   |  |      |  |____ |  `----.    |  |     |  `--'  | |  |\\  \\----.\n" +
                "|_______/ |______/         |__| |__| \\__| |_______/    | _|      |_______| \\______|    |__|      \\______/  | _| `._____|\n" +
//				"                                                                                                                        \n");
                "");
        log.info("DB-INFO:" + connection.getMetaData().getUserName());
        System.out.println("DB-INFO:" + connection.getMetaData().getUserName());
        for (String ddl : ddlList) {
            if (StringUtils.isBlank(ddl)) {
                continue;
            }
            log.info(ddl + ";");
            System.out.println(ddl + ";");
        }
        log.info("==========================================END OF DB-INSPECTOR==========================================");
        System.out.println("==========================================END OF DB-INSPECTOR==========================================");
    }
}
