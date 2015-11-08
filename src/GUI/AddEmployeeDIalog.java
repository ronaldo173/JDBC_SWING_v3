package GUI;

import DB.DBConnect;
import DB.Employee;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Arc2D;
import java.math.BigDecimal;

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

    private Employee previousEmployee;
    private boolean updateMode = false;

    public AddEmployeeDIalog(EmployeeSearchApp employeeSearchApp, DBConnect dbConnect, Employee thePreviousEployee,
                             boolean theUpdatMode) {
        this();
        this.dbConnect = dbConnect;
        this.employeeSearchApp = employeeSearchApp;
        this.previousEmployee = thePreviousEployee;
        this.updateMode = theUpdatMode;
        if (updateMode) {
            setTitle("Update employee!!");
            populateGui(previousEmployee);
        }
    }

    public AddEmployeeDIalog(EmployeeSearchApp employeeSearchApp, DBConnect dbConnect) {
        this(employeeSearchApp, dbConnect, null, false);
    }

    private void populateGui(Employee employee) {
        firstNameTextField.setText(employee.getFirstName());
        lastNameTextField.setText(employee.getLastName());
        emailTextField.setText(employee.getEmail());
        salaryTextField.setText(employee.getSalary().toString());
    }

    public AddEmployeeDIalog() {
        setTitle("Add employee dialog!");
        Image imageIcon = Toolkit.getDefaultToolkit().getImage("user_add.png");
        setIconImage(imageIcon);
        setBounds(150, 150, 450, 300);
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
        String firstName = firstNameTextField.getText();
        String lastName = lastNameTextField.getText();
        String email = emailTextField.getText();
        String salaryStr = salaryTextField.getText();

        BigDecimal salary = convertStringTobigDecimal(salaryStr);

        //test input
        JOptionPane.showMessageDialog(this, "First name: " + firstName + "\nLast name:" +
                lastName + "\nEmail: " + email + "\nSalary: " + salary);
        Employee employee = new Employee(lastName, firstName, email, salary);


        //---
        if (updateMode) {
            employee = previousEmployee;
            employee.setLastName(lastName);
            employee.setFirstName(firstName);
            employee.setSalary(salary);
        } else {
            employee = new Employee(lastName, firstName, email, salary);
        }
        try {
            if (updateMode) {
                dbConnect.updateEMployee(employee);
            } else {
                dbConnect.addEmployee(employee);
            }
            setVisible(false);
            dispose();
            employeeSearchApp.refreshEmployeesView();

            JOptionPane.showMessageDialog(employeeSearchApp, "employee added succesfully!",
                    "Employee saved!", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(employeeSearchApp, "Error! .." + e.getMessage() + "...xz ", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private BigDecimal convertStringTobigDecimal(String salaryStr) {
        BigDecimal result = null;

        try {
            double salaryDouble = Double.parseDouble(salaryStr);
            result = BigDecimal.valueOf(salaryDouble);
        } catch (Exception e) {
            System.out.println("Invalid value of salary");
            result = BigDecimal.valueOf(0.0);
        }
        return result;
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
