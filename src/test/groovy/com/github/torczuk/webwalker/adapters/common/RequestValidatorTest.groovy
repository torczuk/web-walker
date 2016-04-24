package com.github.torczuk.webwalker.adapters.common

import spock.lang.Specification

import static com.github.torczuk.webwalker.adapters.common.RequestValidator.isValid


class RequestValidatorTest extends Specification {

    def 'should not be valid when url is under https protocol'() {
        when:
        RequestedWebPage request = new RequestedWebPage("https://example.com", 10)

        then:
        isValid(request) == false
    }

    def 'should not be valid when for url is empty'() {
        when:
        RequestedWebPage request = new RequestedWebPage("", 10)

        then:
        isValid(request) == false
    }

    def 'should not be valid when depth is less than zero'() {
        when:
        RequestedWebPage request = new RequestedWebPage("http://example.com", -1)

        then:
        isValid(request) == false
    }
}
