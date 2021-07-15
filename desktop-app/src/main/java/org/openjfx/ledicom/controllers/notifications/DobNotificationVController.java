package org.openjfx.ledicom.controllers.notifications;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import org.openjfx.ledicom.controllers.interfaces.EmployeeControllerInterface;
import org.openjfx.ledicom.entities.Employee;
import org.openjfx.utilities.Global;
import org.openjfx.utilities.TableDoubleClickSetter;
import org.openjfx.utilities.Validator;
import org.openjfx.utilities.comparators.DodNotificationsComparator;
import org.openjfx.utilities.database.DatabaseEmployeeController;
import org.openjfx.utilities.database.DatabaseNotificationController;
import org.openjfx.utilities.exceptions.DayOfYearException;
import org.openjfx.utilities.panels.EmployeePanel;

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
    private TableColumn<Employee, String> fullNameCol;
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
            table.setItems(DatabaseEmployeeController.dobNotificationsEmployeeList());
        } catch (DayOfYearException | SQLException exception) {
            System.out.println(exception.getMessage());
        }
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
        Global.getFooterPane().getChildren().clear();

        ObservableList<Employee> list = DatabaseEmployeeController.dobNotificationsEmployeeList();
        list.sort(Comparator.comparing(e -> LocalDate.parse(e.getDOB(), DateTimeFormatter.ofPattern("dd.MM.yyyy")).getDayOfYear()));
        dobNotificationsPeriodText.setText(String.valueOf(DatabaseNotificationController.getDobNotificationsPeriod()));

        fullNameCol.setCellValueFactory(new PropertyValueFactory<>("fullName"));
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
            } catch (SQLException e)  {
                e.printStackTrace();
            }
            setButtons();
        });

        table.setOnMouseClicked(event -> {
            if (table.getSelectionModel().getSelectedIndex() >= 0) {
                Global.setEmployee(table.getSelectionModel().getSelectedItem());
                try {
                    EmployeePanel.showEmployeeDetails();
                    showEmployeeManagement(table);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        TableDoubleClickSetter.setEmployeeTable(table);
    }
}
