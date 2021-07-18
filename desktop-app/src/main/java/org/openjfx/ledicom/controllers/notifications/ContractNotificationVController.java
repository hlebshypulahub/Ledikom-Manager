package org.openjfx.ledicom.controllers.notifications;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import org.openjfx.ledicom.entities.EmployeeContract;
import org.openjfx.utilities.Global;
import org.openjfx.utilities.Validator;
import org.openjfx.utilities.database.DatabaseNotificationController;
import org.openjfx.utilities.exceptions.DayOfYearException;
import org.openjfx.utilities.panels.EmployeePanel;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ContractNotificationVController implements Initializable {
    @FXML
    private TableView<EmployeeContract> table;

    @FXML
    private TableColumn<EmployeeContract, String> fullNameCol;

    @FXML
    private TableColumn<EmployeeContract, String> contractCol;

    @FXML
    private Text contractNotificationsPeriodText;

    @FXML
    private Button contractNotificationsEditButton;

    @FXML
    private Pane contractNotificationsEditPane;

    @FXML
    private TextField contractNotificationsEditTF;

    @FXML
    private Button editContractNotificationButton;

    @FXML
    private Button showOnAppStartButton;

    @FXML
    private Button dontShowOnAppStartButton;

    @FXML
    void contractNotificationsEdit(ActionEvent event) {
        try {
            DatabaseNotificationController.contractNotificationsPeriodEdit(Validator.validateDayOfYear(contractNotificationsEditTF.getText()));
            contractNotificationsEditPane.setVisible(false);
            contractNotificationsEditButton.setVisible(true);
            contractNotificationsPeriodText.setText(String.valueOf(Validator.validateDayOfYear(contractNotificationsEditTF.getText())));
            table.setItems(DatabaseNotificationController.contractNotificationsList());
        } catch (DayOfYearException | SQLException exception) {
            System.out.println(exception.getMessage());
        }
    }

    @FXML
    void showContractNotificationsEditPane(ActionEvent event) {
        contractNotificationsEditPane.setVisible(true);
        contractNotificationsEditButton.setVisible(false);
    }

    public void setButtons() {
        if (DatabaseNotificationController.getContractOnAppStart()) {
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

        ObservableList<EmployeeContract> list = null;
        try {
            list = DatabaseNotificationController.contractNotificationsList();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        contractNotificationsPeriodText.setText(String.valueOf(DatabaseNotificationController.getContractNotificationsPeriod()));

        fullNameCol.setCellValueFactory(new PropertyValueFactory<>("employeeName"));
        contractCol.setCellValueFactory(new PropertyValueFactory<>("fullInfo"));

        table.setItems(list);
        table.setPlaceholder(new Label("В течение ближайших " + DatabaseNotificationController.getContractNotificationsPeriod() + " дней ни у одного сотрудника контракт не заканчивается!"));

        contractNotificationsEditTF.setTextFormatter(new TextFormatter<>(Validator.intValidationFormatter));

        setButtons();

        showOnAppStartButton.setOnAction(event -> {
            try {
                DatabaseNotificationController.setContractOnApStart(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            setButtons();
        });

        dontShowOnAppStartButton.setOnAction(event -> {
            try {
                DatabaseNotificationController.setContractOnApStart(false);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            setButtons();
        });

        table.setOnMouseClicked(event -> {
            if (table.getSelectionModel().getSelectedIndex() >= 0) {
                Global.setEmployee(table.getSelectionModel().getSelectedItem().getEmployee());
                try {
                    EmployeePanel.showEmployeeDetails();
                    EmployeePanel.showEmployeeManagement();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        table.setRowFactory(event -> {
            TableRow<EmployeeContract> row = new TableRow<>();
            row.setOnMouseClicked(e -> {
                if (e.getClickCount() == 2 && (!row.isEmpty())) {
                    try {
                        Global.setEmployee(table.getSelectionModel().getSelectedItem().getEmployee());
                        EmployeePanel.showEmployeeEdit();
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }
            });
            return row;
        });
    }
}
