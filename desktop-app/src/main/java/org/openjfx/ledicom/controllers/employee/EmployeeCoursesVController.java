package org.openjfx.ledicom.controllers.employee;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import org.openjfx.ledicom.controllers.interfaces.EmployeeControllerInterface;
import org.openjfx.ledicom.entities.EmployeeCourseData;
import org.openjfx.utilities.Global;
import org.openjfx.utilities.database.DatabaseCourseController;
import org.openjfx.utilities.database.DatabaseEmployeeController;
import org.openjfx.utilities.panels.EmployeePanel;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class EmployeeCoursesVController implements Initializable, EmployeeControllerInterface {
    @FXML
    private TableView<EmployeeCourseData> table;
    @FXML
    private TableColumn<EmployeeCourseData, String> employeeNameCol;
    @FXML
    private TableColumn<EmployeeCourseData, String> positionCol;
    @FXML
    private TableColumn<EmployeeCourseData, String> eduCol;
    @FXML
    private TableColumn<EmployeeCourseData, String> categoryCol;
    @FXML
    private TableColumn<EmployeeCourseData, String> courseNameCol;
    @FXML
    private TableColumn<EmployeeCourseData, String> courseDataCol;
    @FXML
    private TableColumn<EmployeeCourseData, String> nextCourseCol;

    @FXML
    public void showFullInfo(MouseEvent event) throws IOException {
        showEmployeesCoursesDetails(table);
    }

    public void setTableCss() {
        table.setRowFactory(tv -> new TableRow<>() {
            @Override
            protected void updateItem(EmployeeCourseData item, boolean empty) {
                super.updateItem(item, empty);
                System.out.println("1");
                if (item == null) {
                    System.out.println("2");
                    setStyle("");
                } else if (!item.getEmployeeName().equals("")) {
                    System.out.println("3");
                    setStyle("-fx-background-color: #ebffdb; -fx-border-width: 2 0 0 0; -fx-border-color: black");
                } else {
                    System.out.println("4");
                    setStyle("");
                }
                if (item == null) {
                    System.out.println("5");
                    setStyle("");
                } else if (tv.getSelectionModel().getSelectedItem() == item) {
                    System.out.println("6");
                    setStyle("-fx-background-color: #0096c9;  -fx-border-width: 2 0 0 0; -fx-border-color: black");
                }
            }
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Global.getFooterPane().getChildren().clear();

        employeeNameCol.setCellValueFactory(new PropertyValueFactory<>("employeeName"));
        positionCol.setCellValueFactory(new PropertyValueFactory<>("position"));
        eduCol.setCellValueFactory(new PropertyValueFactory<>("edu"));
        categoryCol.setCellValueFactory(new PropertyValueFactory<>("category"));
        courseNameCol.setCellValueFactory(new PropertyValueFactory<>("courseName"));
        courseDataCol.setCellValueFactory(new PropertyValueFactory<>("courseData"));
        nextCourseCol.setCellValueFactory(new PropertyValueFactory<>("nextCourseData"));
        table.setItems(DatabaseCourseController.getEmployeesCourses());

        employeeNameCol.setSortable(false);
        positionCol.setSortable(false);
        eduCol.setSortable(false);
        categoryCol.setSortable(false);
        courseDataCol.setSortable(false);
        courseNameCol.setSortable(false);
        nextCourseCol.setSortable(false);

        setTableCss();

        table.setRowFactory(event -> {
            TableRow<EmployeeCourseData> row = new TableRow<>();
            row.setOnMouseClicked(e -> {
                if (e.getClickCount() == 2 && (!row.isEmpty())) {
                    try {
                        Global.setEmployee(DatabaseEmployeeController.getEmployee(table.getSelectionModel().getSelectedItem().getEmployeeId()));
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
