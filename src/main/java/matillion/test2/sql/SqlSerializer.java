package matillion.test2.sql;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface SqlSerializer<T>
{
    List<T> serialize( ResultSet resultSet ) throws SQLException;
}
