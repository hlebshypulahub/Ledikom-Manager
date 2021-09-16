package org.openjfx.utilities.docs;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public final class FileOpener {

    private FileOpener() {

    }

    public static void openFile(File file) {
        try {
            if (Desktop.isDesktopSupported()) {
                Desktop desktop = Desktop.getDesktop();
                System.out.println(file.toURI());
                desktop.browse(file.toURI());
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}

