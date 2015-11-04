package GUI;

import DB.DBConnect;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by Santer on 04.11.2015.
 */
public class EmployeeSearchApp extends JFrame {
    private JPanel Application;
    private JTextField lastNameTextField;
    private JButton searchButton;
    private JTable table1;

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

        setTitle("my Employee search app");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
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
            }
        });
        panel.add(searchButton);
        table1 = new JTable();

    }



}
