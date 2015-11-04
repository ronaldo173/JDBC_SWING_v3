package GUI;

import DB.DBConnect;
import DB.Employee;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

/**
 * Created by Santer on 04.11.2015.
 */
public class EmployeeSearchApp extends JFrame {
    private JPanel Application;
    private JTextField lastNameTextField;
    private JButton searchButton;
    private JTable table1;
    private JScrollPane jScrollPane;

    private DBConnect dbConnect = null;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                EmployeeSearchApp frame = new EmployeeSearchApp();
                frame.setVisible(true);
            }
        });
    }

    public EmployeeSearchApp() {
        try{
             dbConnect = new DBConnect();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error" + e,
                    "Error", JOptionPane.ERROR_MESSAGE);
        }

        setTitle("Searching app");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //middle of window
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
//        JOptionPane.showMessageDialog(this, screenSize); //show size
        setBounds((int) (screenSize.getWidth()*0.2), (int) (screenSize.getHeight()*0.3), (int) (0.9*screenSize.getHeight()), 450);
        Application = new JPanel();
        Application.setBorder(new EmptyBorder(5, 5, 5, 5));
        Application.setLayout(new BorderLayout(0, 0));
        setContentPane(Application);

        JPanel panel = new JPanel();
        FlowLayout flowLayout = (FlowLayout) panel.getLayout();
        flowLayout.setAlignment(FlowLayout.LEFT);
        Application.add(panel, BorderLayout.NORTH);

        JLabel ldblEnterLN = new JLabel("Enter last name");
        panel.add(ldblEnterLN);

        lastNameTextField = new JTextField();
        panel.add(lastNameTextField);
        lastNameTextField.setColumns(10);

        searchButton = new JButton("Search");

        searchButton.addComponentListener(new ComponentAdapter() {
        });
        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                //get last name of text field

                //cal DBConnect and get employees for last name

                //if lastName empty get all employees

                //print employees

                try {
                    String lastName = lastNameTextField.getText();
                    java.util.List<Employee> employeeList = null;

                    if (lastName != null && lastName.trim().length() > 0) {
                        employeeList = dbConnect.searchEMployee(lastName);
                    } else {
                        employeeList = dbConnect.getAllEmployees();
                    }

//                    for (Employee employee : employeeList) {
//                        System.out.println(employee);
//                    }

                    EmployeeTableModel model = new EmployeeTableModel(employeeList);
                    table1.setModel(model);
                } catch (SQLException e1) {
                    JOptionPane.showMessageDialog(EmployeeSearchApp.this, "Error: " + e, "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        jScrollPane = new JScrollPane();
        Application.add(jScrollPane, BorderLayout.CENTER);
        panel.add(searchButton);
        table1 = new JTable();
        table1.setFont(new Font("Verdana",Font.ITALIC, 18));
        jScrollPane.setViewportView(table1);

    }



}
