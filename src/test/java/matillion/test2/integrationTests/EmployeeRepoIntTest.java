package matillion.test2.integrationTests;

import matillion.test2.model.Employee;
import matillion.test2.repo.EmployeeRepository;
import matillion.test2.sql.Config;
import matillion.test2.sql.EmployeeSerializer;
import matillion.test2.sql.SqlRunner;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;

/**
 * Intentionally very few tests, functionality should be tested mainly with unit tests
 * These are just to check everything is wired up correctly.
 *
 * In a real world environment I'd probably have a few tests that populate the db with data, run the test,
 * then cleanup the db since I don't control the db I'd rather not manipulate it.
 */
public class EmployeeRepoIntTest
{
    @Test
    public void test_SampleQuery_isCorrect() throws SQLException
    {
        EmployeeRepository employeeRepo = new EmployeeRepository( new SqlRunner( Config.instance ), new EmployeeSerializer() );

        List<Employee> resultSet = employeeRepo.getEmployeesByDepartmentPayTypeEducationLevel("Permanent Checkers",
                                                                                        "Monthly",
                                                                                         "Partial College");
        // Very loose checks since data could easily change in the db
        Assertions.assertNotNull( resultSet );
        Assertions.assertTrue( resultSet.size() > 0 );
    }
}
