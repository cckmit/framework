package org.mickey.framework.common.query;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;

/**
 * description
 *
 * @author mickey
 * 23/07/2019
 */
@Slf4j
@Data
public class SortProperty implements Serializable {
    private String propertyName;
    private Sort sort = Sort.ASC;

    public SortProperty() {
    }

    public SortProperty(String propertyName) {
        this.propertyName = propertyName;
    }

    public SortProperty(String propertyName, Sort sort) {
        this.propertyName = propertyName;
        this.sort = sort;
    }
}
