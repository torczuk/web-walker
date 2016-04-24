package com.github.torczuk.webwalker.adapters.common;

import org.springframework.util.StringUtils;

public class RequestValidator {

    public static boolean isValid(RequestedWebPage requestedWebPage) {
        if(requestedWebPage.getUrl().startsWith("https")) {
            return false;
        }
        if(StringUtils.isEmpty(requestedWebPage.getUrl())) {
            return false;
        }
        if(requestedWebPage.getDepth() < 0) {
            return false;
        }
        return true;
    }
}
