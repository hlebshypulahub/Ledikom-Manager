package org.openjfx.utilities;

import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import org.openjfx.ledicom.entities.Employee;
import org.openjfx.utilities.panels.EmployeePanel;

import java.io.IOException;

public final class TableDoubleClickSetter {
    private TableDoubleClickSetter() {

    }

    public static void setEmployeeTable(TableView<Employee> table) {
        table.setRowFactory(event -> {
            TableRow<Employee> row = new TableRow<>();
            row.setOnMouseClicked(e -> {
                if (e.getClickCount() == 2 && (!row.isEmpty())) {
                    try {
                        Global.setEmployee(table.getSelectionModel().getSelectedItem());
                        EmployeePanel.showEmployeeEdit();
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }
            });
            return row;
        });
    }

    public static void setEmployeeTableFull(TableView<Employee> table) {
        table.setRowFactory(event -> {
            TableRow<Employee> row = new TableRow<>();
            row.setOnMouseClicked(e -> {
                if (e.getClickCount() == 2 && (!row.isEmpty())) {
                    try {
                        Global.setEmployee(table.getSelectionModel().getSelectedItem());
                        EmployeePanel.showEmployeeEdit();
                        EmployeePanel.showEmployeeDetails();
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }
            });
            return row;
        });
    }
}
