package com.github.torczuk.webwalker.application.representation;

import com.github.torczuk.webwalker.domain.model.WebPage;

import java.io.UnsupportedEncodingException;
import java.util.Optional;

import static org.apache.commons.codec.digest.DigestUtils.md5Hex;

public class WebPageRepresentationFactory {

    public static WebPageRepresentation create(WebPage webPage) {
        Optional<String> payload = webPage.payload();
        return new WebPageRepresentation(webPage.url(), md5(payload), size(payload), webPage.urls());
    }

    private static int size(Optional<String> payload) {
        return payload.map(text -> {
            try {
                return text.getBytes("UTF-8").length;
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
        }).orElse(0);
    }

    private static String md5(Optional<String> payload) {
        return payload.map(text -> md5Hex(text)).orElse("");
    }

}
