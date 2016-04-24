package com.github.torczuk.webwalker.domain.model;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class WebPagePayloadFetcher {
    private static final Logger logger = LoggerFactory.getLogger(WebPagePayloadFetcher.class);

    public static String from(String url) throws IOException {
        logger.info("Fetching {}", url);
        InputStream in = null;
        try {
            in = new URL(url).openStream();
            String payload = IOUtils.toString(in);
            return payload;
        } catch (Throwable e) {
            logger.warn("Exception when fetching {}", url, e);
            return null;
        } finally {
            IOUtils.closeQuietly(in);
        }
    }
}
