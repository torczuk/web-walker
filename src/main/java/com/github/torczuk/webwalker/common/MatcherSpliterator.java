package com.github.torczuk.webwalker.common;

import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.regex.Matcher;

public class MatcherSpliterator implements Spliterator<Matcher> {

    private final Matcher matcher;

    private MatcherSpliterator(Matcher matcher) {
        this.matcher = matcher;
    }

    @Override
    public boolean tryAdvance(Consumer<? super Matcher> action) {
        boolean found = matcher.find();
        if (found) {
            action.accept(matcher);
        }
        return found;
    }

    @Override
    public Spliterator<Matcher> trySplit() {
        return null;
    }

    @Override
    public long estimateSize() {
        return 0;
    }

    @Override
    public int characteristics() {
        return NONNULL + ORDERED;
    }

    public static MatcherSpliterator matcherStream(Matcher matcher) {
        return new MatcherSpliterator(matcher);
    }
}