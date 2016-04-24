package com.github.torczuk.webwalker.application;

import com.github.torczuk.webwalker.application.representation.WebPageRepresentation;
import com.github.torczuk.webwalker.application.representation.WebPageRepresentationFactory;
import com.github.torczuk.webwalker.domain.model.GetWebPageServiceExecutor;
import com.github.torczuk.webwalker.domain.model.WebPage;
import com.github.torczuk.webwalker.domain.model.WebWalkerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.*;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

@Service
@Scope("prototype")
public class WebPagesRetrieveService {

    private final GetWebPageServiceExecutor executor;

    @Autowired
    public WebPagesRetrieveService(GetWebPageServiceExecutor executor) {
        this.executor = executor;
    }

    public List<WebPageRepresentation> retrieve(String startingUrl, int depth) {
        Map<String, WebPage> visitedPages = new HashMap<>();

        executor.submit(startingUrl);
        Optional<WebPage> startingPage = executor.take();

        if(!startingPage.isPresent()) {
            throw new WebWalkerException("Can not fetch staring web page");
        }

        visitedPages.put(startingUrl, startingPage.get());

        Set<String> pagesToBeVisited = yetNotVisitedPages(visitedPages, startingPage.get());

        depth--;
        while (depth >= 0 && !pagesToBeVisited.isEmpty()) {
            pagesToBeVisited.stream().forEach(url -> executor.submit(url));
            pagesToBeVisited = new HashSet<>();

            Optional<WebPage> optionalWebPage;
            while ((optionalWebPage = executor.take()).isPresent()) {
                WebPage webPage = optionalWebPage.get();

                pagesToBeVisited.addAll(yetNotVisitedPages(visitedPages, webPage));
                visitedPages.put(webPage.url(), webPage);
            }
            depth--;
        }

        return visitedPages.values().stream()
                .map(webPage -> WebPageRepresentationFactory.create(webPage))
                .collect(toList());
    }

    private Set<String> yetNotVisitedPages(Map<String, WebPage> visitedPages, WebPage webPage) {
        return webPage.urls().stream()
                .filter(url -> !visitedPages.containsKey(url))
                .collect(toSet());
    }
}
