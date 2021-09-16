package org.openjfx.utilities.docs;

import javafx.collections.ObservableList;
import org.apache.commons.io.FileUtils;
import org.openjfx.ledicom.entities.Employee;
import org.openjfx.ledicom.entities.Facility;
import org.openjfx.utilities.EmployeeTableValue;
import org.openjfx.utilities.Validator;
import org.openjfx.utilities.formatters.DateFormatter;

import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;

public final class EmployeeTableDoc {

    private EmployeeTableDoc() {

    }

    public static void createTable(ObservableList<Employee> employeeList, EmployeeTableValue employeeTableValue, Facility facility) throws IOException {

        String title = "Список сотрудников" + (facility == null ? "" : " «" + facility.getName() + "»") + " (" + Validator.validateString(employeeTableValue.getName()) + ") ";

        String body = "<p><strong>ЧТПУП «Ледиком»&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;" +
                "&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;" +
                "&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;</strong></p>\n";


        body += "<p style=\"text-align: center;\"><strong>" + "Список сотрудников" + (facility == null ? "" : " «" + facility.getName() + "»") + "</strong></p><br>";

        body += "<table style=\"border-collapse: collapse; width: 100%;\" border=\"1\">\n" +
                "<tbody>";

        body += "<tr>\n" +
                "<td style=\"width: 8.09659%; text-align: center;\"><strong>№ п/п</strong></td>\n" +
                "<td style=\"width: 21.0227%; text-align: center;\"><strong>ФИО</strong></td>\n" +
                "<td style=\"width: 26.8466%; text-align: center;\"><strong>" + Validator.validateString(employeeTableValue.getName()) + "</strong></td>\n" +
                "</tr>";

        for (int i = 0; i < employeeList.size(); i++) {
            body += "<tr>\n" +
                    "<td style=\"width: 8.09659%; text-align: center;\">" + (i + 1) + "</td>\n" +
                    "<td style=\"width: 21.0227%;\">&nbsp;" + Validator.validateString(employeeList.get(i).getFullName()) + "</td>\n" +
                    "<td style=\"width: 26.8466%;\">&nbsp;" + Validator.validateString(employeeList.get(i).get(employeeTableValue)) + "</td>\n" +
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


