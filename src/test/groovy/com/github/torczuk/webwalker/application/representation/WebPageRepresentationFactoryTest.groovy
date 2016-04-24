package com.github.torczuk.webwalker.application.representation

import com.github.torczuk.webwalker.domain.model.WebPage
import spock.lang.Specification

import static com.github.torczuk.webwalker.common.Fakes.WEB_PAGE

class WebPageRepresentationFactoryTest extends Specification {

    def 'should create WebPageRepresentation'() {
        given:
        WebPage webPage = WEB_PAGE;

        when:
        WebPageRepresentation representation = WebPageRepresentationFactory.create(webPage);

        then:
        representation.url == webPage.url()
        representation.size == webPage.payload().get().getBytes("UTF-8").size()
        representation.hash == "fb720cf47f61fe6dd9dadb6585af95ef"
        representation.pages as Set == [
                'http://www.example.com/index.html',
                'http://www.example.com/offer.html',
                'http://www.example.com/about.html'
        ] as Set
    }
}
