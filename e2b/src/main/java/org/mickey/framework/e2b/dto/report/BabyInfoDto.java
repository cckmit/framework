package org.mickey.framework.e2b.dto.report;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.mickey.framework.e2b.entity.BabyInfo;

import java.util.Map;

/**
 * description
 *
 * @author mickey
 * 2020-02-21
 */
@Slf4j
@Data
public class BabyInfoDto extends BabyInfo implements IcsrDtoInterface {
    Map<String, MeddraFieldInfoDto> meddraMap;
    Map<String, NullFlavorInfoDto> nullFlavorMap;

}
