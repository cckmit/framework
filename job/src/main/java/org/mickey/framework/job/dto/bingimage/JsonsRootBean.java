package org.mickey.framework.job.dto.bingimage;

import lombok.Data;

import java.util.List;

/**
 * #Description
 *
 * @author wangmeng
 * @date 2020-06-11
 */
@Data
public class JsonsRootBean {
    private List<Image> images;
    private Tooltips tooltips;
}
