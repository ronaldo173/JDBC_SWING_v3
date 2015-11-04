import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Santer on 03.11.2015.
 */
public class connect {

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
//        Class.forName("com.mysql.jdbc.Driver");
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/demo", "root", "root");
        ResultSet resultSet = (conn.createStatement()).executeQuery("SELECT id from employees");
        while (resultSet.next()){
            System.out.println(resultSet.getInt(1));
        }

        conn.close();
    }
}
