package autoconfigure;

import common.ORMapping;
import database.Table;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.util.ClassUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.activation.DataSource;
import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

@Configuration
@AutoConfigureAfter({DataSource.class})
@EnableConfigurationProperties({DbInspectorProperties.class})
public class DbInspectorAutoConfiguration {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private DbInspectorProperties properties;
    private ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();

    @PostConstruct
    public void inspect() {
        if (!this.properties.isEnabled()) {
            return;
        }
        doInspect();
    }

    private void doInspect() {
        List<Table> tables = scanTables();

        if (CollectionUtils.isEmpty(tables)) {
            return;
        }
    }

    private List<Table> scanTables() {
        String[] packagesToScan = StringUtils.commaDelimitedListToStringArray(this.properties.getPackagesToScan());
        if (packagesToScan == null || packagesToScan.length == 0) {
            return null;
        }
        Set<String> classNames = scanPackages(packagesToScan);
        List<Table> jpaTables = new ArrayList<Table>();
        for (String className : classNames) {
            try {
                Class<?> classzz = Class.forName(className);
                Table jpaTable = ORMapping.get(classzz);
                if (jpaTable == null) {
                    continue;
                }
                jpaTables.add(jpaTable);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return jpaTables;
    }

    private Set<String> scanPackages(String[] packagesToScan) {
        Set<String> classNames = new TreeSet<String>();
        if (packagesToScan != null) {
            try {
                for (String pkg : packagesToScan) {
                    String pattern = "classpath*:" + ClassUtils.convertClassNameToResourcePath(pkg) + "/**/*.class";
                    Resource[] resources = this.resourcePatternResolver.getResources(pattern);
                    CachingMetadataReaderFactory cachingMetadataReaderFactory = new CachingMetadataReaderFactory(this.resourcePatternResolver);
                    for(Resource resource: resources) {
                        if (resource.isReadable()) {
                            MetadataReader reader = cachingMetadataReaderFactory.getMetadataReader(resource);
                            String className = reader.getClassMetadata().getClassName();
                            if (!className.endsWith(".package-info")) {
                                AnnotationMetadata annotationMetadata = reader.getAnnotationMetadata();
                                boolean hasAnnotation = annotationMetadata.hasAnnotation(javax.persistence.Table.class.getName());
                                if (hasAnnotation) {
                                    classNames.add(reader.getClassMetadata().getClassName());
                                }
                            }
                        }
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException("Failed to scan classpath for unlisted classes", e);
            }
        }
        return classNames;
    }
}
