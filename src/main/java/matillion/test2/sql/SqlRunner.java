package matillion.test2.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class SqlRunner
{
    public interface StatementEditor {
        void editStatement( PreparedStatement s) throws SQLException;
    }

    private SqlConfig sqlConfig;

    public SqlRunner( SqlConfig config )
    {
        sqlConfig = config;
    }

    /**
     * Runs a prepared statement and returns the parsed output
     *
     * @param query The SQL query
     * @param statementEditor A callback used to set the parameters of the prepared statement
     * @param serializer Converts from the SQL result set to a list of type T
     * @param <T>
     * @return
     * @throws SQLException
     */
    public <T> List<T> runPreparedStatement( String query,
                                             StatementEditor statementEditor,
                                             SqlSerializer<T> serializer ) throws SQLException
    {
        try
        {
            Class.forName ("com.mysql.jdbc.Driver");
        }
        catch ( Exception e )
        {
            e.printStackTrace();
        }

        Connection conn = DriverManager.getConnection(  sqlConfig.getHostname(),
                                                        sqlConfig.getUsername(),
                                                        sqlConfig.getPassword() );


        PreparedStatement statement = conn.prepareStatement( query );
        statementEditor.editStatement( statement );

        ResultSet resultSet = statement.executeQuery();
        List<T> parsed = serializer.serialize( resultSet );

        resultSet.close();
        conn.close();

        return parsed;
    }
}
