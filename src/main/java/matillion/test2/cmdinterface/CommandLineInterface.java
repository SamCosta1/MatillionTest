package matillion.test2.cmdinterface;

import matillion.test2.model.Employee;
import matillion.test2.repo.EmployeeRepository;
import matillion.test2.util.StringUtils;

import java.io.InputStream;
import java.io.PrintStream;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class CommandLineInterface
{
    private final PrintStream out;
    private Scanner scanner;

    public CommandLineInterface( Scanner scanner, PrintStream out )
    {
        this.scanner = scanner;
        this.out = out;
    }

    public void run( EmployeeRepository repository )
    {
        String dept = promptUntilValueEntered( "Enter the department: ", scanner );
        String payType = promptUntilValueEntered( "Enter the pay type: ", scanner );
        String edLevel = promptUntilValueEntered( "Enter the education level: ", scanner );

        List<Employee> employeeList = null;

        try
        {
            employeeList = repository.getEmployeesByDepartmentPayTypeEducationLevel( dept, payType, edLevel );
        }
        catch ( SQLException|IllegalArgumentException e )
        {
            out.println( "Something's gone wrong! Please check your inputs and your internet connection" );
        }

        output( employeeList, out );
    }

    // Keep asking the user for the same data until they enter something
    private String promptUntilValueEntered( String message, Scanner scanner )
    {
        out.print( message );
        String response = scanner.nextLine();

        return StringUtils.isNullOrBlank( response ) ? promptUntilValueEntered( message, scanner ) : response;
    }

    private void output( List<Employee> employeeList, PrintStream out )
    {
        if ( employeeList == null )
        {
            return;
        }

        if ( employeeList.isEmpty() )
        {
            out.println( "No results!" );
        }

        employeeList.forEach( employee -> {
            out.println( "Full Name: " + employee.getFullName() );
            out.println( "Birth Date: " + employee.getBirthDate() );
            out.println( "Management Role: " + employee.getManagementRole() );
            out.println();
            out.println( "-" );
            out.println();
        } );
    }
}
