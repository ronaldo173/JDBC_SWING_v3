package DB;

import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Created by Santer on 04.11.2015.
 */
public class DBConnect {
    private Connection connection = null;

    public  DBConnect() throws IOException, SQLException {
        Properties properties = new Properties();
        properties.load(new FileInputStream("dataBase.properties"));

        String url, name, password;
        url=properties.getProperty("url");
        name = properties.getProperty("user");
        password = properties.getProperty("password");

        System.out.println(url + "\n" + name + "\n" + password);

        connection = DriverManager.getConnection(url, name, password);
        System.out.println("Connected to DB succesfully!!");
    }

    public List<Employee> getAllEmployees() {
        List<Employee> employeeList = new ArrayList<>();
        java.sql.Statement statement = null;
        ResultSet resultSet = null;

        try {
            statement= connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM employees");

            while (resultSet.next()){
                Employee employee = convertRowToEmployee(resultSet);
                employeeList.add(employee);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employeeList;
    }

    public List<Employee> searchEMployee(String lastName) throws SQLException {
        List<Employee> employeeList = new ArrayList<>();

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            lastName +="%";
            preparedStatement = connection.prepareStatement("SELECT * from employees WHERE last_name like ?");
            preparedStatement.setString(1, lastName);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                Employee employee = convertRowToEmployee(resultSet);
                employeeList.add(employee);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            close(preparedStatement, resultSet);
        }
        return employeeList;

    }

    private void close(PreparedStatement preparedStatement, ResultSet resultSet) throws SQLException {
        close(null, preparedStatement, resultSet);
    }

    private void close(Connection connection, PreparedStatement preparedStatement, ResultSet resultSet) throws SQLException {
        if( connection !=null ){
            connection.close();
        }
        if(preparedStatement !=null){
            preparedStatement.close();
        }
        if(resultSet !=null){
            resultSet.close();
        }
    }

    private Employee convertRowToEmployee(ResultSet resultSet) throws SQLException {
        int id =resultSet.getInt("id");
        String lastName = resultSet.getString("last_name");
        String firstName= resultSet.getString("first_name");
        String email= resultSet.getString("email");
        BigDecimal salary = resultSet.getBigDecimal("salary");
        Employee employee =  new Employee(id, lastName, firstName, email, salary);

        return employee;
    }

    public void addEmployee(Employee employee) {
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = connection.prepareStatement("INSERT  into employees " +
                    " (first_name, last_name, email, salary)" + "  values (?, ? , ?, ?)");

            preparedStatement.setString(1, employee.getFirstName());
            preparedStatement.setString(2, employee.getLastName());
            preparedStatement.setString(3, employee.getEmail());
            preparedStatement.setBigDecimal(4, employee.getSalary());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateEMployee(Employee employeeNew) {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement("UPDATE employees SET first_name = ?, last_name = ?," +
                    "email = ?, salary = ? WHERE id = ?");

            preparedStatement.setString(1, employeeNew.getFirstName());
            preparedStatement.setString(2, employeeNew.getLastName());
            preparedStatement.setBigDecimal(3, employeeNew.getSalary());
            preparedStatement.setString(4, employeeNew.getEmail());
            preparedStatement.setInt(5, employeeNew.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        String requestLastName = "Do";
        try {
            DBConnect dbConnect = new DBConnect();
            System.out.println("Find results of query: " + requestLastName + "\n" +dbConnect.searchEMployee(requestLastName));
            System.out.println("\n ALL EMPLOYEES \n" + dbConnect.getAllEmployees());
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }
}
