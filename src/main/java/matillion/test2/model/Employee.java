package matillion.test2.model;

public class Employee
{
    private String managementRole;
    private String birthDate;
    private String fullName;

    public Employee( String fullName, String managementRole, String birthDate )
    {
        this.fullName = fullName;
        this.managementRole = managementRole;
        this.birthDate = birthDate;
    }

    public Employee( String fullName )
    {
        this.fullName = fullName;
    }

    public String getManagementRole()
    {
        return managementRole;
    }

    public String getBirthDate()
    {
        return birthDate;
    }

    public String getFullName()
    {
        return fullName;
    }
}
