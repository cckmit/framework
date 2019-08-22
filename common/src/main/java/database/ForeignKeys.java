package database;

import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.TreeMap;

/**
 * description
 *
 * @author mickey
 * 05/07/2019
 */
@Data
public class ForeignKeys {
    private Logger logger = LoggerFactory.getLogger(getClass());

    private final Table pkTable;
    private final TreeMap<String, List<ForeignKey>> keys;
}
