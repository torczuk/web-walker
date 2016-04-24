package com.github.torczuk.webwalker.application;

import com.github.torczuk.webwalker.domain.model.WebPage;
import com.github.torczuk.webwalker.domain.model.WebPagePayloadFetcher;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.Callable;

import static java.util.Optional.ofNullable;

@Service
public class FetchWebPageTaskFactory {
    public Callable<WebPage> create(String url) {
        return () -> {
                String payload = WebPagePayloadFetcher.from(url);
                return new WebPage(url, ofNullable(payload));
        };
    }
}
