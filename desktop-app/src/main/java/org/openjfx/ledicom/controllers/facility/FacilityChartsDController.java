package org.openjfx.ledicom.controllers.facility;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import org.openjfx.utilities.database.DatabaseFacilityController;

import java.net.URL;
import java.sql.SQLException;
import java.util.*;

public class FacilityChartsDController implements Initializable {

    @FXML
    private CategoryAxis cities = new CategoryAxis();
    @FXML
    private NumberAxis quantity = new NumberAxis();
    @FXML
    private BarChart<String, Number> bc = new BarChart<>(cities, quantity);

    public void setChart() throws SQLException {
        ArrayList<String> citiesStr = DatabaseFacilityController.getFacilityCities();
        Set<String> data = new HashSet<>(citiesStr);

        XYChart.Series<String, Number> seria = new XYChart.Series<>();
        seria.setName("Количество");

        data.forEach(city -> seria.getData().add(new XYChart.Data<>(city, Collections.frequency(citiesStr, city))));

        bc.getData().add(seria);
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
