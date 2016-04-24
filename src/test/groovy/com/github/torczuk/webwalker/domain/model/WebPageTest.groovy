package com.github.torczuk.webwalker.domain.model

import spock.lang.Specification


class WebPageTest extends Specification {

    public static final String PAYLOAD_WITHOUT_LINKS = 'some payload'
    public static final String EMPTY_PAYLOAD = ''
    public static final String PAYLOAD_WITH_LINKS = '''
                                            <a href = "http://google.pl">some payload</a>
                                            <a href="/home.html">home</a>
                                        '''

    def 'should equals for the same urls'() {
        when:
        WebPage first = new WebPage('http://example.com', PAYLOAD_WITHOUT_LINKS)
        WebPage second = new WebPage('http://example.com', EMPTY_PAYLOAD)

        then:
        first == second
    }

    def 'should return empty set when there are not links on the page'() {
        when:
        WebPage first = new WebPage('http://example.com', PAYLOAD_WITHOUT_LINKS)

        then:
        first.urls() as Set == [] as Set
    }

    def 'should return all links on the page'() {
        when:
        WebPage webPage = new WebPage('http://example.com/pages', PAYLOAD_WITH_LINKS)

        then:
        webPage.urls() as Set == ['http://google.pl', 'http://example.com/home.html'] as Set
    }
}
