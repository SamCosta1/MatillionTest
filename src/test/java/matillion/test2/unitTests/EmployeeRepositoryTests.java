package matillion.test2.unitTests;

import matillion.test2.model.Employee;
import matillion.test2.repo.EmployeeRepository;
import matillion.test2.sql.EmployeeSerializer;
import matillion.test2.sql.SqlRunner;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Matchers;
import org.mockito.Mockito;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class EmployeeRepositoryTests
{

    private EmployeeRepository employeeRepo;
    private SqlRunner sqlRunner;

    @BeforeEach
    public  void setup() {
        EmployeeSerializer serializer = Mockito.mock( EmployeeSerializer.class );
        sqlRunner = Mockito.mock(SqlRunner.class);
        employeeRepo = new EmployeeRepository( sqlRunner, serializer );
    }

    @ParameterizedTest
    @CsvSource( { " , arg2, arg3",
                  " arg1, , arg3",
                  "arg1 , arg2, ",
                  "arg1 , , ",
                } )
    public void test_nullArguments_throwsException( String dept, String payType, String edLevel )
    {
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> employeeRepo.getEmployeesByDepartmentPayTypeEducationLevel( dept, payType, edLevel )
        );
    }

    @Test
    public void test_blankArguments_throwsException()
    {
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> employeeRepo.getEmployeesByDepartmentPayTypeEducationLevel( "\n", " ", "" )
        );
    }

    @Test
    public void test_returnsListOfEmployees_isCorrect() throws SQLException
    {
        List<Employee> employees = Arrays.asList(   new Employee("Tony Stark"),
                                                    new Employee("Stan Shunpike"));

        Mockito.when( sqlRunner.runPreparedStatement(   Matchers.anyString(),
                                                        Mockito.any(),
                                                        Matchers.any(EmployeeSerializer.class) ))
                .thenReturn( employees );

        List<Employee> result = employeeRepo.getEmployeesByDepartmentPayTypeEducationLevel( "dept",
                                                                                        "pay",
                                                                                         "ed" );
        Assertions.assertEquals( employees, result );
    }
}
