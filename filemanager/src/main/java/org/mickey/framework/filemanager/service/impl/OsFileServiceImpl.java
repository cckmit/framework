package org.mickey.framework.filemanager.service.impl;

import org.mickey.framework.core.service.GenericService;
import org.mickey.framework.filemanager.mapper.OsFileMapper;
import org.mickey.framework.filemanager.po.OsFilePo;
import org.mickey.framework.filemanager.service.IOsFileService;
import org.springframework.stereotype.Service;

/**
 * description
 *
 * @author mickey
 * 2020-02-12
 */
@Service
public class OsFileServiceImpl extends GenericService<OsFileMapper, OsFilePo> implements IOsFileService {
}
