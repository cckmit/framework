package org.mickey.framework.common.po;

import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * description
 *
 * @author mickey
 * 23/07/2019
 */
@Data
public abstract class BaseExtPo extends BasePo implements Serializable {
    protected Map<String, Object> extMap =new HashMap<>();

}
