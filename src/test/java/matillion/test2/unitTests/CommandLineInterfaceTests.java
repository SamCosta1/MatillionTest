package matillion.test2.unitTests;

import matillion.test2.cmdinterface.CommandLineInterface;
import matillion.test2.model.Employee;
import matillion.test2.repo.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.PrintStream;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Scanner;

public class CommandLineInterfaceTests
{
    private EmployeeRepository employeeRepo;
    private Scanner scanner;
    private PrintStream outputStream;

    @BeforeEach
    public  void setup() {
        scanner = Mockito.mock( Scanner.class );
        outputStream = Mockito.mock( PrintStream.class );
        employeeRepo = Mockito.mock( EmployeeRepository.class );
    }

    @Test
    public void test_emptyResponse_isCorrect() throws SQLException
    {
        String department = "department";
        String payType = "payType";
        String edLevel = "edLevel";

        CommandLineInterface cmd = new CommandLineInterface( scanner, outputStream );

        Mockito.when( scanner.nextLine() ).thenReturn( department, payType, edLevel );
        Mockito.when( employeeRepo.getEmployeesByDepartmentPayTypeEducationLevel( department, payType, edLevel ) )
                .thenReturn(Collections.emptyList() );

        cmd.run( employeeRepo );

        Mockito.verify( outputStream, Mockito.times( 1 ) ).print( "Enter the department: " );
        Mockito.verify( outputStream, Mockito.times( 1 ) ).print( "Enter the pay type: " );
        Mockito.verify( outputStream, Mockito.times( 1 ) ).print( "Enter the education level: " );
        Mockito.verify( outputStream, Mockito.times( 1 ) ).println( "No results!" );
    }

    @Test
    public void test_blankData_promptsTheUserAgain() throws SQLException
    {
        String department = "department";
        String payType = "payType";
        String edLevel = "edLevel";

        CommandLineInterface cmd = new CommandLineInterface( scanner, outputStream );

        Mockito.when( scanner.nextLine() ).thenReturn( "", department, "", payType, "", edLevel );
        Mockito.when( employeeRepo.getEmployeesByDepartmentPayTypeEducationLevel( department, payType, edLevel ) )
                .thenReturn(Collections.emptyList() );

        cmd.run( employeeRepo );

        Mockito.verify( outputStream, Mockito.times( 2 )  ).print( "Enter the department: " );
        Mockito.verify( outputStream, Mockito.times( 2 ) ).print( "Enter the pay type: " );
        Mockito.verify( outputStream, Mockito.times( 2 ) ).print( "Enter the education level: " );
    }

    @Test
    public void test_populatedResponse_isCorrect() throws SQLException
    {
        Employee employee = new Employee( "Tom Bombadil", "Nobodt knows", "00-00-00" );
        String department = "department";
        String payType = "payType";
        String edLevel = "edLevel";

        CommandLineInterface cmd = new CommandLineInterface( scanner, outputStream );

        Mockito.when( scanner.nextLine() ).thenReturn( "", department, "", payType, "", edLevel );
        Mockito.when( employeeRepo.getEmployeesByDepartmentPayTypeEducationLevel( department, payType, edLevel ) )
                .thenReturn( Collections.singletonList( employee ) );

        cmd.run( employeeRepo );

        Mockito.verify( outputStream, Mockito.times( 2 )  ).print( "Enter the department: " );
        Mockito.verify( outputStream, Mockito.times( 2 ) ).print( "Enter the pay type: " );
        Mockito.verify( outputStream, Mockito.times( 2 ) ).print( "Enter the education level: " );
        Mockito.verify( outputStream, Mockito.times( 1 ) ).println( "Full Name: " + employee.getFullName() );
        Mockito.verify( outputStream, Mockito.times( 1 ) ).println( "Birth Date: " + employee.getBirthDate() );
        Mockito.verify( outputStream, Mockito.times( 1 ) ).println( "Management Role: " + employee.getManagementRole() );
    }
}
