package GUI;

import DB.DBConnect;
import DB.Employee;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Santer on 04.11.2015.
 */
public class EmployeeSearchApp extends JFrame {
    private JPanel Application;
    private JTextField lastNameTextField;
    private JButton searchButton, Add_button, Update_button;
    private JTable table1;
    private JButton deleteButton;
    private JScrollPane jScrollPane;

    private DBConnect dbConnect = null;
    private ImageIcon imageIcon;

    private Image imageMainIcon;


    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                EmployeeSearchApp frame = new EmployeeSearchApp();
                frame.setVisible(true);
            }
        });
    }

    public EmployeeSearchApp() {
        try {
            dbConnect = new DBConnect();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error" + e,
                    "Error", JOptionPane.ERROR_MESSAGE);
        }

        setTitle("Searching app");
        imageIcon = new ImageIcon("search.png", "go");
        imageMainIcon = Toolkit.getDefaultToolkit().getImage("search_main.png");
        setIconImage(imageMainIcon);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //middle of window
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
//        JOptionPane.showMessageDialog(this, screenSize); //show size
        setBounds((int) (screenSize.getWidth() * 0.2), (int) (screenSize.getHeight() * 0.3), (int) (0.9 * screenSize.getHeight()), 450);
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

        searchButton = new JButton("Search", imageIcon);

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
        table1.setFont(new Font("Verdana", Font.ITALIC, 18));
        jScrollPane.setViewportView(table1);

        JPanel jPanel_1 = new JPanel();
        Application.add(jPanel_1, BorderLayout.SOUTH);
        jPanel_1.add(Add_button);
        Add_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddEmployeeDIalog dIalog = new AddEmployeeDIalog(EmployeeSearchApp.this, dbConnect);
                dIalog.setVisible(true);
            }
        });

        jPanel_1.add(Update_button);
        Update_button.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                int row = table1.getSelectedRow();
                if (row < 0) {
                    JOptionPane.showMessageDialog(EmployeeSearchApp.this, "not selected employee", "Errorr",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Employee tempEmployee = (Employee) table1.getValueAt(row, EmployeeTableModel.OBJECT_COL);
                AddEmployeeDIalog dIalog = new AddEmployeeDIalog(EmployeeSearchApp.this, dbConnect,
                        tempEmployee, true);

                dIalog.setVisible(true);
            }
        });
        jPanel_1.add(deleteButton);
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = table1.getSelectedRow();
                if (row < 0) {
                    JOptionPane.showMessageDialog(EmployeeSearchApp.this, "You don't select any row", "Not selected",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Employee checkedEmployee = (Employee) table1.getValueAt(row, EmployeeTableModel.OBJECT_COL);

                int confirm = JOptionPane.showConfirmDialog(EmployeeSearchApp.this, "Shure want to delete row: \n" +
                                checkedEmployee.getFirstName() + "\n" + checkedEmployee.getLastName() + "\n" +
                                checkedEmployee.getEmail(), "Confirm",
                        JOptionPane.YES_NO_OPTION);
                if (confirm == 0) {
                    try {
                        dbConnect.deleteEmployee(checkedEmployee.getId());
                        EmployeeSearchApp.this.refreshEmployeesView();
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
//                    System.out.println("deleted!");
                    JOptionPane.showMessageDialog(EmployeeSearchApp.this, "Employee deleted!", "Success",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
    }


    public void refreshEmployeesView() {
        try {
            List<Employee> employeeList = dbConnect.getAllEmployees();
            EmployeeTableModel model = new EmployeeTableModel(employeeList);
            table1.setModel(model);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error " + "Error" + JOptionPane.ERROR_MESSAGE);
        }
    }
}
