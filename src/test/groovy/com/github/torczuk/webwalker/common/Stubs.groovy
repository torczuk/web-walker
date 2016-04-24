package com.github.torczuk.webwalker.common

import com.github.torczuk.webwalker.domain.model.WebPage

import java.util.concurrent.Callable


class Stubs {
    public static Callable<WebPage> GET_WEB_PAGE_TASK = new Callable<WebPage>() {
        @Override
        WebPage call() throws Exception {
            Fakes.EMPTY_WEB_PAGE
        }
    }
}
