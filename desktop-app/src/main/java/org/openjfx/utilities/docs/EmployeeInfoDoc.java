package org.openjfx.utilities.docs;

import org.apache.commons.io.FileUtils;
import org.openjfx.utilities.Global;

import javax.print.PrintException;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class EmployeeInfoDoc {
    public static void createDocument() throws IOException, PrintException {

        String title = "Карточка " + Global.getEmployee().getShortName();

        String body = "    <p><strong>План мероприятий по устранению выявленных в ходе самоинспекции несоответствий</strong></p>\n" +
                "    <p><strong>&nbsp;</strong></p>\n" +
                "    <p>План поэтапного устранения выявленных несоответствий по результатам самоинспекции</p>\n" +
                "    <p>аптеки № __________, расположенной по адресу: _________________________________,</p>\n" +
                "    <p>от ____________________ г.</p>\n" +
                "    <p>&nbsp;</p>\n" +
                "    <table style=\"border-collapse: collapse; width: 1000px; height: 28px;\" border=\"1\">\n" +
                "        <tbody>\n" +
                "            <tr style=\"height: 10px;\">\n" +
                "                <td style=\"width: 16.6667%; height: 10px;\">№ <br />п/п</td>\n" +
                "                <td style=\"width: 16.6667%; height: 10px;\">Выявленные&nbsp; нарушения</td>\n" +
                "                <td style=\"width: 16.6667%; height: 10px;\">" + Global.getEmployee().getShortName() + "</td>\n" +
                "                <td style=\"width: 16.6667%; height: 10px;\">Ответственные лица</td>\n" +
                "                <td style=\"width: 16.6667%; height: 10px;\">Срок исполнения</td>\n" +
                "                <td style=\"width: 2.05339%; height: 10px;\">Отметки о выполнении</td>\n" +
                "            </tr>\n" +
                "            <tr style=\"height: 18px;\">\n" +
                "                <td style=\"width: 16.6667%; height: 18px;\">&nbsp;</td>\n" +
                "                <td style=\"width: 16.6667%; height: 18px;\">&nbsp;</td>\n" +
                "                <td style=\"width: 16.6667%; height: 18px;\">&nbsp;</td>\n" +
                "                <td style=\"width: 16.6667%; height: 18px;\">&nbsp;</td>\n" +
                "                <td style=\"width: 16.6667%; height: 18px;\">&nbsp;</td>\n" +
                "                <td style=\"width: 2.05339%; height: 18px;\">&nbsp;</td>\n" +
                "            </tr>\n" +
                "        </tbody>\n" +
                "    </table>";

        String htmlString = htmlTemplate.htmlTop + body + htmlTemplate.htmlBottom;

        htmlString = htmlString.replace("$title", title);

        File htmlFile = new File(System.getProperty("user.home") + "/Desktop/LedicomDocs/" + title + ".html");
        FileUtils.writeStringToFile(htmlFile, htmlString, StandardCharsets.UTF_8.name());

        FileOpener.openFile(htmlFile);
    }
}