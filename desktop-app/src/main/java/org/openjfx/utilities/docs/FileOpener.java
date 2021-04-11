package org.openjfx.utilities.docs;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class FileOpener {
    public static void openFile(File file) {
        try {
            if (Desktop.isDesktopSupported()) {
                Desktop desktop = Desktop.getDesktop();
                desktop.browse(file.toURI());
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}

