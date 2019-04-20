package matillion.test2.sql;

import matillion.test2.model.Employee;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EmployeeSerializer implements SqlSerializer<Employee>
{
    @Override
    public List<Employee> serialize( ResultSet resultSet ) throws SQLException
    {
        if (resultSet == null)
        {
            return Collections.emptyList();
        }

        List<Employee> employees = new ArrayList<>();

        while ( resultSet.next() )
        {
            Employee employee = new Employee( resultSet.getString( "full_name" ),
                    resultSet.getString( "birth_date" ),
                    resultSet.getString( "management_role" ) );
            employees.add( employee );
        }

        return employees;
    }
}
