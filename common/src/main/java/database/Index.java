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
public class Index {
    private Logger logger = LoggerFactory.getLogger(getClass());

    private Table table;
    private String name;
    private String columnList;
    private boolean unique;

    public Index(Table table) {
        this.table = table;
    }
}
