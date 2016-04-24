package com.github.torczuk.webwalker;

import static spark.Spark.*;

public class SparkLauncher {
    public static void main(String[] args) {
        startLauncher();
    }

    public static void startLauncher() {
        staticFileLocation("/public");
        port(9090);
        get("/about/index.html", (req, res) -> ":)");
        get("/", (req, res) -> "<a href=\"/index.html\">index</a>");

        get("/single.html", (req, res) -> "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head lang=\"en\">\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>index</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "Single page that contains external and internal links <br>\n" +
                "<a href=\"/index.html\">home</a><br>\n" +
                "<a href=\"http://www.google.pl\">google</a><br>\n" +
                "</body>\n" +
                "</html>");
    }

    public static void stopLauncher() {
        stop();
    }
}