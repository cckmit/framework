package database;

import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * description
 *
 * @author mickey
 * 05/07/2019
 */
@Data
public class Join {
    private Logger logger = LoggerFactory.getLogger(getClass());

    private Relation relation;
    private String fieldName;
    private String targetTableName;
    private List<JoinColumn> joinColumns;
    private Class targetEntity;

    public Join(Relation relation, String fieldName, Class targetEntity) {
        this.relation = relation;
        this.fieldName = fieldName;
        this.targetEntity = targetEntity;
    }
}
