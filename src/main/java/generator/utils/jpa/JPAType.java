package generator.utils.jpa;

import generator.utils.TextEnum;
import generator.utils.TextFormat;
import lombok.Getter;
import org.apache.logging.log4j.Logger;
import generator.service.FileService;
import generator.service.LoggerService;
import generator.serviceimpl.FileServiceImpl;
import generator.serviceimpl.LoggerServiceImpl;

import java.util.ArrayList;
import java.util.List;

@Getter
public enum JPAType {
    ENTITY("Entity", "src/main/java/jpa/entity/", "jpa.entity"),
    DTO("Dto", "src/main/java/business/dto/", "business.dto"),
    DAO("Dao", "src/main/java/jpa/dao/", "jpa.dao"),
    DAO_IMPL("DaoImpl", "src/main/java/jpa/daoimpl/", "jpa.daoimpl"),
    ENTITY_MANAGER("EntityManager", "src/main/java/jpa/", "jpa");

    private final LoggerService loggerService = LoggerServiceImpl.getInstance(JPAType.class);
    private final Logger log = loggerService.getLog();
    private final FileService fileService = FileServiceImpl.getInstance();
    private final String title;
    private final String packageName;
    private String path;

    JPAType(String suffix, String path, String packageName) {
        this.title = suffix;
        this.path = path;
        this.packageName = packageName;
        if (!path.endsWith(TextEnum.SLASH_MARK.getText())) {
            this.path += TextEnum.SLASH_MARK.getText();
        }
    }

    public List<String> getImports(ValidatedColumns validatedColumns) {
        List<String> imports = new ArrayList<>();
        switch (this) {
            case ENTITY:
                imports.add(TextEnum.IMPORT_DATA.getText());
                imports.add(TextEnum.IMPORT_NO_ARGS_CONSTRUCTOR.getText());
                imports.add(TextEnum.EMPTY_MARK.getText());
                imports.add(TextEnum.IMPORT_COLUMN.getText());
                imports.add(TextEnum.IMPORT_ENTITY.getText());
                imports.add(TextEnum.IMPORT_GENERATED_VALUE.getText());
                imports.add(TextEnum.IMPORT_GENERATION_TYPE.getText());
                imports.add(TextEnum.IMPORT_ID.getText());
                imports.add(TextEnum.IMPORT_TABLE.getText());
                if (validatedColumns.isDateIncluded()) {
                    imports.add(TextEnum.IMPORT_LOCAL_DATE.getText());
                }
                if (validatedColumns.isDateTimeIncluded()) {
                    imports.add(TextEnum.IMPORT_LOCAL_DATE_TIME.getText());
                }
                break;
            case DTO:
                imports.add(TextEnum.IMPORT_ALL_ARGS_CONSTRUCTOR.getText());
                imports.add(TextEnum.IMPORT_DATA.getText());
                imports.add(TextEnum.IMPORT_NO_ARGS_CONSTRUCTOR.getText());
                if (validatedColumns.isDateIncluded()) {
                    imports.add(TextEnum.IMPORT_LOCAL_DATE.getText());
                }
                if (validatedColumns.isDateTimeIncluded()) {
                    imports.add(TextEnum.IMPORT_LOCAL_DATE_TIME.getText());
                }
                break;
            case DAO:
                imports.add(String.format(TextEnum.IMPORT_BUSINESS.getText(),
                        TextFormat.capitalizeOrCamelCaseText(validatedColumns.getTableName(), false)));
                imports.add(TextEnum.EMPTY_MARK.getText());
                imports.add(TextEnum.IMPORT_LIST.getText());
                break;
            case DAO_IMPL:
                String className = TextFormat.capitalizeOrCamelCaseText(validatedColumns.getTableName(), false);
                imports.add(String.format(TextEnum.IMPORT_BUSINESS.getText(), className));
                imports.add(String.format(TextEnum.IMPORT_ENTITY_MANAGER.getText(), ENTITY_MANAGER.getTitle()));
                imports.add(String.format(TextEnum.IMPORT_DAO.getText(), className, DAO.getTitle()));
                imports.add(String.format(TextEnum.IMPORT_SPECIFIC_ENTITY.getText(), className, ENTITY.getTitle()));
                imports.add(TextEnum.IMPORT_QUERY.getText());
                imports.add(TextEnum.EMPTY_MARK.getText());
                imports.add(TextEnum.IMPORT_ARRAY_LIST.getText());
                imports.add(TextEnum.IMPORT_LIST.getText());
                break;
            case ENTITY_MANAGER:
                imports.add(TextEnum.IMPORT_LOGGER_SERVICE.getText());
                imports.add(TextEnum.IMPORT_LOGGER_SERVICE_IMPL.getText());
                imports.add(TextEnum.IMPORT_ALL_ENTITIES.getText());
                imports.add(TextEnum.IMPORT_SESSION.getText());
                imports.add(TextEnum.IMPORT_SESSION_FACTORY.getText());
                imports.add(TextEnum.IMPORT_CONFIGURATION.getText() + TextEnum.NEW_LINE_MARK.getText());
                imports.add(TextEnum.IMPORT_OBJECTS.getText());
                break;
            default:
        }
        return imports;
    }

