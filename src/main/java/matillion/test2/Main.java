package matillion.test2;

import matillion.test2.cmdinterface.CommandLineInterface;
import matillion.test2.repo.EmployeeRepository;
import matillion.test2.sql.Config;
import matillion.test2.sql.EmployeeSerializer;
import matillion.test2.sql.SqlRunner;

import java.util.Scanner;

public class Main
{
    public static void main( String[] args )
    {
        EmployeeRepository repository = new EmployeeRepository( new SqlRunner( Config.instance ),
                                                                new EmployeeSerializer() );

        new CommandLineInterface( new Scanner( System.in ), System.out ).run( repository );
    }
}
