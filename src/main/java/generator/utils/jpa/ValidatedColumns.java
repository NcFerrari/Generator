package generator.utils.jpa;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ValidatedColumns {

    private String tableName;
    private List<Column> columns = new ArrayList<>();
    private boolean primaryKeyIncluded = false;
    private boolean dateTimeIncluded = false;
    private boolean dateIncluded = false;
    private String databaseName;

    public ValidatedColumns(String tableName) {
        this.tableName = tableName;
    }

    public void add(Column column) {
        getColumns().add(column);
    }

    public void addAsFirst(Column column) {
        setPrimaryKeyIncluded(true);
        getColumns().add(0, column);
    }

    public void hasDateTimeIncluded() {
        dateTimeIncluded = true;
    }

    public void hasDateIncluded() {
        dateIncluded = true;
    }
}
