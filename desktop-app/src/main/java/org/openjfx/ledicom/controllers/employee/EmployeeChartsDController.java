package org.openjfx.ledicom.controllers.employee;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import org.openjfx.ledicom.entities.Employee;
import org.openjfx.utilities.database.DatabaseEmployeeController;
import org.openjfx.utilities.formatters.AxisIntegerFormatter;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class EmployeeChartsDController implements Initializable {

    @FXML
    private CategoryAxis positions = new CategoryAxis();
    @FXML
    private NumberAxis quantity = new NumberAxis();
    @FXML
    private StackedBarChart<String, Number> sbc = new StackedBarChart<>(positions, quantity);

    public void setChart() throws SQLException {
        ArrayList<Employee> employeeList = DatabaseEmployeeController.getEmployeePositions();

        int fProvizor = 0;
        int sProvizor = 0;
        int hProvizor = 0;
        int nProvizor = 0;
        int fFarmacevt = 0;
        int sFarmacevt = 0;
        int hFarmacevt = 0;
        int nFarmacevt = 0;

        for (Employee employee : employeeList) {
            if (employee.getPosition().equals("Провизор")) {
                switch (employee.getCategory()) {
                    case "Первая" :
                        fProvizor++;
                        break;
                    case "Вторая" :
                        sProvizor++;
                        break;
                    case "Высшая" :
                        hProvizor++;
                        break;
                    case "":
                        nProvizor++;
                        break;
                }
            } else if (employee.getPosition().equals("Фармацевт")) {
                switch (employee.getCategory()) {
                    case "Первая" :
                        fFarmacevt++;
                        break;
                    case "Вторая" :
                        sFarmacevt++;
                        break;
                    case "Высшая" :
                        hFarmacevt++;
                        break;
                    case "":
                        nFarmacevt++;
                        break;
                }
            }
        }

        XYChart.Series<String, Number> firstCat = new XYChart.Series<>();
        firstCat.setName("Первая категория");
        XYChart.Series<String, Number> secondCat = new XYChart.Series<>();
        secondCat.setName("Вторая категория");
        XYChart.Series<String, Number> highestCat = new XYChart.Series<>();
        highestCat.setName("Высшая категория");
        XYChart.Series<String, Number> noCat = new XYChart.Series<>();
        noCat.setName("Без категории");

        firstCat.getData().add(new XYChart.Data<>("Провизор", fProvizor));
        firstCat.getData().add(new XYChart.Data<>("Фармацевт", fFarmacevt));
        secondCat.getData().add(new XYChart.Data<>("Провизор", sProvizor));
        secondCat.getData().add(new XYChart.Data<>("Фармацевт", sFarmacevt));
        highestCat.getData().add(new XYChart.Data<>("Провизор", hProvizor));
        highestCat.getData().add(new XYChart.Data<>("Фармацевт", hFarmacevt));
        noCat.getData().add(new XYChart.Data<>("Провизор", nProvizor));
        noCat.getData().add(new XYChart.Data<>("Фармацевт", nFarmacevt));

        sbc.getData().addAll(noCat, firstCat, secondCat, highestCat);

        AxisIntegerFormatter.setFormatter(quantity);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            setChart();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
