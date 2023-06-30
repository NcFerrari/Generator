package generator;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.NativeQuery;
import generator.service.FileService;
import generator.service.LoggerService;
import generator.serviceimpl.FileServiceImpl;
import generator.serviceimpl.LoggerServiceImpl;
import generator.utils.TextEnum;
import generator.utils.TextFormat;
import generator.utils.jpa.Column;
import generator.utils.jpa.DataTypeEnum;
import generator.utils.jpa.JPAType;
import generator.utils.jpa.ValidatedColumns;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Use it by calling cunstructor with parametr (database schema name);
 */
public class JPACreator {

    private static final String CONFIG_FILE = TextEnum.HIBERNATE_CONFIG_FILE.getText();

    private final LoggerService loggerService = LoggerServiceImpl.getInstance(JPACreator.class);
    private final Logger log = loggerService.getLog();
    private final FileService fileService = FileServiceImpl.getInstance();

    public JPACreator(String db) {
        List<ValidatedColumns> schemaList = setMapDataFromDB(loadTableStructure(db));
        schemaList.forEach(validatedColumn -> {
            generateEntity(validatedColumn);
            generateDtoFiles(validatedColumn);
            generateDaoFile(validatedColumn);
            generateDaoImplFile(validatedColumn);
        });
        generateManagerEntity(db);
    }

    public void generateEntity(ValidatedColumns validatedColumns) {
        String classDefinition = createClassDefinition(validatedColumns, JPAType.ENTITY);
        String fileName = validatedColumns.getTableName() +
                TextEnum.UNDERSCORE_MARK.getText() +
                JPAType.ENTITY.getTitle();
        fileService.createAndFillJavaFile(
                TextFormat.capitalizeOrCamelCaseText(fileName, false) + TextEnum.JAVA_SUFFIX.getText(),
                JPAType.ENTITY.getPath(),
                classDefinition);
    }

    public void generateDtoFiles(ValidatedColumns validatedColumns) {
        String classDefinition = createClassDefinition(validatedColumns, JPAType.DTO);
        fileService.createAndFillJavaFile(
                TextFormat.capitalizeOrCamelCaseText(validatedColumns.getTableName(), false) +
                        TextEnum.JAVA_SUFFIX.getText(),
                JPAType.DTO.getPath(),
                classDefinition);
    }

    public void generateDaoFile(ValidatedColumns validatedColumns) {
        String classDefinition = createClassDefinition(validatedColumns, JPAType.DAO);
        String fileName = TextFormat.capitalizeOrCamelCaseText(validatedColumns.getTableName(), false) +
                JPAType.DAO.getTitle() + TextEnum.JAVA_SUFFIX.getText();
        fileService.createAndFillJavaFile(fileName, JPAType.DAO.getPath(), classDefinition);
    }

    public void generateDaoImplFile(ValidatedColumns validatedColumns) {
        String classDefinition = createClassDefinition(validatedColumns, JPAType.DAO_IMPL);
        String fileName = TextFormat.capitalizeOrCamelCaseText(validatedColumns.getTableName(), false) +
                JPAType.DAO_IMPL.getTitle() + TextEnum.JAVA_SUFFIX.getText();
        fileService.createAndFillJavaFile(fileName, JPAType.DAO_IMPL.getPath(), classDefinition);
    }

    public void generateManagerEntity(String db) {
        ValidatedColumns validatedColumns = new ValidatedColumns("");
        validatedColumns.setDatabaseName(db);
        String classDefinition = createClassDefinition(validatedColumns, JPAType.ENTITY_MANAGER);
        fileService.createAndFillJavaFile(
                JPAType.ENTITY_MANAGER.getTitle() + TextEnum.JAVA_SUFFIX.getText(),
                JPAType.ENTITY_MANAGER.getPath(),
                classDefinition);
    }

    private List<Object[]> loadTableStructure(String db) {
        SessionFactory factory = new Configuration().configure(CONFIG_FILE).buildSessionFactory();
        Session session = factory.getCurrentSession();
        session.beginTransaction();
        @SuppressWarnings("unchecked")
        NativeQuery<Object[]> query = session.createNativeQuery(TextEnum.SQL_SELECT_FROM_IS.getText());
        query.setParameter(TextEnum.SQL_DB_PARAMETER.getText(), db);
        List<Object[]> list = query.getResultList();
        session.getTransaction().commit();
        session.close();
        factory.close();
        return list;
    }

