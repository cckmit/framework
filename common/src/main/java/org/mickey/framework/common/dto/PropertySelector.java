package org.mickey.framework.common.dto;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * description
 *
 * @author mickey
 * 23/07/2019
 */
@Slf4j
@Data
public class PropertySelector implements Serializable {

    /**
     * 包含哪些属性
     */
    private List<String> includes;
    /**
     * 不包含哪些属性
     */
    private List<String> excludes;

    public PropertySelector addInclude(String include) {
        if (excludes != null && !excludes.isEmpty()) {
            throw new RuntimeException("you can't both assign excludes and includes");
        }
        if (this.includes == null) {
            this.includes = new ArrayList<>();
        }
        this.includes.add(include);
        return this;
    }

    public PropertySelector include(String include) {
        if (excludes != null && !excludes.isEmpty()) {
            throw new RuntimeException("you can't both assign excludes and includes");
        }
        if (this.includes == null) {
            this.includes = new ArrayList<>();
        }
        this.includes.add(include);
        return this;
    }

    public PropertySelector addExclude(String exclude) {
        if (includes != null && !includes.isEmpty()) {
            throw new RuntimeException("you can't both assign includes and excludes");
        }
        if (this.excludes == null) {
            this.excludes = new ArrayList<>();
        }
        this.excludes.add(exclude);
        return this;
    }
}
