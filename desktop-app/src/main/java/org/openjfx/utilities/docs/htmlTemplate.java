package org.openjfx.utilities.docs;

public class htmlTemplate {
    public static final String htmlTop = "<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">\n" +
            "<style type=\"text/css\" media=\"print\">\n" +
            "    @page {\n" +
            "        size: auto;\n" +
            "        /* auto is the initial value */\n" +
            "        margin-bottom: 20px;\n" +
            "        margin-top: 20px;\n" +
            "        /* this affects the margin in the printer settings */\n" +
            "    }\n" +
            "</style>\n" +
            "<html>\n" +
            "\n" +
            "<head>\n" +
            "    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n" +
            "    <title>$title</title>\n" +
            "</head>\n" +
            "\n" +
            "<body>\n";
    public static final String htmlBottom = "</body>\n" +
            "\n" +
            "</html>\n";
}
