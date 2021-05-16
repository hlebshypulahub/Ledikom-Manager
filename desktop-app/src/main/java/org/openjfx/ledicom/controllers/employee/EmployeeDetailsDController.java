package org.openjfx.ledicom.controllers.employee;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import org.openjfx.ledicom.entities.Course;
import org.openjfx.ledicom.entities.Edu;
import org.openjfx.utilities.Global;
import org.openjfx.utilities.converters.StringToLocalDateConverter;
import org.openjfx.utilities.database.DatabaseCourseController;
import org.openjfx.utilities.database.DatabaseEmployeeController;
import org.openjfx.utilities.panels.EmployeePanel;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class EmployeeDetailsDController implements Initializable {

    @FXML
    private Text fullNameText;
    @FXML
    private Text dobText;
    @FXML
    private Text phoneText;
    @FXML
    private Text addressText;
    @FXML
    private Text positionText;
    @FXML
    private Text salaryText;
    @FXML
    private Text ppeText;
    @FXML
    private Text hiringDateText;
    @FXML
    private Text start5Text;
    @FXML
    private Text end5Text;
    @FXML
    private Text courseDeadlineText;
    @FXML
    private Text courseHoursSumText;
    @FXML
    private Text eduNameText;
    @FXML
    private Text eduGraduationDateText;
    @FXML
    private Text categoryText;
    @FXML
    private Text maternityText;
    @FXML
    private Text childrenDataText;
    @FXML
    private Text noteText;
    @FXML
    private ListView<String> contractList;

    @FXML
    private TableView<Course> courseTable;
    @FXML
    private TableColumn<Course, String> courseNameCol;
    @FXML
    private TableColumn<Course, String> courseDescriptionCol;
    @FXML
    private TableColumn<Course, Integer> courseHoursCol;
    @FXML
    private TableColumn<Course, String> courseStartCol;
    @FXML
    private TableColumn<Course, String> courseEndCol;

    @FXML
    public void employeeEdit(ActionEvent event) throws IOException {
        EmployeePanel.showEmployeeEdit();
    }

    public void setCourseTableCss() {
        courseTable.setRowFactory(tv -> new TableRow<>() {
            @Override
            protected void updateItem(Course item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null)
                    setStyle("");
                else if (StringToLocalDateConverter.convert(item.getStartDate())
                                                   .compareTo(StringToLocalDateConverter.convert(start5Text.getText())) >= 0
                        && StringToLocalDateConverter.convert(item.getEndDate())
                                                     .compareTo(StringToLocalDateConverter.convert(end5Text.getText())) <= 0) {
                    setStyle("-fx-background-color: #b3ffac;");
                } else {
                    setStyle("");
                }
                if (item == null)
                    setStyle("");
                else if (tv.getSelectionModel().getSelectedItem() == item) {
                    setStyle("-fx-background-color: #0096c9;");
                }
            }
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fullNameText.setText(Global.getEmployee().getFullName());
        dobText.setText(Global.getEmployee().getDOB());
        phoneText.setText(Global.getEmployee().getPhone());
        addressText.setText(Global.getEmployee().getAddress());
        positionText.setText(Global.getEmployee().getPosition());
        salaryText.setText(String.valueOf(Global.getEmployee().getSalary()));
        ppeText.setText(Global.getEmployee().getPPE());
        start5Text.setText(Global.getEmployee().getFiveYearStart());
        end5Text.setText(Global.getEmployee().getFiveYearEnd());
        courseDeadlineText.setText(Global.getEmployee().getCourseDeadlineDate());
        courseHoursSumText.setText(String.valueOf(DatabaseCourseController.getRequiredCourseHours(Global.getEmployee().getPosition())
                - Global.getEmployee().getCourseHoursSum()));
        hiringDateText.setText(Global.getEmployee().getHiringDate());
        maternityText.setText(Global.getEmployee().getMaternityStartDate() == null
                || Global.getEmployee().getMaternityEndDate() == null ? ""
                : Global.getEmployee().getMaternityStartDate() + " - " + Global.getEmployee().getMaternityEndDate());
        childrenDataText.setText(Global.getEmployee().getChildrenData());
        noteText.setText(Global.getEmployee().getNote());

        Edu edu = DatabaseEmployeeController.getEmployeeEdu();
        assert edu != null;
        eduNameText.setText(edu.getName());
        eduGraduationDateText.setText(edu.getGraduationDate());

        categoryText.setText(Global.getEmployee().getCategory() == null ? "" : Global.getEmployee().getCategory() + " (" + (Global.getEmployee().getCategoryNum().equals("") ? "" : " номер "
                + Global.getEmployee().getCategoryNum() + ",") + " дата получения " + Global.getEmployee().getCategoryAssignmentDate() + " )");

        courseNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        courseDescriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        courseHoursCol.setCellValueFactory(new PropertyValueFactory<>("hours"));
        courseStartCol.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        courseEndCol.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        courseTable.setItems(DatabaseCourseController.getEmployeeCourses());

        courseTable.setPlaceholder(new Label("Сотрудник не имеет пройденных курсов"));
        setCourseTableCss();

        contractList.setItems(DatabaseEmployeeController.employeeContractList());
    }
}
