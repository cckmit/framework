package org.mickey.framework.e2b.service.config.impl;

import lombok.extern.slf4j.Slf4j;
import org.mickey.framework.common.query.Sort;
import org.mickey.framework.common.query.SortProperty;
import org.mickey.framework.common.query.v2.Condition;
import org.mickey.framework.common.query.v2.Criteria;
import org.mickey.framework.common.util.BeanUtil;
import org.mickey.framework.common.util.CollectionUtil;
import org.mickey.framework.e2b.dto.config.ReportConfigDto;
import org.mickey.framework.e2b.mapper.config.ImportConfigMapper;
import org.mickey.framework.e2b.po.config.ReportImportConfig;
import org.mickey.framework.e2b.service.config.IImportConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * description
 *
 * @author mickey
 * 2020-02-20
 */
@Slf4j
@Service
public class ImportConfigServiceImpl implements IImportConfigService {

    @Autowired
    ImportConfigMapper mapper;

    @Override
    public Optional<ReportConfigDto> queryByUniqueCode(String uniqueCode) {
        List<ReportImportConfig> byCriteria = mapper.findByConditionBase(
                Criteria.create()
                        .addCriterion(new Condition("tenantId", "baseCompanyId"))
                        .addCriterion(new Condition("uniqueCode", uniqueCode))
                        .addSortProperty(new SortProperty("createTime", Sort.DESC)));

        if (CollectionUtil.isNotEmpty(byCriteria)) {
            return Optional.of(BeanUtil.convert(byCriteria.get(0), ReportConfigDto.class));
        }
        return Optional.empty();
    }
}
