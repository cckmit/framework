package org.mickey.framework.e2b.service.e2b.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.mickey.framework.common.exception.BusinessException;
import org.mickey.framework.common.util.CollectionUtil;
import org.mickey.framework.common.util.UUIDUtils;
import org.mickey.framework.e2b.constant.ErrorCodeProvider;
import org.mickey.framework.e2b.constant.ItemUniqueCodeProvider;
import org.mickey.framework.e2b.dto.e2b.E2BR3ExportDto;
import org.mickey.framework.e2b.dto.e2b.E2BR3ExportQueryDto;
import org.mickey.framework.e2b.dto.e2b.E2BR3SingleExportDto;
import org.mickey.framework.e2b.dto.e2b.E2bR3CompanyInfoDto;
import org.mickey.framework.e2b.service.common.ITemplateService;
import org.mickey.framework.e2b.service.e2b.*;
import org.mickey.framework.e2b.service.e2b.parameter.R3CommonDataParameter;
import org.mickey.framework.filemanager.client.FileClient;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * description
 *
 * @author mickey
 * 2020-02-22
 */
@Slf4j
@Service
public class E2bExportServiceImpl implements IE2bExportService {
    @Resource
    E2BR3ConvertService e2BR3ConvertService;
    @Resource
    IPostAttachmentR3Service postAttachmentR3Service;
    @Resource
    IPostResearchR3Service postResearchR3Service;
    @Resource
    IPostPsurItemR3Service postPsurItemR3Service;
    @Resource
    ITemplateService templateService;

    private static List<String> needProcessRelatedToCodeTypeList = new ArrayList<>();
    static {
        needProcessRelatedToCodeTypeList.add(ItemUniqueCodeProvider.CHILD_CODE);
        needProcessRelatedToCodeTypeList.add(ItemUniqueCodeProvider.CONSIN_CODE);
        needProcessRelatedToCodeTypeList.add(ItemUniqueCodeProvider.SAME_PATIENT_CODE);
        needProcessRelatedToCodeTypeList.add(ItemUniqueCodeProvider.SAME_REPORTER_CODE);
        needProcessRelatedToCodeTypeList.add(ItemUniqueCodeProvider.RELATED_CODE);
    }

    @Override
    public String exportR3(E2BR3ExportQueryDto exportQueryDto) {
        return exportR3(exportQueryDto,false);
    }

    @Override
    public String exportR3(E2BR3ExportQueryDto exportQueryDto, boolean isEU) {
        byte[] bytes = exportR3File(exportQueryDto,isEU);

        String fileUri = FileClient.updateByteFileToTemp(UUIDUtils.getUuid() + ".xml", bytes);

        return fileUri;
//        ErrorInfo errorInfo ;
//        if (isEU) {
//            errorInfo = xmlValidator.validateE2BR3ContentEU(bytes);
//        } else {
//            errorInfo = xmlValidator.validateE2BR3Content(bytes);
//        }
//        FileMetaInfo render = render(bytes);
//        actionResult.setData(render);
//        if (null != errorInfo) {
//            actionResult.setErrors(Collections.singletonList(errorInfo));
//        }
//        return actionResult;
    }

    @Override
    public byte[] exportR3File(E2BR3ExportQueryDto exportQueryDto) {
        return exportR3File(exportQueryDto,false);
    }

    @Override
    public byte[] exportR3File(E2BR3ExportQueryDto exportQueryDto, boolean isEU) {
        R3CommonDataParameter r3Parameter = e2BR3ConvertService.generateR3CommonDataParameter();
        E2bR3CompanyInfoDto companyInfoDto = exportQueryDto.getR3CompanyInfoDto();

        if (companyInfoDto == null) {
            throw new BusinessException(ErrorCodeProvider.COMPANY_INFO_ERROR);
        }

        String r3ValuebyMapping = r3Parameter.getR3ValuebyMapping("C.3.1", companyInfoDto.getSenderType());
        companyInfoDto.setSenderType(r3ValuebyMapping);
        return exportR3File(exportQueryDto, companyInfoDto);
    }

    @Override
    public byte[] exportR3File(E2BR3ExportQueryDto exportQueryDto, E2bR3CompanyInfoDto companyInfo) {
        return exportR3File(exportQueryDto, companyInfo, false);
    }

    @Override
    public byte[] exportR3File(E2BR3ExportQueryDto exportQueryDto, E2bR3CompanyInfoDto companyInfoDto, boolean isEU) {
        E2BR3ExportDto exportDTO = null;
        R3CommonDataParameter r3Parameter = e2BR3ConvertService.generateR3CommonDataParameter();
        try {
            exportDTO = generateExportDTO(exportQueryDto,companyInfoDto,r3Parameter);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            return generateFreemarkerData(exportDTO,isEU);
        } catch (IOException | DocumentException e) {
            throw new BusinessException(ErrorCodeProvider.R3_EXPORT_ERROR);
        }
    }

    private byte[] generateFreemarkerData(E2BR3ExportDto report, boolean isEU) throws IOException, DocumentException {
        String tmp = System.getProperty("java.io.tmpdir");
        Path wordPath = Paths.get(tmp, UUIDUtils.getUuid() + "_e2br3.xml");
        File tempFile = wordPath.toFile();
        FileOutputStream fileOutputStream = new FileOutputStream(tempFile);
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream, "UTF-8");
        if(isEU){
            templateService.process("e2b_r3_eu/report.ftl", report, outputStreamWriter);
        }else {
            templateService.process("e2b_r3/report.ftl", report, outputStreamWriter);
        }
        fileOutputStream.close();
        outputStreamWriter.close();
        String prettyReturnValue = prettyWrite(tempFile);
        byte[] bytes = prettyReturnValue.getBytes("UTF-8");
        FileUtils.writeByteArrayToFile(tempFile, bytes);
        return bytes;
    }

    private String prettyWrite(File file) throws DocumentException, IOException {
        SAXReader xmlReader = new SAXReader();
        Document xmlDocument = xmlReader.read(file);
        OutputFormat format = OutputFormat.createPrettyPrint();
        format.setEncoding("UTF-8");
        StringWriter swriter = new StringWriter();
        XMLWriter xmlwriter = new XMLWriter(swriter, format);
        xmlwriter.write(xmlDocument);
        String returnValue = swriter.toString();
        swriter.close();
        return returnValue;
    }

    private E2BR3ExportDto generateExportDTO(E2BR3ExportQueryDto exportQueryDto, E2bR3CompanyInfoDto companyInfoDto, R3CommonDataParameter r3Parameter) throws Exception {
        E2BR3ExportDto result = new E2BR3ExportDto();
        CollectionUtil.forEach(exportQueryDto.getEntityDtoList(), entity -> {
            Map<String, List<E2BR3SingleExportDto>> data = e2BR3ConvertService.exportAllTemplateObject(r3Parameter, entity);
            result.addData(data);

            String worldWideUniqueNumber = entity.getReportBaseDto().getWorldwideUniqueNumber();
            data.get("C.1.1").get(0).putValue("N.2.r.1", worldWideUniqueNumber);
            data.get("C.1.1").get(0).putValue("C.1.1", worldWideUniqueNumber);
            companyInfoDto.setSenderSafetyReportIdentifier(worldWideUniqueNumber);
        });
        result.setCompany(companyInfoDto);
        return result;
    }
}
