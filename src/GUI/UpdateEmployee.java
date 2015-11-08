package GUI;

import DB.DBConnect;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class UpdateEmployee extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField salaryTF;
    private JLabel labelLN;
    private JLabel email;
    private JLabel labelFN;
    private JTextField emailTF;
    private JTextField firstNameTF;
    private JTextField lastNameTF;

    private DBConnect dbConnect;
    private EmployeeSearchApp employeeSearchApp;

    public UpdateEmployee() {
        setTitle("Update employee!");
        Image image = Toolkit.getDefaultToolkit().getImage("refresh.png");
        this.setIconImage(image);

        setLocationRelativeTo(null);
        setBounds(150, 150, 450, 300);
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
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

    public UpdateEmployee(EmployeeSearchApp employeeSearchApp, DBConnect dbConnect) {
        this();
        this.dbConnect = dbConnect;
        this.employeeSearchApp = employeeSearchApp;
    }

    private void onOK() {
// add your code here
        dispose();
    }

    private void onCancel() {
// add your code here if necessary
        dispose();
    }

    public static void main(String[] args) {
        UpdateEmployee dialog = new UpdateEmployee();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
