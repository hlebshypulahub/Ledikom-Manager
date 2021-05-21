package org.openjfx.utilities;

import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import org.openjfx.utilities.exceptions.DayOfYearException;
import org.openjfx.utilities.exceptions.TextException;
import org.openjfx.utilities.formatters.DateTimeFormatter;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.function.UnaryOperator;

public final class Validator {

    private Validator() {

    }

    public static String validateName(String name, TextField textField) {
        if (name == null || name.length() == 0) {
            MyAlert.showAndWait("ERROR", "Ошибка", "Неправильно введено '" + textField.getPromptText() + "'",
                    "Поле '" + textField.getPromptText() + "' обязательно для заполнения.");
            throw new TextException("Invalid name validation");
        }

        name = name.length() > 1 ?
                name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase()
                : name.substring(0, 1).toUpperCase();

        return name;
    }

    public static String validateString(String str) {
        return str == null ? "" : str;
    }

    public static String validateDate(DatePicker datePicker) {
        if (datePicker.getEditor().getText().isEmpty()) {
            return null;
        } else {
            try {
                LocalDate ld = LocalDate.parse(datePicker.getEditor().getText(), DateTimeFormatter.getDateTimeFormatter());
                return datePicker.getEditor().getText();
            } catch (DateTimeParseException exception) {
                exception.printStackTrace();
                MyAlert.showAndWait("ERROR", "Ошибка", "Неверный формат даты.", "");
                datePicker.requestFocus();
                throw exception;
            }
        }
    }

    public static Integer validateDayOfYear(String number) {
        if (number.equals("")) {
            return 7;
        } else if (!(Integer.parseInt(number) >= 0 && Integer.parseInt(number) <= 365)) {
            MyAlert.showAndWait("ERROR", "Ошибка", "Введите число от 0 до 365!", "");
            throw new DayOfYearException("Integer is out of bounds");
        }

        return Integer.parseInt(number);
    }

    public static UnaryOperator<TextFormatter.Change> nameValidationFormatter = change -> {
        if (change.getText().matches("[а-яА-ЯёЁ]+")) {
            return change;
        } else {
            change.setText("");
            return change;
        }
    };

    public static UnaryOperator<TextFormatter.Change> intValidationFormatter = change -> {
        if (change.getText().matches("\\d+")) {
            return change;
        } else {
            change.setText("");
            return change;
        }
    };

    public static UnaryOperator<TextFormatter.Change> phoneValidationFormatter = change -> {
        if (change.getText().matches("^[\\+\\d]?(?:[\\d-.\\s()]*)$")) {
            return change;
        } else {
            change.setText("");
            return change;
        }
    };
}