    public List<String> getClassHead(String table) {
        List<String> classHeadData = new ArrayList<>();
        switch (this) {
            case ENTITY:
                classHeadData.add(TextEnum.ANNOTATION_DATA.getText());
                classHeadData.add(TextEnum.ANNOTATION_NO_ARGS_CONSTRUCTOR.getText());
                classHeadData.add(TextEnum.ANNOTATION_ENTITY.getText());
                classHeadData.add(String.format(TextEnum.ANNOTATION_TABLE.getText(), table));
                classHeadData.add(String.format(TextEnum.ENTITY_CLASS_HEAD.getText(),
                        TextFormat.capitalizeOrCamelCaseText(table, false),
                        getTitle()));
                break;
            case DTO:
                classHeadData.add(TextEnum.ANNOTATION_DATA.getText());
                classHeadData.add(TextEnum.ANNOTATION_ALL_ARGS_CONSTRUCTOR.getText());
                classHeadData.add(TextEnum.ANNOTATION_NO_ARGS_CONSTRUCTOR.getText());
                classHeadData.add(String.format(TextEnum.CLASS_HEAD.getText(),
                        TextFormat.capitalizeOrCamelCaseText(table, false)));
                break;
            case DAO:
                String className = TextFormat.capitalizeOrCamelCaseText(table, false);
                classHeadData.add(String.format(TextEnum.INTERFACE_HEAD.getText(), className, getTitle()));
                break;
            case DAO_IMPL:
                className = TextFormat.capitalizeOrCamelCaseText(table, false);
                classHeadData.add(String.format(TextEnum.CLASS_EXTENDS_IMPLEMENTS_HEAD.getText(),
                        className, DAO_IMPL.getTitle(), ENTITY_MANAGER.getTitle(), className, DAO.getTitle()));
                break;
            case ENTITY_MANAGER:
                classHeadData.add(String.format(TextEnum.CLASS_HEAD.getText(), getTitle()));
                break;
            default:
        }
        return classHeadData;
    }

