package generator.utils;

public enum TextEnum {

    DATE_FORMAT("yyyy-MM-dd"),
    HOUR_FORMAT("HH"),
    LOG_FILE_FORMAT("logs/%s/%s.log"),
    GENERATE_LOG_FORMAT("%s %s (for table %s)"),
    LOG_PATTERN("[%d] %-8p (%-25c): %m%n"),
    EMAIL_PATTERN("%s.%s@%s"),
    SAVE_OR_UPDATE_PATTERN("    void saveOrUpdate(%s %s);%n"),
    GET_CLASS_PATTERN("    %s get%s(int id);%n"),
    GET_ALL_CLASSES_PATTERN("    List<%s> getAll%s();%n"),
    DELETE_BY_CLASS_PATTERN("    void delete%s(%s %s);%n"),
    DELETE_BY_ID_PATTERN("    void delete%s(int id);%n"),
    WOMAN_SPECIFIC_FILE("sources/woman-specific.txt"),
    MAN_SPECIFIC_FILE("sources/man-specific.txt"),
    DIACRITIC_FILE("sources/diacritic.txt"),
    NAMES_FILE("sources/names.txt"),
    MAN_SURNAMES_FILE("sources/manSurnames.txt"),
    WOMAN_SURNAMES_FILE("sources/womanSurnames.txt"),
    DOMAINS_FILE("sources/domains.txt"),
    STREETS_FILE("sources/streets.txt"),
    HIBERNATE_CONFIG_FILE("hibernate.cfg.xml"),
    HIBERNATE_DATABASE_PROPERTY("%s%s%s.setProperty(\"hibernate.connection.url\", \"jdbc:mysql://localhost:3306/%s\")"),
    FORMAT_SUFFIX_EK("ek"),
    FORMAT_SUFFIX_KOVA("ková"),
    FORMAT_SUFFIX_EK2("ěk"),
    FORMAT_SUFFIX_A("a"),
    FORMAT_SUFFIX_OVA("ová"),
    FORMAT_SUFFIX_Y("ý"),
    FORMAT_SUFFIX_A2("á"),
    FORMAT_SUFFIX_EC("ec"),
    FORMAT_SUFFIX_COVA("cová"),
    FORMAT_SUFFIX_EL("el"),
    FORMAT_SUFFIX_LOVA("lová"),
    ALEXIS_NAME("Alexis"),
    COLON_MARK(":"),
    EMPTY_MARK(""),
    SPACE_MARK(" "),
    NEW_LINE_MARK("\n"),
    SLASH_MARK("/"),
    DASH_MARK("-"),
    UNDERSCORE_MARK("_"),
    CURLY_END_BRACKET("}"),
    SQL_DB_PARAMETER("db"),
    PRI("PRI"),
    GENERATE_LOG_TEXT("Generate "),
    DONE_GENERATING("%s completed"),
    PACKAGE_TEXT("package %s;%n%n"),
    IMPORT_FORMAT("%s%n"),
    IMPORT_TEXT("import %s%n"),
    DATE_TIME("datetime"),
    DATE("date"),
    TIME("time"),
    TAB_SPACE("    "),
    RETURN("return;"),
    RETURN_NULL("return null;"),
    RETURN_EMPTY_LIST("return new ArrayList<>();"),
    IMPORT_ALL_ARGS_CONSTRUCTOR("lombok.AllArgsConstructor;"),
    IMPORT_DATA("lombok.Data;"),
    IMPORT_NO_ARGS_CONSTRUCTOR("lombok.NoArgsConstructor;"),
    IMPORT_COLUMN("javax.persistence.Column;"),
    IMPORT_ENTITY("javax.persistence.Entity;"),
    IMPORT_GENERATED_VALUE("javax.persistence.GeneratedValue;"),
    IMPORT_GENERATION_TYPE("javax.persistence.GenerationType;"),
    IMPORT_ID("javax.persistence.Id;"),
    IMPORT_TABLE("javax.persistence.Table;"),
    IMPORT_LOCAL_DATE("java.time.LocalDate;"),
    IMPORT_LOCAL_DATE_TIME("java.time.LocalDateTime;"),
    IMPORT_ARRAY_LIST("java.util.ArrayList;"),
    IMPORT_LIST("java.util.List;"),
    IMPORT_ALL_ENTITIES("jpa.entity.*;"),
    IMPORT_SESSION("org.hibernate.Session;"),
    IMPORT_SESSION_FACTORY("org.hibernate.SessionFactory;"),
    IMPORT_CONFIGURATION("org.hibernate.cfg.Configuration;"),
    IMPORT_BUSINESS("business.dto.%s;"),
    IMPORT_ENTITY_MANAGER("jpa.%s;"),
    IMPORT_DAO("jpa.dao.%s%s;"),
    IMPORT_SPECIFIC_ENTITY("jpa.entity.%s%s;"),
    IMPORT_QUERY("org.hibernate.query.Query;"),
    IMPORT_LOGGER_SERVICE("generator.service.LoggerService;"),
    IMPORT_LOGGER_SERVICE_IMPL("generator.serviceimpl.LoggerServiceImpl;"),
    IMPORT_OBJECTS("java.util.Objects;"),
    ANNOTATION_DATA("@Data"),
    ANNOTATION_ALL_ARGS_CONSTRUCTOR("@AllArgsConstructor"),
    ANNOTATION_NO_ARGS_CONSTRUCTOR("@NoArgsConstructor"),
    ANNOTATION_ENTITY("@Entity"),
    ANNOTATION_TABLE("@Table(name = \"%s\")"),
    ANNOTATION_ID("    @Id"),
    ANNOTATION_GENERATED_VALUE("    @GeneratedValue(strategy = GenerationType.IDENTITY)"),
    ANNOTATION_COLUMN("    @Column(name = \"%s\")"),
    ANNOTATION_OVERRIDE("    @Override"),
    ENTITY_CLASS_HEAD("public class %s%s {"),
    CLASS_HEAD("public class %s {"),
    CLASS_EXTENDS_IMPLEMENTS_HEAD("public class %s%s extends %s implements %s%s {"),
    INTERFACE_HEAD("public interface %s%s {"),
    ATTRIBUTE_DECLARATION("    private %s %s;%n"),
    ATTRIBUTE_SESSION_FACTORY("    private SessionFactory factory;"),
    ATTRIBUTE_SESSION("    private Session session;"),
    UNKNOWN_DATA_TYPE("Unknown data type!"),
    FILE_NOT_CREATED("File %s not created!"),
    DIRS_NOT_CREATED("Some of directories (%s) not created"),
    JAVA_SUFFIX(".java"),
    CLASS_SUFFIX(".class"),
    PROTECTED_CONSTRUCTOR("    protected %s() {"),
    FACTORY_INIT("            factory = new Configuration()"),
    SETTING_CONFIGURE("                    .configure(\"%s\")"),
    ADD_ANNOTATED_CLASS("                    .addAnnotatedClass(%s)"),
    BUILD_SESSION_FACTORY("                    .buildSessionFactory();"),
    SESSION_METHOD_HEAD("    public Session getSession() {"),
    SESSION_IF_HEAD("        if (factory != null && (session == null || !session.isOpen())) {"),
    SESSION_IF_BODY("            session = Objects.requireNonNull(factory).getCurrentSession();"),
    SESSION_RETURN("        return session;"),
    SAVE_OR_UPDATE_DEFINITION_HEAD("    public void saveOrUpdate(%s %s) {"),
    DEFINITION_IF("        if (getSession() == null || %s == null) {"),
    DEFINITION_BEGIN_TRANSACTION("        getSession().beginTransaction();"),
    SAVE_OR_UPDATE_DEFINITION_SAVE_OR_UPDATE("        getSession().saveOrUpdate(mapDtoToEntity(%s));"),
    DEFINITION_COMMIT("        getSession().getTransaction().commit();"),
    GET_DEFINITION_HEAD("    public %s get%s(int id) {"),
    IF_DEFINITION("        if (getSession() == null) {"),
    GET_DEFINITION_GET("        %s%s entity = getSession().get(%s%s.class, id);"),
    GET_DEFINITION_MAP_TO_DTO("        return mapEntityToDto(entity);"),
    GET_ALL_DEFINITION_HEAD("    public List<%s> getAll%s() {"),
    GET_ALL_DEFINITION_QUERY("        List<%s%s> entities = getSession().createQuery(\"FROM %s%s\").getResultList();"),
    GET_ALL_DEFINITION_NEW_LIST("        List<%s> dtos = new ArrayList<>();"),
    GET_ALL_DEFINITION_FOR_EACH("        entities.forEach(entity -> dtos.add(mapEntityToDto(entity)));"),
    GET_ALL_DEFINITION_RETURN("        return dtos;"),
    DELETE_DEFINITION_HEAD("    public void delete%s(%s %s) {"),
    DELETE_DEFINITION_DELETE("        getSession().delete(mapDtoToEntity(%s));"),
    DELETE_DEFINITION_BY_ID_HEAD("    public void delete%s(int id) {"),
    DELETE_DEFINITION_BY_ID_QUERY("%sQuery<?> query = getSession().createQuery(\"DELETE FROM %s%s WHERE id=:id\");"),
    DELETE_DEFINITION_BY_ID_PARAMETER("        query.setParameter(\"id\", id);"),
    DELETE_DEFINITION_BY_ID_EXECUTE_UPDATE("        query.executeUpdate();"),
    SUPPRESS_WARNINGS("        @SuppressWarnings(\"unchecked\")"),
    ENTITY_TO_DTO_HEAD("    private %s mapEntityToDto(%s%s entity) {"),
    ENTITY_TO_DTO_IF("        if (entity == null) {"),
    ENTITY_TO_DTO_NEW_DTO("        %s dto = new %s();"),
    ENTITY_TO_DTO_SET("        dto.set%s(entity.get%s%s());"),
    ENTITY_TO_DTO_RETURN("        return dto;"),
    DTO_TO_ENTITY_HEAD("    private %s%s mapDtoToEntity(%s dto) {"),
    DTO_TO_ENTITY_IF("        if (dto == null) {"),
    DTO_TO_ENTITY_NEW_ENTITY("        %s%s entity = new %s%s();"),
    DTO_TO_ENTITY_SET("        entity.set%s%s(dto.get%s());"),
    DTO_TO_ENTITY_RETURN("        return entity;"),
    SQL_SELECT_FROM_IS("" +
            "SELECT TABLE_NAME, COLUMN_NAME, COLUMN_TYPE, COLUMN_KEY " +
            "FROM INFORMATION_SCHEMA.COLUMNS " +
            "WHERE TABLE_SCHEMA=:db"),
    TRY_BLOCK("        try {"),
    CATCH_BLOCK("        } catch (Exception exp) {"),
    LOGGER_SERVICE_INIT("            LoggerService logger = LoggerServiceImpl.getInstance(%s.class);"),
    LOGGER_SERVICE_LOG("            logger.getLog().error(\"%s\");"),
    CANNOT_LOG_DB_TEXT("Cannot log to database"),
    CLOSE_CATCH_BLOCK("        }");

    private final String text;

    TextEnum(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
