package com.github.torczuk.webwalker.domain.model

import com.github.torczuk.webwalker.common.Fakes
import spock.lang.Specification

class WebPagePayloadFetcherIntegrationTest extends Specification {


    def 'should fetch payload of the page of a given URL'() {
        when:
        String payload = WebPagePayloadFetcher.from('http://127.0.0.1:9090/index.html')

        then:
        payload == Fakes.HTML_PAYLOAD
    }
}