    private List<ValidatedColumns> setMapDataFromDB(List<Object[]> list) {
        Map<String, ValidatedColumns> resultMap = new HashMap<>();
        list.forEach(object -> {
            resultMap.putIfAbsent(object[0].toString(), new ValidatedColumns(object[0].toString()));
            ValidatedColumns validatedColumns = resultMap.get(object[0].toString());
            String dataType = processingAndRefinementDataType(object[2].toString());
            Column newColumn = new Column(object[1].toString(), dataType);
            if (!validatedColumns.isPrimaryKeyIncluded() &&
                    TextEnum.PRI.getText().equals(object[3]) &&
                    !DataTypeEnum.VARCHAR.name().equals(dataType.toUpperCase())) {
                validatedColumns.addAsFirst(newColumn);
            } else {
                validatedColumns.add(newColumn);
            }
            if (TextEnum.DATE_TIME.getText().equals(dataType) || TextEnum.TIME.getText().equals(dataType)) {
                validatedColumns.hasDateTimeIncluded();
            } else if (TextEnum.DATE.getText().equals(dataType)) {
                validatedColumns.hasDateIncluded();
            }
        });
        return resultMap.values().stream()
                .filter(ValidatedColumns::isPrimaryKeyIncluded)
                .collect(Collectors.toList());
    }

    private String createClassDefinition(final ValidatedColumns validatedColumns, JPAType jpaType) {
        log.info(String.format(TextEnum.GENERATE_LOG_FORMAT.getText(),
                TextEnum.GENERATE_LOG_TEXT.getText(),
                jpaType.getTitle(),
                validatedColumns.getTableName()));
        String classDefinition = TextEnum.EMPTY_MARK.getText();
        classDefinition += String.format(TextEnum.PACKAGE_TEXT.getText(), jpaType.getPackageName());
        classDefinition += generateImports(validatedColumns, jpaType);
        classDefinition += generateClassHead(validatedColumns.getTableName(), jpaType);
        classDefinition += generateClassBody(validatedColumns, jpaType);
        return classDefinition;
    }

    private String generateImports(ValidatedColumns validatedColumns, JPAType jpaType) {
        StringBuilder imports = new StringBuilder();
        jpaType.getImports(validatedColumns).forEach(anImport -> imports.append(getFormattedImport(anImport)));
        imports.append(TextEnum.NEW_LINE_MARK.getText());
        return imports.toString();
    }

    private String getFormattedImport(String anImport) {
        if (TextEnum.EMPTY_MARK.getText().equals(anImport)) {
            return String.format(TextEnum.IMPORT_FORMAT.getText(), anImport);
        }
        return String.format(TextEnum.IMPORT_TEXT.getText(), anImport);
    }

    private String generateClassHead(String table, JPAType jpaType) {
        StringBuilder classHeadData = new StringBuilder();
        jpaType.getClassHead(table).forEach(line -> {
            classHeadData.append(line);
            classHeadData.append(TextEnum.NEW_LINE_MARK.getText());
        });
        classHeadData.append(TextEnum.NEW_LINE_MARK.getText());
        return classHeadData.toString();
    }

    private String generateClassBody(ValidatedColumns validatedColumns, JPAType jpaType) {
        StringBuilder lines = new StringBuilder();
        jpaType.getLines(validatedColumns).forEach(line -> {
            lines.append(line);
            lines.append(TextEnum.NEW_LINE_MARK.getText());
        });
        lines.append(TextEnum.CURLY_END_BRACKET.getText());
        return lines.toString();
    }

    /**
     * Convert sql data type into java data type. This method solves problems for example when there is a sql data
     * type int(10). This data type could contain number higher than max integer, so it cannot be converted to int data
     * type but to long one.
     *
     * @param sqlDataType expected format xxx(yy) where xxx = SQL Data type and yy = number of precision
     *                    for example: varchar(255) or int(10)
     * @return java Data type (depended on precision)
     */
    private String processingAndRefinementDataType(String sqlDataType) {
        String dataType = sqlDataType.split("\\(")[0];
        if (DataTypeEnum.INT.name().equals(dataType.toUpperCase())) {
            String stringPrecision = sqlDataType.substring(sqlDataType.indexOf("(") + 1, sqlDataType.indexOf(")"));
            int precision = Integer.parseInt(stringPrecision);
            if (precision > 9) {
                return DataTypeEnum.BIGINT.name().toLowerCase();
            } else if (precision < 5) {
                return DataTypeEnum.SMALLINT.name().toLowerCase();
            }
        }
        return dataType;
    }
}