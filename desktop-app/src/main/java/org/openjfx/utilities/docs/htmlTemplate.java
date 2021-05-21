package org.openjfx.utilities.docs;

interface htmlTemplate {
    String htmlTop = "<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">\n" +
            "<html>\n" +
            "\n" +
            "<head>\n" +
            "    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n" +
            "    <title>$title</title>\n" +
            "</head>\n" +
            "\n" +
            "<body>\n";
    String htmlBottom = "</body>\n" +
            "\n" +
            "</html>\n";
}
