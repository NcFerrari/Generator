package utils.jpa;

import lombok.Getter;

@Getter
public enum DataTypeEnum {

    VARCHAR("String"),
    CHAR("String"),
    BIGINT("Long"),
    MEDIUMINT("Integer"),
    INT("Integer"),
    TINYINT("Short"),
    SMALLINT("Short"),
    DATETIME("LocalDateTime"),
    TIME("LocalDateTime"),
    DATE("LocalDate");

    private final String javaDataType;

    DataTypeEnum(String javaDataType) {
        this.javaDataType = javaDataType;
    }

    public static boolean contains(String inputValue) {
        for (DataTypeEnum value : DataTypeEnum.values()) {
            if (value.name().equals(inputValue)) {
                return true;
            }
        }
        return false;
    }

}