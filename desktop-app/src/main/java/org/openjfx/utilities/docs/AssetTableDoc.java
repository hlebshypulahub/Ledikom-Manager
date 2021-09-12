package org.openjfx.utilities.docs;

import javafx.collections.ObservableList;
import org.apache.commons.io.FileUtils;
import org.openjfx.ledicom.entities.Asset;
import org.openjfx.ledicom.entities.AssetType;
import org.openjfx.ledicom.entities.Facility;
import org.openjfx.utilities.formatters.DateFormatter;

import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;

public final class AssetTableDoc {
    private AssetTableDoc() {

    }

    public static void createTable(ObservableList<Asset> assets, AssetType assetType, Facility facility) throws IOException {
        String title = "Инвентарный список " + (facility == null ? "" : "на " + facility.getName()) + (assetType == null ? "" : " ( " + assetType + ")");

        String body = "<p><strong>ЧТПУП «Ледиком»&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;" +
                "&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;" +
                "&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;</strong></p>\n";

        body += "<p style=\"text-align: center;\"><strong>" + "Инвентарный список объектов основных средств" + (facility == null ? "" : " <br>на «" + facility.getName() + "»") + (assetType == null ? "" : " (инвентарная группа: " + assetType + ")") + "</strong></p><br>";

        body += "<table style=\"border-collapse: collapse; width: 100%;\" border=\"1\">\n" +
                "<tbody>";

        body += "<tr>\n" +
                "<td style=\"width: 5%; text-align: center;\"><strong>№ п/п</strong></td>\n" +
                "<td style=\"width: 10%; text-align: center;\"><strong>Инвентарный номер</strong></td>\n" +
                "<td style=\"width: 26.8466%; text-align: center;\"><strong>Наименование объекта</strong></td>\n" +
                "</tr>";

        for (int i = 0; i < assets.size(); i++) {
            body += "<tr>\n" +
                    "<td style=\"width: 5%; text-align: center;\">" + (i + 1) + "</td>\n" +
                    "<td style=\"width: 10%;\">&nbsp;" + assets.get(i).getNumber() + "</td>\n" +
                    "<td style=\"width: 26.8466%;\">&nbsp;" + assets.get(i).getName() + "</td>\n" +
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