    public List<String> getLines(ValidatedColumns validatedColumns) {
        List<String> lines = new ArrayList<>();
        switch (this) {
            case ENTITY:
                lines.add(TextEnum.ANNOTATION_ID.getText());
                lines.add(TextEnum.ANNOTATION_GENERATED_VALUE.getText());
                validatedColumns.getColumns().forEach(column -> {
                    lines.add(String.format(TextEnum.ANNOTATION_COLUMN.getText(), column.getColumnName()));
                    lines.add(String.format(TextEnum.ATTRIBUTE_DECLARATION.getText(),
                            getDataType(column.getDataType().toUpperCase()),
                            TextFormat.capitalizeOrCamelCaseText(column.getColumnName(), true) + getTitle()));
                });
                break;
            case DTO:
                validatedColumns.getColumns().forEach(column -> lines.add(
                        String.format(TextEnum.ATTRIBUTE_DECLARATION.getText(),
                                getDataType(column.getDataType().toUpperCase()),
                                TextFormat.capitalizeOrCamelCaseText(column.getColumnName(), true))
                ));
                break;
            case DAO:
                String className = TextFormat.capitalizeOrCamelCaseText(validatedColumns.getTableName(), false);
                String attributeName = TextFormat.capitalizeOrCamelCaseText(validatedColumns.getTableName(), true);
                lines.add(String.format(TextEnum.SAVE_OR_UPDATE_PATTERN.getText(), className, attributeName));
                lines.add(String.format(TextEnum.GET_CLASS_PATTERN.getText(), className, className));
                lines.add(String.format(TextEnum.GET_ALL_CLASSES_PATTERN.getText(), className, className));
                lines.add(String.format(TextEnum.DELETE_BY_CLASS_PATTERN.getText(),
                        className,
                        className,
                        attributeName));
                lines.add(String.format(TextEnum.DELETE_BY_ID_PATTERN.getText(), className));
                break;
            case DAO_IMPL:
                className = TextFormat.capitalizeOrCamelCaseText(validatedColumns.getTableName(), false);
                attributeName = TextFormat.capitalizeOrCamelCaseText(validatedColumns.getTableName(), true);

                lines.add(TextEnum.ANNOTATION_OVERRIDE.getText());
                lines.add(String.format(TextEnum.SAVE_OR_UPDATE_DEFINITION_HEAD.getText(), className, attributeName));
                lines.add(String.format(TextEnum.DEFINITION_IF.getText(), attributeName));
                lines.add(TextEnum.TAB_SPACE.getText() + TextEnum.TAB_SPACE.getText() +
                        TextEnum.TAB_SPACE.getText() + TextEnum.RETURN.getText());
                lines.add(TextEnum.TAB_SPACE.getText() + TextEnum.TAB_SPACE.getText() +
                        TextEnum.CURLY_END_BRACKET.getText());
                lines.add(TextEnum.DEFINITION_BEGIN_TRANSACTION.getText());
                lines.add(String.format(TextEnum.SAVE_OR_UPDATE_DEFINITION_SAVE_OR_UPDATE.getText(), attributeName));
                lines.add(TextEnum.DEFINITION_COMMIT.getText());
                lines.add(TextEnum.TAB_SPACE.getText() + TextEnum.CURLY_END_BRACKET.getText());
                lines.add(TextEnum.EMPTY_MARK.getText());

                lines.add(TextEnum.ANNOTATION_OVERRIDE.getText());
                lines.add(String.format(TextEnum.GET_DEFINITION_HEAD.getText(), className, className));
                lines.add(TextEnum.IF_DEFINITION.getText());
                lines.add(TextEnum.TAB_SPACE.getText() + TextEnum.TAB_SPACE.getText() +
                        TextEnum.TAB_SPACE.getText() + TextEnum.RETURN_NULL.getText());
                lines.add(TextEnum.TAB_SPACE.getText() + TextEnum.TAB_SPACE.getText() +
                        TextEnum.CURLY_END_BRACKET.getText());
                lines.add(TextEnum.DEFINITION_BEGIN_TRANSACTION.getText());
                lines.add(String.format(TextEnum.GET_DEFINITION_GET.getText(),
                        className, ENTITY.getTitle(), className, ENTITY.getTitle()));
                lines.add(TextEnum.DEFINITION_COMMIT.getText());
                lines.add(TextEnum.GET_DEFINITION_MAP_TO_DTO.getText());
                lines.add(TextEnum.TAB_SPACE.getText() + TextEnum.CURLY_END_BRACKET.getText());
                lines.add(TextEnum.EMPTY_MARK.getText());

                lines.add(TextEnum.ANNOTATION_OVERRIDE.getText());
                lines.add(String.format(TextEnum.GET_ALL_DEFINITION_HEAD.getText(), className, className));
                lines.add(TextEnum.IF_DEFINITION.getText());
                lines.add(TextEnum.TAB_SPACE.getText() + TextEnum.TAB_SPACE.getText() +
                        TextEnum.TAB_SPACE.getText() + TextEnum.RETURN_EMPTY_LIST.getText());
                lines.add(TextEnum.TAB_SPACE.getText() + TextEnum.TAB_SPACE.getText() +
                        TextEnum.CURLY_END_BRACKET.getText());
                lines.add(TextEnum.DEFINITION_BEGIN_TRANSACTION.getText());
                lines.add(TextEnum.SUPPRESS_WARNINGS.getText());
                lines.add(String.format(TextEnum.GET_ALL_DEFINITION_QUERY.getText(),
                        className, ENTITY.getTitle(), className, ENTITY.getTitle()));
                lines.add(TextEnum.DEFINITION_COMMIT.getText());
                lines.add(String.format(TextEnum.GET_ALL_DEFINITION_NEW_LIST.getText(), className));
                lines.add(TextEnum.GET_ALL_DEFINITION_FOR_EACH.getText());
                lines.add(TextEnum.GET_ALL_DEFINITION_RETURN.getText());
                lines.add(TextEnum.TAB_SPACE.getText() + TextEnum.CURLY_END_BRACKET.getText());
                lines.add(TextEnum.EMPTY_MARK.getText());

                lines.add(TextEnum.ANNOTATION_OVERRIDE.getText());
                lines.add(String.format(TextEnum.DELETE_DEFINITION_HEAD.getText(),
                        className, className, attributeName));
                lines.add(String.format(TextEnum.DEFINITION_IF.getText(), attributeName));
                lines.add(TextEnum.TAB_SPACE.getText() + TextEnum.TAB_SPACE.getText() +
                        TextEnum.TAB_SPACE.getText() + TextEnum.RETURN.getText());
                lines.add(TextEnum.TAB_SPACE.getText() + TextEnum.TAB_SPACE.getText() +
                        TextEnum.CURLY_END_BRACKET.getText());
                lines.add(TextEnum.DEFINITION_BEGIN_TRANSACTION.getText());
                lines.add(String.format(TextEnum.DELETE_DEFINITION_DELETE.getText(), attributeName));
                lines.add(TextEnum.DEFINITION_COMMIT.getText());
                lines.add(TextEnum.TAB_SPACE.getText() + TextEnum.CURLY_END_BRACKET.getText());
                lines.add(TextEnum.EMPTY_MARK.getText());

                lines.add(TextEnum.ANNOTATION_OVERRIDE.getText());
                lines.add(String.format(TextEnum.DELETE_DEFINITION_BY_ID_HEAD.getText(), className));
                lines.add(TextEnum.IF_DEFINITION.getText());
                lines.add(TextEnum.TAB_SPACE.getText() + TextEnum.TAB_SPACE.getText() +
                        TextEnum.TAB_SPACE.getText() + TextEnum.RETURN.getText());
                lines.add(TextEnum.TAB_SPACE.getText() + TextEnum.TAB_SPACE.getText() +
                        TextEnum.CURLY_END_BRACKET.getText());
                lines.add(TextEnum.DEFINITION_BEGIN_TRANSACTION.getText());
                lines.add(String.format(TextEnum.DELETE_DEFINITION_BY_ID_QUERY.getText(),
                        TextEnum.TAB_SPACE.getText() + TextEnum.TAB_SPACE.getText(),
                        className, ENTITY.getTitle()));
                lines.add(TextEnum.DELETE_DEFINITION_BY_ID_PARAMETER.getText());
                lines.add(TextEnum.DELETE_DEFINITION_BY_ID_EXECUTE_UPDATE.getText());
                lines.add(TextEnum.DEFINITION_COMMIT.getText());
                lines.add(TextEnum.TAB_SPACE.getText() + TextEnum.CURLY_END_BRACKET.getText());
                lines.add(TextEnum.EMPTY_MARK.getText());

                lines.add(String.format(TextEnum.ENTITY_TO_DTO_HEAD.getText(),
                        className, className, ENTITY.getTitle()));
                lines.add(TextEnum.ENTITY_TO_DTO_IF.getText());
                lines.add(TextEnum.TAB_SPACE.getText() + TextEnum.TAB_SPACE.getText() +
                        TextEnum.TAB_SPACE.getText() + TextEnum.RETURN_NULL.getText());
                lines.add(TextEnum.TAB_SPACE.getText() + TextEnum.TAB_SPACE.getText() +
                        TextEnum.CURLY_END_BRACKET.getText());
                lines.add(String.format(TextEnum.ENTITY_TO_DTO_NEW_DTO.getText(), className, className));
                validatedColumns.getColumns().forEach(columnObject -> {
                    String column = TextFormat.capitalizeOrCamelCaseText(columnObject.getColumnName(), false);
                    lines.add(String.format(TextEnum.ENTITY_TO_DTO_SET.getText(), column, column, ENTITY.getTitle()));
                });
                lines.add(TextEnum.ENTITY_TO_DTO_RETURN.getText());
                lines.add(TextEnum.TAB_SPACE.getText() + TextEnum.CURLY_END_BRACKET.getText());
                lines.add(TextEnum.EMPTY_MARK.getText());

                lines.add(String.format(TextEnum.DTO_TO_ENTITY_HEAD.getText(),
                        className, ENTITY.getTitle(), className));
                lines.add(TextEnum.DTO_TO_ENTITY_IF.getText());
                lines.add(TextEnum.TAB_SPACE.getText() + TextEnum.TAB_SPACE.getText() +
                        TextEnum.TAB_SPACE.getText() + TextEnum.RETURN_NULL.getText());
                lines.add(TextEnum.TAB_SPACE.getText() + TextEnum.TAB_SPACE.getText() +
                        TextEnum.CURLY_END_BRACKET.getText());
                lines.add(String.format(TextEnum.DTO_TO_ENTITY_NEW_ENTITY.getText(), className, ENTITY.getTitle(),
                        className, ENTITY.getTitle()));
                validatedColumns.getColumns().forEach(columnObject -> {
                    String column = TextFormat.capitalizeOrCamelCaseText(columnObject.getColumnName(), false);
                    lines.add(String.format(TextEnum.DTO_TO_ENTITY_SET.getText(), column, ENTITY.getTitle(), column));
                });
                lines.add(TextEnum.DTO_TO_ENTITY_RETURN.getText());
                lines.add(TextEnum.TAB_SPACE.getText() + TextEnum.CURLY_END_BRACKET.getText());
                break;
            case ENTITY_MANAGER:
                lines.add(TextEnum.ATTRIBUTE_SESSION_FACTORY.getText());
                lines.add(TextEnum.ATTRIBUTE_SESSION.getText());
                lines.add(TextEnum.EMPTY_MARK.getText());
                lines.add(String.format(TextEnum.PROTECTED_CONSTRUCTOR.getText(), getTitle()));
                lines.add(TextEnum.TRY_BLOCK.getText());
                lines.add(TextEnum.FACTORY_INIT.getText());
                lines.add(String.format(TextEnum.SETTING_CONFIGURE.getText(),
                        TextEnum.HIBERNATE_CONFIG_FILE.getText()));
                lines.add(String.format(TextEnum.HIBERNATE_DATABASE_PROPERTY.getText(),
                        TextEnum.TAB_SPACE.getText() + TextEnum.TAB_SPACE.getText(),
                        TextEnum.TAB_SPACE.getText() + TextEnum.TAB_SPACE.getText(),
                        TextEnum.TAB_SPACE.getText(), validatedColumns.getDatabaseName()));
                addAnnotatedClasses(lines);
                lines.add(TextEnum.BUILD_SESSION_FACTORY.getText());
                lines.add(TextEnum.CATCH_BLOCK.getText());
                lines.add(String.format(TextEnum.LOGGER_SERVICE_INIT.getText(), ENTITY_MANAGER.getTitle()));
                lines.add(String.format(TextEnum.LOGGER_SERVICE_LOG.getText(), TextEnum.CANNOT_LOG_DB_TEXT.getText()));
                lines.add(TextEnum.CLOSE_CATCH_BLOCK.getText());
                lines.add(TextEnum.TAB_SPACE.getText() + TextEnum.CURLY_END_BRACKET.getText());
                lines.add(TextEnum.EMPTY_MARK.getText());
                lines.add(TextEnum.SESSION_METHOD_HEAD.getText());
                lines.add(TextEnum.SESSION_IF_HEAD.getText());
                lines.add(TextEnum.SESSION_IF_BODY.getText());
                lines.add(TextEnum.TAB_SPACE.getText() +
                        TextEnum.TAB_SPACE.getText() +
                        TextEnum.CURLY_END_BRACKET.getText());
                lines.add(TextEnum.SESSION_RETURN.getText());
                lines.add(TextEnum.TAB_SPACE.getText() + TextEnum.CURLY_END_BRACKET.getText());
                break;
            default:
        }
        return lines;
    }

    private String getDataType(String sqlDataType) {
        if (DataTypeEnum.contains(sqlDataType)) {
            return DataTypeEnum.valueOf(sqlDataType).getJavaDataType();
        }
        return TextEnum.UNKNOWN_DATA_TYPE.getText();
    }

    private void addAnnotatedClasses(List<String> lines) {
        List<String> entities = fileService.getFileNames(ENTITY.getPath());
        entities.forEach(entity -> lines.add(String.format(TextEnum.ADD_ANNOTATED_CLASS.getText(),
                entity.replace(TextEnum.JAVA_SUFFIX.getText(), TextEnum.CLASS_SUFFIX.getText()))));
    }
}