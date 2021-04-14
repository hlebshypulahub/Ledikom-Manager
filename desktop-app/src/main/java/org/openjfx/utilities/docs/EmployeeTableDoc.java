package org.openjfx.utilities.docs;

import javafx.collections.ObservableList;
import org.apache.commons.io.FileUtils;
import org.openjfx.ledicom.entities.Employee;
import org.openjfx.ledicom.entities.Facility;
import org.openjfx.utilities.EmployeeValue;
import org.openjfx.utilities.Validator;
import org.openjfx.utilities.formatters.DateFormatter;

import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;

public class EmployeeTableDoc {
    public static void createTable(ObservableList<Employee> employeeList, EmployeeValue employeeValue, Facility facility) throws IOException {

        String title = "Список сотрудников" + (facility == null ? "" : " «" + facility.getName() + "»") + " (" + Validator.validateString(employeeValue.getName()) + ") ";

        String body = "<p><strong>ЧТПУП \"Ледиком\"&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;" +
                "&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;" +
                "&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;</strong><strong>" + DateFormatter.format(LocalDate.now()) + "&nbsp; г.</strong></p>\n";


        body += "<p style=\"text-align: center;\"><strong>" + "Список сотрудников" + (facility == null ? "" : " «" + facility.getName() + "»") + "</strong></p><br>";

        body += "<table style=\"border-collapse: collapse; width: 100%;\" border=\"1\">\n" +
                "<tbody>";

        body += "<tr>\n" +
                "<td style=\"width: 8.09659%; text-align: center;\"><strong>№ п/п</strong></td>\n" +
                "<td style=\"width: 21.0227%; text-align: center;\"><strong>Фамилия</strong></td>\n" +
                "<td style=\"width: 20.8807%; text-align: center;\"><strong>Имя</strong></td>\n" +
                "<td style=\"width: 23.1534%; text-align: center;\"><strong>Отчество</strong></td>\n" +
                "<td style=\"width: 26.8466%; text-align: center;\"><strong>" + Validator.validateString(employeeValue.getName()) + "</strong></td>\n" +
                "</tr>";

        for (int i = 0; i < employeeList.size(); i++) {
            body += "<tr>\n" +
                    "<td style=\"width: 8.09659%; text-align: center;\">" + (i + 1) + "</td>\n" +
                    "<td style=\"width: 21.0227%;\">&nbsp;" + Validator.validateString(employeeList.get(i).getLastName()) + "</td>\n" +
                    "<td style=\"width: 20.8807%;\">&nbsp;" + Validator.validateString(employeeList.get(i).getFirstName()) + "</td>\n" +
                    "<td style=\"width: 23.1534%;\">&nbsp;" + Validator.validateString(employeeList.get(i).getPatronymic()) + "</td>\n" +
                    "<td style=\"width: 26.8466%;\">&nbsp;" + Validator.validateString(employeeList.get(i).get(employeeValue)) + "</td>\n" +
                    "</tr>";
        }

        body += "</tbody>\n" +
                "</table>";

        String htmlString = htmlTemplate.htmlTop + body + htmlTemplate.htmlBottom;
        htmlString = htmlString.replace("$title", title);
        File htmlFile = new File(FileSystemView.getFileSystemView().getDefaultDirectory().getPath() + "/Документы программа Ледиком/" + title + DateFormatter.format(LocalDate.now()) + ".html");
        FileUtils.writeStringToFile(htmlFile, htmlString, StandardCharsets.UTF_8.name());

        FileOpener.openFile(htmlFile);
    }
}


