package com.github.torczuk.webwalker.domain.model;

import java.util.Optional;

public interface GetWebPageServiceExecutor {
    void submit(String url);
    Optional<WebPage> take();
}
