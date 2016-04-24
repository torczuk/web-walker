package com.github.torczuk.webwalker.common

import com.github.torczuk.webwalker.domain.model.WebPage


class Fakes {
    public static WebPage EMPTY_WEB_PAGE = new WebPage('http://www.example.com', 'empty')
    public static WebPage WEB_PAGE = new WebPage('http://www.example.com', HTML_PAYLOAD)

    public static final HTML_PAYLOAD =
"""<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <title>index</title>
</head>
<body>
Hello on my page. You can go the the locations <br>
<a href="/index.html">home</a><br>
<a href="/offer.html">offer</a><br>
<a href="/about.html">about</a><br>
</body>
</html>"""
}
