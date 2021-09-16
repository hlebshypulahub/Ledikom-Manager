package org.openjfx.utilities.docs;

import javafx.collections.ObservableList;
import org.apache.commons.io.FileUtils;
import org.openjfx.ledicom.entities.Device;
import org.openjfx.ledicom.entities.Facility;
import org.openjfx.utilities.formatters.DateFormatter;

import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;

public final class DeviceFacilityDoc {
    private DeviceFacilityDoc() {

    }

    public static void createTable(ObservableList<Device> observableList, Facility facility) throws IOException {
        String title = "ЖУРНАЛ УЧЕТА СРЕДСТВ ИЗМЕРЕНИЙ " + (facility == null ? "" : " «" + facility.getName() + "» ");

        String body = "<p style=\"text-align: right;\">Утверждаю<br />Директор ЧТПУП &laquo;Ледиком&raquo;<br />_____________ С. Н.\n" +
                "    Шипуло<br />&laquo;____&raquo; ____________ 20___ г</p>\n" +
                "<p style=\"text-align: right;\">&nbsp;</p>\n" +
                "<p style=\"text-align: right;\">&nbsp;</p>\n" +
                "<p style=\"text-align: left;\"><strong>ЖУРНАЛ УЧЕТА СРЕДСТВ ИЗМЕРЕНИЙ В " + (facility == null ? "" : facility.getName()) + " " + (facility == null ? "" : facility.getCategory()) + " категории по адресу: " + (facility == null ? "" : facility.getFullAddress()) + "</strong></p>\n" +
                "<p style=\"text-align: right;\">&nbsp;</p>\n" +
                "<table style=\"border-collapse: collapse; width: 100%;\" border=\"1\">\n" +
                "    <tbody>\n" +
                "        <tr>\n" +
                "            <td style=\"width: 4%;  text-align: center;\"><strong>№<br />пп</strong></td>\n" +
                "            <td style=\"width: 22%;  text-align: center;\"><strong>Наименование,<br />тип</strong></td>\n" +
                "            <td style=\"width: 10%;  text-align: center;\"><strong>Ниж/верх<br />предел<br />измерений<br />температуры</strong></td>\n" +
                "            <td style=\"width: 10%;  text-align: center;\"><strong>Индиви-<br />дуальный номер, №</strong></td>\n" +
                "            <td style=\"width: 12%;  text-align: center;\"><strong>Дата<br />поверки</strong></td>\n" +
                "            <td style=\"width: 8%;  text-align: center;\"><strong>Межповерочный интервал</strong></td>\n" +
                "            <td style=\"width: 12%;  text-align: center;\"><strong>Дата очередной поверки</strong></td>\n" +
                "            <td style=\"width: 22%;  text-align: center;\"><strong>Место установки<br />(местонахождение)</strong></td>\n" +
                "        </tr>";

        for (int i = 0; i < observableList.size(); i++) {
            body += "<tr>\n" +
                    "            <td style=\"width: 4%; text-align: center;\">" + (i + 1) + "</td>\n" +
                    "            <td style=\"width: 21%;\">" + observableList.get(i).getName() + " (" + observableList.get(i).getType() + ")" + "</td>\n" +
                    "            <td style=\"width: 10%;\">&nbsp;" + observableList.get(i).getTemperatureRange() + "</td>\n" +
                    "            <td style=\"width: 10%;\">&nbsp;" + observableList.get(i).getNumber() + "</td>\n" +
                    "            <td style=\"width: 12%;\">&nbsp;" + observableList.get(i).getVerificationDate() + "</td>\n" +
                    "            <td style=\"width: 8%;\">&nbsp;" + observableList.get(i).getVerificationInterval() + "</td>\n" +
                    "            <td style=\"width: 12%;\">&nbsp;" + observableList.get(i).getNextVerificationDate() + "</td>\n" +
                    "            <td style=\"width: 22%;\">" + observableList.get(i).getLocation() + "</td>\n" +
                    "        </tr>";
        }

        body += "</tbody>\n" +
                "</table>\n" +
                "<p>&nbsp;</p>\n" +
                "<p>&nbsp;</p>\n" +
                "<p>Ответственное лицо: бухгалтер ____________ С. В. Копать</p>";

        String htmlString = htmlTemplate.htmlTop + body + htmlTemplate.htmlBottom;
        htmlString = htmlString.replace("$title", title);
        File htmlFile = new File(FileSystemView.getFileSystemView().getDefaultDirectory().getPath() + "/Документы программа Ледиком/" + title + DateFormatter.format(LocalDate.now()) + ".html");
        FileUtils.writeStringToFile(htmlFile, htmlString, StandardCharsets.UTF_8.name());

        FileOpener.openFile(htmlFile);
    }
}
