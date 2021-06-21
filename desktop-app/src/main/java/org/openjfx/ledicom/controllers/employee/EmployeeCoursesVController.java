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
        table.setRowFactory(tv -> {
            TableRow<EmployeeCourseData> row = new TableRow<>() {
                @Override
                protected void updateItem(EmployeeCourseData item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item == null) {
                        setStyle("");
                    } else if (!item.getEmployeeName().equals("")) {
                        setStyle("-fx-background-color: #ebffdb; -fx-border-width: 2 0 0 0; -fx-border-color: black");
                    } else {
                        setStyle("");
                    }
                    if (item == null) {
                        setStyle("");
                    } else if (tv.getSelectionModel().getSelectedItem() == item && item.getEmployeeName().equals("")) {
                        setStyle("-fx-background-color: #0096c9;");
                    } else if (tv.getSelectionModel().getSelectedItem() == item) {
                        setStyle("-fx-background-color: #0096c9;  -fx-border-width: 2 0 0 0; -fx-border-color: black");
                    }
                }
            };

            row.setOnMouseClicked(e -> {
                if (e.getClickCount() == 2 && (!row.isEmpty())) {
                    try {
                        Global.setEmployee(DatabaseEmployeeController.getEmployee(tv.getSelectionModel().getSelectedItem().getEmployeeId()));
                        EmployeePanel.showEmployeeEdit();
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }
            });
            return row;
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
    }
}
