package generator.utils.jpa;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Column {

    private String columnName;
    private String dataType;

    @Override
    public String toString() {
        return columnName;
    }
}
