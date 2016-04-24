package com.github.torczuk.webwalker.domain.model;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.*;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.github.torczuk.webwalker.common.MatcherSpliterator.matcherStream;
import static java.util.stream.Collectors.toList;
import static java.util.stream.StreamSupport.stream;

public class WebPage {
    public static final String HTTP = "http";
    private static final String HREF_PATTERN = "<a(\\s)*href(\\s)*=(\\s)*\"([^\"]*)\"";
    private static final Pattern pattern = Pattern.compile(HREF_PATTERN);

    private String url;
    private Optional<String> payload;

    public WebPage(String url, Optional<String> payload) {
        this.url = url;
        this.payload = payload;
    }

    public WebPage(String url, String payload) {
        this(url, Optional.of(payload));
    }

    public List<String> urls() {
        Set<String> links = payload
                .map(text -> extractUrlsFrom(text))
                .orElse(new HashSet<>());

        List<String> external = links
                .stream()
                .filter(externalLink())
                .collect(toList());

        List<String> internal = links
                .stream()
                .filter(internalLink())
                .map(link -> evaluate(url, link))
                .filter(link -> link.isPresent())
                .map(result -> result.get())
                .collect(toList());

        return unique(external, internal);
    }

    public Optional<String> payload() {
        return payload;
    }

    public String url() {
        return url;
    }

    private static Predicate<String> externalLink() {
        return link -> link.startsWith(HTTP);
    }

    private static Predicate<String> internalLink() {
        return externalLink().negate();
    }

    private static Optional<String> evaluate(String url, String link) {
        try {
            return Optional.of(new URL(new URL(url), link).toString());
        } catch (MalformedURLException e) {
            return Optional.empty();
        }
    }

    private static List<String> unique(List<String> first, List<String> second) {
        Set<String> result = new TreeSet<>();
        result.addAll(first);
        result.addAll(second);
        return new ArrayList<>(result);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WebPage that = (WebPage) o;
        return Objects.equals(url, that.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(url);
    }

    private Set<String> extractUrlsFrom(String text) {
        Stream<Matcher> stream = stream(matcherStream(pattern.matcher(text)), false);
        return stream.filter((matcher) -> matcher.groupCount() == 4)
                .map((matcher) -> matcher.group(4))
                .map(href -> URI.create(url).resolve(href).toString())
                .collect(Collectors.toSet());
    }
}
