package GUI;

import DB.DBConnect;
import DB.Employee;

import javax.swing.*;
import java.awt.event.*;
import java.math.BigDecimal;
import java.util.DoubleSummaryStatistics;

public class AddEmployeeDIalog extends JDialog {
    private JPanel contentPane;
    private JButton saveEmployee;
    private JButton buttonCancel;
    private JTextField firstNameTextField;
    private JTextField emailTextField;
    private JTextField lastNameTextField;
    private JTextField salaryTextField;
    private JLabel FirstNameLabel;
    private JLabel LastNameLabel;
    private JLabel EmailLabel;
    private JLabel salaryLabel;

    private DBConnect dbConnect;
    private EmployeeSearchApp employeeSearchApp;

    public AddEmployeeDIalog(EmployeeSearchApp employeeSearchApp, DBConnect dbConnect) {
        this();
        this.dbConnect = dbConnect;
        this.employeeSearchApp = employeeSearchApp;
    }

    public AddEmployeeDIalog() {
        setTitle("Add employee dialog!");
        setBounds(150, 150, 450, 250);
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(saveEmployee);

        saveEmployee.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

// call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

// call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onOK() {
// add your code here
//        dispose();
        //get employee from gui
        String firstName = firstNameTextField.getText();
        String lastName = lastNameTextField.getText();
        String email = emailTextField.getText();
        String salaryStr = salaryTextField.getText();

        BigDecimal salary = null;
        try {
            salary = BigDecimal.valueOf(Integer.parseInt(salaryStr));
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error salary = BigDecimal.valueOf..", JOptionPane.ERROR_MESSAGE);
        }

        //test input
        JOptionPane.showMessageDialog(this, "First name: " + firstName + "\nLast name:" +
                lastName + "\nEmail: " + email + "\nSalary: " + salary);
        Employee employee = new Employee(lastName, firstName, email, salary);

        try {
            dbConnect.addEmployee(employee);
            setVisible(false);
            dispose();
            employeeSearchApp.refreshEmployeesView();

            JOptionPane.showMessageDialog(employeeSearchApp, "employee added succesfully!",
                    "Employee added!", JOptionPane.INFORMATION_MESSAGE);
            System.out.println("Added");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(employeeSearchApp, "Error! .." + e.getMessage() + "...xz ", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }


    private void onCancel() {
// add your code here if necessary
        setVisible(false);
        dispose();
    }

    public static void main(String[] args) {
        AddEmployeeDIalog dialog = new AddEmployeeDIalog();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
