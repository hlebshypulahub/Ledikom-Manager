package org.openjfx.utilities;

import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Comparator;

public class SortColumnByDate {
    private static boolean order = true;

    public static void setUp(TableColumn<?, String> column, Button button) {
        column.setGraphic(button);
        column.setSortable(false);
        button.setOnAction(event -> {
            Global.getEmployeeList().sort(Comparator.comparing(e -> LocalDate.parse(e.getDOB(), DateTimeFormatter.ofPattern("dd.MM.yyyy"))));
            if(order) {
                Collections.reverse(Global.getEmployeeList());
            }
            order = !order;
        });
    }
}
