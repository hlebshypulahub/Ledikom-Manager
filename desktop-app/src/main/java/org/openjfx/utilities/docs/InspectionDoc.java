package org.openjfx.utilities.docs;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.apache.commons.io.FileUtils;
import org.openjfx.ledicom.entities.inspection.Checkup;
import org.openjfx.ledicom.entities.inspection.CheckupType;
import org.openjfx.ledicom.entities.inspection.Inspection;
import org.openjfx.utilities.Global;
import org.openjfx.utilities.database.DatabaseInspectionController;

import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.stream.Collectors;

public class InspectionDoc {
    public static void createDocument(Inspection inspection) throws IOException {
        String title = "Самоинспекция " + Global.getFacility().getName() + " " + inspection.getDate();

        String body = "";

        body += "<p style=\"text-align: center;\">ОТЧЕТ О САМОИНСПЕКЦИИ</p>\n" +
                "<p>&nbsp;</p>\n" +
                "<p>Результаты самоинспекции «" + Global.getFacility().getName() + "» </p>\n" +
                "<p>по адресу: " + Global.getFacility().getFullAddress() + " </p>\n" +
                "<p>Дата проведения: " + inspection.getDate() + " </p>\n";

        body += "<p>&nbsp;</p>\n" +
                "<table style=\"height: 56px; border-collapse: collapse; width: 0%;\" border=\"1\" width=\"100%\">\n" +
                "    <tbody>\n" +
                "        <tr style=\"height: 10px;\">\n" +
                "            <td style=\"width: 7.5188%; height: 22px; text-align: center;\" rowspan=\"2\">№</td>\n" +
                "            <td style=\"height: 22px; width: 55.5398%; text-align: center;\" rowspan=\"2\">Проверяемый параметр</td>\n" +
                "            <td style=\"width: 22.406%; height: 12px; text-align: center;\" colspan=\"2\">Соответствие параметра</td>\n" +
                "            <td style=\"width: 16.0065%; height: 22px; text-align: center;\" rowspan=\"2\">Примечание</td>\n" +
                "        </tr>\n" +
                "<tr style=\"height: 10px;\">\n" +
                "            <td style=\"width: 11.4952%; height: 10px; text-align: center;\">\n" +
                "                <p><strong>да</strong></p>\n" +
                "            </td>\n" +
                "            <td style=\"width: 10.9108%; height: 10px; text-align: center;\">\n" +
                "                <p><strong>нет</strong></p>\n" +
                "            </td>\n" +
                "        </tr>\n";

        ObservableList<CheckupType> checkupTypeList = DatabaseInspectionController.CheckupTypes();

        for (int i = 0; i < Objects.requireNonNull(checkupTypeList).size(); i++) {
            int finalI = i;
            ObservableList<Checkup> checkupTempList = inspection.getCheckupList().stream().filter(checkup -> checkup.getQuestion().getCheckupType().getId() == checkupTypeList.get(finalI).getId())
                                                                .collect(Collectors.toCollection(FXCollections::observableArrayList));

            body += "<tr style=\"height: 46px;\">\n" +
                    "    <td style=\"width: 101.471%; height: 24px;\" colspan=\"5\"><strong>" + (i + 1) + ". " + checkupTypeList.get(i).getTypeName() + "</strong></td>\n" +
                    "</tr>";

            for (int j = 0; j < checkupTempList.size(); j++) {
                checkupTempList.get(j).getQuestion().setQuestion(checkupTempList.get(j).getQuestion().getQuestion().replace("\n", "<br>"));
                body += "<tr style=\"height: 48px;\">\n" +
                        "<td style=\"width: 7.5188%; height: 10px; text-align: center;\">" + (i + 1) + "." + (j + 1) + "." + "</td>\n" +
                        "<td style=\"width: 55.5398%; height: 10px;\">" + checkupTempList.get(j).getQuestion().getQuestion() + "</td>\n";

                if (checkupTempList.get(j).getAnswer().equals("Да")) {
                    body += "<td style=\"width: 11.4952%; height: 10px; text-align: center;\">\n" +
                            "Да" +
                            "</td>\n" +
                            "<td style=\"width: 10.9108%; height: 10px; text-align: center;\">\n" +
                            "<p>&nbsp;</p>\n" +
                            "</td>\n";
                } else {
                    body += "<td style=\"width: 11.4952%; height: 10px; text-align: center;\">\n" +
                            "<p>&nbsp;</p>\n" +
                            "</td>\n" +
                            "<td style=\"width: 10.9108%; height: 10px; text-align: center;\">\n" +
                            "Нет" +
                            "</td>\n";
                }

                body += "<td style=\"width: 16.0065%; height: 10px;\">\n" +
                        checkupTempList.get(j).getNote() +
                        "</td>\n" +
                        "</tr>";
            }
        }

        body += "</tbody>\n" +
                "</table>";

        body += "<p>Члены комиссии:&nbsp;&nbsp;&nbsp;&nbsp; ___________________________________</p>\n" +
                "<p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;___________________________________&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</p>\n" +
                "<p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;___________________________________&nbsp;&nbsp;</p>\n" +
                "<p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;___________________________________</p>\n" +
                "<p>&nbsp;</p>\n" +
                "<p>&nbsp;</p>\n" +
                "<p>С результатами самоинспекции ознакомлен</p>\n" +
                "<p>Директор ЧТПУП &laquo;Ледиком&raquo;</p>\n" +
                "<p>_____________ Шипуло С.Н.</p>";


        String htmlString = htmlTemplate.htmlTop + body + htmlTemplate.htmlBottom;
        htmlString = htmlString.replace("$title", title);
        File htmlFile = new File(System.getProperty("user.home") + "/Desktop/LedicomDocs/" + title + ".html");
        FileUtils.writeStringToFile(htmlFile, htmlString, StandardCharsets.UTF_8.name());
        FileOpener.openFile(htmlFile);

        /////////////////////// Violations
        ObservableList<Checkup> violationList = inspection.getCheckupList().stream().filter(checkup ->
                checkup.getViolation() != null).collect(Collectors.toCollection(FXCollections::observableArrayList));

        if (!violationList.isEmpty()) {

            title = "Самоинспекция " + Global.getFacility().getName() + " " + inspection.getDate() + " Нарушения";

            body = "<p style=\"text-align: center;\">План поэтапного устранения выявленных несоответствий по результатам самоинспекции \""
                    + Global.getFacility().getName() + "\" , расположенной по адресу: " + Global.getFacility().getFullAddress()
                    + ",<br>от " + inspection.getDate() + " г.</p>";

            body += "<table style=\"border-collapse: collapse; width: 100%;\" border=\"1\">\n" +
                    "<tbody>";

            body += "<tr>\n" +
                    "<td style=\"width: 16.6667%; text-align: center;\">№<br />п/п</td>\n" +
                    "<td style=\"width: 16.6667%; text-align: center;\">Выявленные<br />нарушения</td>\n" +
                    "<td style=\"width: 16.6667%; text-align: center;\">План действий</td>\n" +
                    "<td style=\"width: 16.6667%; text-align: center;\">Ответственные<br />лица</td>\n" +
                    "<td style=\"width: 16.6667%; text-align: center;\">Срок<br />исполнения</td>\n" +
                    "<td style=\"width: 16.6667%; text-align: center;\">Отметки о<br />выполнении</td>\n" +
                    "</tr>";

            for (int i = 0; i < violationList.size(); i++) {
                body += "<tr>\n" +
                        "<td style=\"width: 16.6667%; text-align: center;\">" + (i + 1) + "." + "</td>\n" +
                        "<td style=\"width: 16.6667%; text-align: center;\">" + violationList.get(i).getViolation().getDescription() + "</td>\n" +
                        "<td style=\"width: 16.6667%; text-align: center;\">" + violationList.get(i).getViolation().getActionPlan() + "</td>\n" +
                        "<td style=\"width: 16.6667%; text-align: center;\">" + violationList.get(i).getViolation().getEmployeeName() + "</td>\n" +
                        "<td style=\"width: 16.6667%; text-align: center;\">" + (violationList.get(i).getViolation().getCorrectionTerm() == null ?
                        "" : violationList.get(i).getViolation().getCorrectionTerm()) + "</td>\n" +
                        "<td style=\"width: 16.6667%; text-align: center;\"></td>\n" +
                        "</tr>";
            }

            body += "</tbody>\n" +
                    "</table>";


            htmlString = htmlTemplate.htmlTop + body + htmlTemplate.htmlBottom;
            htmlString = htmlString.replace("$title", title);
            htmlFile = new File(FileSystemView.getFileSystemView().getDefaultDirectory().getPath() + "/Документы программа Ледиком/" + title + ".html");
            FileUtils.writeStringToFile(htmlFile, htmlString, StandardCharsets.UTF_8.name());
            FileOpener.openFile(htmlFile);
        }
    }
}






























