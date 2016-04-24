package com.github.torczuk.webwalker.application

import com.github.torczuk.webwalker.WebWalker
import com.github.torczuk.webwalker.application.representation.WebPageRepresentation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.SpringApplicationConfiguration
import spock.lang.Specification

@SpringApplicationConfiguration(classes = WebWalker.class)
class WebPagesRetrieveServiceIntegrationTest extends Specification {
    @Autowired
    WebPagesRetrieveService service;

    def 'should collect all pages that starting from index page at depth 1'() {
        given:
        String startingPage = 'http://127.0.0.1:9090/index.html'
        int depth = 1;

        when:
        List<WebPageRepresentation> webPageRepresentations = service.retrieve(startingPage, depth)

        then:
        webPageRepresentations.collect { it.url } as Set == [
                'http://127.0.0.1:9090/index.html',
                'http://127.0.0.1:9090/about.html',
                'http://127.0.0.1:9090/offer.html'
        ] as Set
    }
}
