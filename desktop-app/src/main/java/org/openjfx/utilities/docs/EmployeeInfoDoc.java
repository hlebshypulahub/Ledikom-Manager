package org.openjfx.utilities.docs;

import javafx.collections.ObservableList;
import org.apache.commons.io.FileUtils;
import org.openjfx.ledicom.entities.Edu;
import org.openjfx.utilities.Global;
import org.openjfx.utilities.Validator;
import org.openjfx.utilities.database.DatabaseEmployeeController;

import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class EmployeeInfoDoc {
    public static void createPersonalCard() throws IOException {

        String title = "Личная карточка " + Global.getEmployee().getShortName();

        String body = "<p><strong>ЧТПУП «Ледиком»&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;" +
                "&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;" +
                "&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;</strong></p>\n";

        body += fillForm();

        body += "<p>1. С документами по ОТ и ТБ ознакомлен &nbsp; __________&nbsp;" + Global.getEmployee().getShortName() + "&nbsp; &nbsp; дата ___________ г." + "</p>";
        body += "<p>2. С ПВТР ознакомлен &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; __________&nbsp;" + Global.getEmployee().getShortName() + "&nbsp; &nbsp; дата ___________ г." + "</p>";
        body += "<p>3. Инструктаж по соблюдению санитарно-гигиенического режима и правилами работы с ДС пройден <br> __________&nbsp;" + Global.getEmployee().getShortName() + "&nbsp; &nbsp; дата ___________ г." + "</p>";
        body += "<p>4. С СОП ознакомлен &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; __________&nbsp;" + Global.getEmployee().getShortName() + "&nbsp; &nbsp; дата ___________ г." + "</p>";
        body += "<p>5. С документами по ПБ ознакомлен &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; __________&nbsp;" + Global.getEmployee().getShortName() + "&nbsp; &nbsp; дата ___________ г." + "</p>";
        body += "<p>6. Один экземпляр труд. договора получил __________&nbsp;" + Global.getEmployee().getShortName() + "&nbsp; &nbsp; дата ___________ г." + "</p>";
        body += "<p>7. С ДИ ознакомлен &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; __________&nbsp;" + Global.getEmployee().getShortName() + "&nbsp; &nbsp; дата ___________ г." + "</p>";
        body += "<p>8. Труд. книжку получил &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; __________&nbsp;" + Global.getEmployee().getShortName() + "&nbsp; &nbsp; дата ___________ г." + "</p>";


        String htmlString = htmlTemplate.htmlTop + body + htmlTemplate.htmlBottom;

        htmlString = htmlString.replace("$title", title);

        File htmlFile = new File(FileSystemView.getFileSystemView().getDefaultDirectory().getPath() + "/Документы программа Ледиком/" + title + ".html");
        FileUtils.writeStringToFile(htmlFile, htmlString, StandardCharsets.UTF_8.name());

        FileOpener.openFile(htmlFile);
    }

    public static String fillForm() {
        String body = "<p style=\"text-align: center;\"><strong>ЛИЧНАЯ КАРТОЧКА<br /></strong><strong>сотрудника</strong></p><br>";

        body += "<p><strong>1. Личная информация</strong></p>";

        body += "<table style=\"border-collapse: collapse; width: 100%;\" border=\"1\">\n" +
                "<tbody>\n" +
                "<tr>\n" +
                "<td style=\"width: 32.2443%;\">1.1. Фамилия</td>\n" +
                "<td style=\"width: 67.7557%;\">&nbsp; &nbsp; &nbsp; &nbsp;" + Validator.validateString(Global.getEmployee().getLastName()) + "</td>\n" +
                "</tr>\n" +
                "<tr>\n" +
                "<td style=\"width: 32.2443%;\">1.2. Имя</td>\n" +
                "<td style=\"width: 67.7557%;\">&nbsp; &nbsp; &nbsp; &nbsp;" + Validator.validateString(Global.getEmployee().getFirstName()) + "</td>\n" +
                "</tr>\n" +
                "<tr>\n" +
                "<td style=\"width: 32.2443%;\">1.3. Отчество</td>\n" +
                "<td style=\"width: 67.7557%;\">&nbsp; &nbsp; &nbsp; &nbsp;" + Validator.validateString(Global.getEmployee().getPatronymic()) + "</td>\n" +
                "</tr>\n" +
                "<tr>\n" +
                "<td style=\"width: 32.2443%;\">1.4. Дата рождения</td>\n" +
                "<td style=\"width: 67.7557%;\">&nbsp; &nbsp; &nbsp; &nbsp;" + Validator.validateString(Global.getEmployee().getDOB()) + "</td>\n" +
                "</tr>\n" +
                "<tr>\n" +
                "<td style=\"width: 32.2443%;\">1.5. Телефон</td>\n" +
                "<td style=\"width: 67.7557%;\">&nbsp; &nbsp; &nbsp; &nbsp;" + Validator.validateString(Global.getEmployee().getPhone()) + "</td>\n" +
                "</tr>\n" +
                "<tr>\n" +
                "<td style=\"width: 32.2443%;\">1.6. Адрес</td>\n" +
                "<td style=\"width: 67.7557%;\">&nbsp; &nbsp; &nbsp; &nbsp;" + Validator.validateString(Global.getEmployee().getAddress()) + "</td>\n" +
                "</tr>\n" +
                "<tr>\n" +
                "<td style=\"width: 32.2443%;\">1.7. Дети</td>\n" +
                "<td style=\"width: 67.7557%;\">&nbsp; &nbsp; &nbsp; &nbsp;" + Global.getEmployee().getChildrenData() + "</td>\n" +
                "</tr>\n" +
                "</tbody>\n" +
                "</table>";

        body += "<p><strong>2. Трудоустройство</strong></p>";

        body += "<table style=\"border-collapse: collapse; width: 100%;\" border=\"1\">\n" +
                "<tbody>";

        ObservableList<String> employeeContractList = DatabaseEmployeeController.employeeContractList();

        assert employeeContractList != null;
        if (!employeeContractList.isEmpty()) {
            for (int i = 0; i < employeeContractList.size(); i++) {
                body += "<tr>\n" +
                        "<td style=\"width: 32.2443%;\">" + (i == 0 ? "2.1. Контракт" : "") + "</td>\n" +
                        "<td style=\"width: 67.7557%;\">&nbsp; &nbsp; &nbsp; &nbsp;" + employeeContractList.get(i) + "</td>\n" +
                        "</tr>";
            }
        } else {
            System.out.println("!!!");
            body += "<tr>\n" +
                    "<td style=\"width: 32.2443%;\">2.1. Контракт</td>\n" +
                    "<td style=\"width: 67.7557%;\"></td>\n" +
                    "</tr>";
        }

        body += "<tr>\n" +
                "<td style=\"width: 32.2443%;\">2.2. Должность</td>\n" +
                "<td style=\"width: 67.7557%;\">&nbsp; &nbsp; &nbsp; &nbsp;" + Validator.validateString(Global.getEmployee().getPosition()) + "</td>\n" +
                "</tr>\n" +
                "<tr>\n" +
                "<td style=\"width: 32.2443%;\">2.3. Категория</td>\n" +
                "<td style=\"width: 67.7557%;\">&nbsp; &nbsp; &nbsp; &nbsp;" + (Global.getEmployee().getCategory() == null ? "" : Global.getEmployee().getCategory() + " (" + (Global.getEmployee().getCategoryNum().equals("") ? "" : " номер "
                + Global.getEmployee().getCategoryNum() + ",") + " дата получения " + Global.getEmployee().getCategoryAssignmentDate() + " )") + "</td>\n" +
                "</tr>\n" +
                "<tr>\n" +
                "<td style=\"width: 32.2443%;\">2.4. Оклад</td>\n" +
                "<td style=\"width: 67.7557%;\">&nbsp; &nbsp; &nbsp; &nbsp;" + Global.getEmployee().getSalary() + "</td>\n" +
                "</tr>\n" +
                "<tr>\n" +
                "<td style=\"width: 32.2443%;\">2.5. Дата приёма на работу</td>\n" +
                "<td style=\"width: 67.7557%;\">&nbsp; &nbsp; &nbsp; &nbsp;" + Validator.validateString(Global.getEmployee().getHiringDate()) + "</td>\n" +
                "</tr>\n" +
                "<tr>\n" +
                "<td style=\"width: 32.2443%;\">2.6. Дата выдачи СИЗ</td>\n" +
                "<td style=\"width: 67.7557%;\">&nbsp; &nbsp; &nbsp; &nbsp;" + Validator.validateString(Global.getEmployee().getPPE()) + "</td>\n" +
                "</tr>\n" +
                "<tr>\n" +
                "<td style=\"width: 32.2443%;\">2.7. Декретный отпуск</td>\n" +
                "<td style=\"width: 67.7557%;\">&nbsp; &nbsp; &nbsp; &nbsp;" + (Global.getEmployee().getMaternityStartDate() == null
                || Global.getEmployee().getMaternityEndDate() == null ? ""
                : Global.getEmployee().getMaternityStartDate() + " - " + Global.getEmployee().getMaternityEndDate()) + "</td>\n" +
                "</tr>\n" +
                "<tr>\n" +
                "<td style=\"width: 32.2443%;\">2.8. Примечание</td>\n" +
                "<td style=\"width: 67.7557%;\">&nbsp; &nbsp; &nbsp; &nbsp;" + Validator.validateString(Global.getEmployee().getNote()) + "</td>\n" +
                "</tr>";

        body += "</tbody>\n" +
                "</table>";

        Edu edu = DatabaseEmployeeController.getEmployeeEdu();

        body += "<p><strong>3. Образование</strong></p>";

        body += "<table style=\"border-collapse: collapse; width: 100%;\" border=\"1\">\n" +
                "<tbody>\n" +
                "<tr>\n" +
                "<td style=\"width: 32.2443%;\">3.1. Учреждение образования</td>\n" +
                "<td style=\"width: 67.7557%;\">&nbsp; &nbsp; &nbsp; &nbsp;" + (edu == null ? "" : Validator.validateString(edu.getName())) + "</td>\n" +
                "</tr>\n" +
                "<tr>\n" +
                "<td style=\"width: 32.2443%;\">3.2. Дата выпуска</td>\n" +
                "<td style=\"width: 67.7557%;\">&nbsp; &nbsp; &nbsp; &nbsp;" + (edu == null ? "" : Validator.validateString(edu.getGraduationDate())) + "</td>\n" +
                "</tr>\n" +
                "</tbody>\n" +
                "</table><br>";

        return body;
    }
}