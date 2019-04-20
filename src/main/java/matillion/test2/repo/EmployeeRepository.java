package matillion.test2.repo;

import matillion.test2.util.StringUtils;
import matillion.test2.model.Employee;
import matillion.test2.sql.EmployeeSerializer;
import matillion.test2.sql.SqlRunner;

import java.sql.SQLException;
import java.util.List;

public class EmployeeRepository
{
    private SqlRunner sqlRunner;
    private EmployeeSerializer employeeSerializer;

    // This kind of thing ideally calls for dependency injection but that seemed like
    // overkill for such an exercise
    public EmployeeRepository(SqlRunner runner, EmployeeSerializer serializer) {
        sqlRunner = runner;
        employeeSerializer = serializer;
    }

    public List<Employee> getEmployeesByDepartmentPayTypeEducationLevel( String dept,
                                                                         String payType,
                                                                         String edLevel ) throws SQLException
    {
        if ( StringUtils.isNullOrBlank( dept )
            || StringUtils.isNullOrBlank( payType )
            || StringUtils.isNullOrBlank( edLevel ) )
        {
            throw new IllegalArgumentException("Arguments must be non-null and non-empty");
        }


        return sqlRunner.runPreparedStatement(
         "SELECT full_name, birth_date, employee.management_role FROM employee\n" +
                "INNER JOIN position ON employee.position_id = position.position_id\n" +
                "INNER JOIN department ON employee.department_id = department.department_id\n" +
                "WHERE  position.pay_type = ? \n" +
                "AND    department.department_description = ? \n" +
                "AND    employee.education_level = ?",
                s -> {

                s.setString( 1, payType );
                s.setString( 2, dept );
                s.setString( 3, edLevel );
        }, employeeSerializer );
    }
}
