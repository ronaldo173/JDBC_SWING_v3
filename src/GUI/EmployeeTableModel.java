package GUI;

import DB.Employee;

import javax.swing.table.AbstractTableModel;
import java.util.List;

/**
 * Created by Santer on 04.11.2015.
 */
public class EmployeeTableModel extends AbstractTableModel {
    private static final int LAST_NAME_COL = 0;
    private static final int FIRST_NAME_COL = 1;
    private static final int EMAIL_COL = 2;
    private static final int SALARY_COL = 3;

    private String [] columnNames = {"Last Name", "First name", "Email", "SalAry"};
    private List<Employee> employees;

    public EmployeeTableModel(List<Employee> employeeList) {
        this.employees = employeeList;
    }

    @Override
    public int getRowCount() {
        return employees.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Employee employee = employees.get(rowIndex);

        switch (columnIndex){
            case LAST_NAME_COL:
                return employee.getLastName();
            case FIRST_NAME_COL:
                return employee.getFirstName();
            case EMAIL_COL:
                return employee.getEmail();
            case SALARY_COL:
                return employee.getSalary();
            default:
                return employee.getLastName();
        }
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return getValueAt(0, columnIndex).getClass();
    }
}
