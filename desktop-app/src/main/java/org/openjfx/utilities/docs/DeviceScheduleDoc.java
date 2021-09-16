package org.openjfx.utilities.docs;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.apache.commons.io.FileUtils;
import org.openjfx.ledicom.entities.Device;
import org.openjfx.utilities.formatters.DateFormatter;

import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.stream.Collectors;

public final class DeviceScheduleDoc {
    private DeviceScheduleDoc() {

    }

    public static void createDocument(ObservableList<Device> observableList, String year) throws IOException {
        String title = "ГРАФИК ПРОВЕРКИ СРЕДСТВ ИЗМЕРЕНИЙ НА " + year + " ГОД ";

        String body = "<p style=\"text-align: right;\">Утверждаю<br />Директор ЧТПУП &laquo;Ледиком&raquo;<br />_____________ С. Н.\n" +
                "    Шипуло<br />&laquo;____&raquo; ____________ 20___ г</p>\n" +
                "<p style=\"text-align: right;\">&nbsp;</p>\n" +
                "<p style=\"text-align: center;\">ГРАФИК<br />ПРОВЕРКИ СРЕДСТВ ИЗМЕРЕНИЙ<br />НА " + year + " ГОД</p>\n" +
                "<table style=\"border-collapse: collapse; width: 100%;\" border=\"1\">\n" +
                "    <tbody>\n" +
                "        <tr style=\"border: 2px solid black;\">\n" +
                "            <td style=\"width: 8%;  text-align: center;\"><strong>Дата поверки</strong></td>\n" +
                "            <td style=\"width: 22%;  text-align: center;\"><strong>Наименование<br />прибора</strong></td>\n" +
                "            <td style=\"width: 10%;  text-align: center;\"><strong>Номер<br />прибора</strong></td>\n" +
                "            <td style=\"width: 22%;  text-align: center;\"><strong>Местонахождение<br />прибора</strong></td>\n" +
                "        </tr>";

        String quater = "I кв.";
        String finalQuater = quater;
        ObservableList<Device> tempList = observableList.stream().filter(d -> d.getNextVerificationDate().substring(0, d.getNextVerificationDate().indexOf('.') + 1).equals(finalQuater))
                                                        .collect(Collectors.toCollection(FXCollections::observableArrayList));
        if (!tempList.isEmpty())
            body += printQuater(tempList, quater);

        quater = "II кв.";
        String finalQuater1 = quater;
        tempList = observableList.stream().filter(d -> d.getNextVerificationDate().substring(0, d.getNextVerificationDate().indexOf('.') + 1).equals(finalQuater1))
                                 .collect(Collectors.toCollection(FXCollections::observableArrayList));
        if (!tempList.isEmpty())
            body += printQuater(tempList, quater);

        quater = "III кв.";
        String finalQuater2 = quater;
        tempList = observableList.stream().filter(d -> d.getNextVerificationDate().substring(0, d.getNextVerificationDate().indexOf('.') + 1).equals(finalQuater2))
                                 .collect(Collectors.toCollection(FXCollections::observableArrayList));
        if (!tempList.isEmpty())
            body += printQuater(tempList, quater);

        quater = "IV кв.";
        String finalQuater3 = quater;
        tempList = observableList.stream().filter(d -> d.getNextVerificationDate().substring(0, d.getNextVerificationDate().indexOf('.') + 1).equals(finalQuater3))
                                 .collect(Collectors.toCollection(FXCollections::observableArrayList));
        if (!tempList.isEmpty())
            body += printQuater(tempList, quater);

        body += "</tbody>\n" +
                "</table>\n";

        String htmlString = htmlTemplate.htmlTop + body + htmlTemplate.htmlBottom;
        htmlString = htmlString.replace("$title", title);
        File htmlFile = new File(FileSystemView.getFileSystemView().getDefaultDirectory().getPath() + "/Документы программа Ледиком/" + title + DateFormatter.format(LocalDate.now()) + ".html");
        FileUtils.writeStringToFile(htmlFile, htmlString, StandardCharsets.UTF_8.name());

        FileOpener.openFile(htmlFile);
    }

    public static String printQuater(ObservableList<Device> observableList, String quater) {
        String body = "";

        for (int i = 0; i < observableList.size(); i++) {
            body += "<tr>\n" +
                    "            <td style=\"width: 8%; text-align: center;\">" + (i == 0 ? quater : "") + "</td>\n" +
                    "            <td style=\"width: 22%;\">" + observableList.get(i).getType() + "</td>\n" +
                    "            <td style=\"width: 10%;\">" + observableList.get(i).getNumber() + "</td>\n" +
                    "            <td style=\"width: 22%;\">" + observableList.get(i).getFacility() + "</td>\n" +
                    "        </tr>";
        }
        body += "<tr style=\"border-bottom: 2px solid black;\">\n" +
                "            <td style=\"width: 8%; text-align: center;\">&nbsp;</td>\n" +
                "            <td style=\"width: 22%;\">&nbsp;</td>\n" +
                "            <td style=\"width: 10%;\">&nbsp;</td>\n" +
                "            <td style=\"width: 22%;\">&nbsp;</td>\n" +
                "        </tr>";

        return body;
    }
}
