package database;

import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * description
 *
 * @author mickey
 * 05/07/2019
 */
@Data
public class ForeignKey {
    private Logger logger = LoggerFactory.getLogger(getClass());

    private final String pkTable;
    private final String fkTable;
    private final String pkColumn;
    private final int seq;
    private final String fkColumn;

    public ForeignKey(String pkTable, String fkTable, String pkColumn, int seq, String fkColumn) {
        this.pkTable = pkTable;
        this.fkTable = fkTable;
        this.pkColumn = pkColumn;
        this.seq = seq;
        this.fkColumn = fkColumn;
    }
}
