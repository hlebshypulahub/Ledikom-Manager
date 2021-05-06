package org.openjfx.ledicom.controllers.notifications;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import org.openjfx.ledicom.controllers.interfaces.EmployeeControllerInterface;
import org.openjfx.ledicom.entities.Employee;
import org.openjfx.utilities.Validator;
import org.openjfx.utilities.comparators.DodNotificationsComparator;
import org.openjfx.utilities.database.DatabaseEmployeeController;
import org.openjfx.utilities.database.DatabaseNotificationController;
import org.openjfx.utilities.exceptions.DayOfYearException;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.ResourceBundle;

public class DobNotificationVController implements Initializable, EmployeeControllerInterface {

    @FXML
    private TableView<Employee> table;
    @FXML
    private TableColumn<Employee, String> lastNameCol;
    @FXML
    private TableColumn<Employee, String> firstNameCol;
    @FXML
    private TableColumn<Employee, String> patronymicCol;
    @FXML
    private TableColumn<Employee, String> dobCol;
    @FXML
    private TableColumn<Employee, Integer> ageCol;
    @FXML
    private Pane dobNotificationsEditPane;
    @FXML
    private Button dobNotificationsEditButton;
    @FXML
    private TextField dobNotificationsEditTF;
    @FXML
    private Text dobNotificationsPeriodText;
    @FXML
    private Button showOnAppStartButton;
    @FXML
    private Button dontShowOnAppStartButton;

    @FXML
    public void showDobNotificationsEditPane(ActionEvent event) {
        dobNotificationsEditPane.setVisible(true);
        dobNotificationsEditButton.setVisible(false);
    }

    @FXML
    public void dobNotificationsEdit(ActionEvent event) {
        try {
            DatabaseNotificationController.dobNotificationsPeriodEdit(Validator.validateDayOfYear(dobNotificationsEditTF.getText()));
            dobNotificationsEditPane.setVisible(false);
            dobNotificationsEditButton.setVisible(true);
            dobNotificationsPeriodText.setText(String.valueOf(Validator.validateDayOfYear(dobNotificationsEditTF.getText())));
            //Global.setEmployeeList(DatabaseEmployeeController.dobNotificationsEmployeeList());
            table.setItems(DatabaseEmployeeController.dobNotificationsEmployeeList());
        } catch (DayOfYearException | SQLException exception) {
            System.out.println(exception.getMessage());
        }
    }

    @FXML
    public void showFullInfo(MouseEvent event) throws IOException {
        showEmployeeDetails(table);
        showEmployeeManagement(table);
    }

    public void setButtons() {
        if (DatabaseNotificationController.getOnAppStart()) {
            showOnAppStartButton.setVisible(false);
            dontShowOnAppStartButton.setVisible(true);
        } else {
            showOnAppStartButton.setVisible(true);
            dontShowOnAppStartButton.setVisible(false);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<Employee> list = DatabaseEmployeeController.dobNotificationsEmployeeList();
        //Global.setEmployeeList(DatabaseEmployeeController.dobNotificationsEmployeeList());
        list.sort(Comparator.comparing(e -> LocalDate.parse(e.getDOB(), DateTimeFormatter.ofPattern("dd.MM.yyyy")).getDayOfYear()));
        dobNotificationsPeriodText.setText(String.valueOf(DatabaseNotificationController.getDobNotificationsPeriod()));

        firstNameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        patronymicCol.setCellValueFactory(new PropertyValueFactory<>("patronymic"));
        dobCol.setCellValueFactory(new PropertyValueFactory<>("DOB"));
        ageCol.setCellValueFactory(new PropertyValueFactory<>("dobAge"));
        dobCol.setComparator(new DodNotificationsComparator());

        table.setItems(list);
        table.setPlaceholder(new Label("Данные не найдены"));

        dobNotificationsEditTF.setTextFormatter(new TextFormatter<>(Validator.intValidationFormatter));

        setButtons();

        showOnAppStartButton.setOnAction(event -> {
            try {
                DatabaseNotificationController.setOnApStart(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            setButtons();
        });

        dontShowOnAppStartButton.setOnAction(event -> {
            try {
                DatabaseNotificationController.setOnApStart(false);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            setButtons();
        });
    }
}
