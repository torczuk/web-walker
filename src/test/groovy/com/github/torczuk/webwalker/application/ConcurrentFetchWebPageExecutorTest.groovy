package com.github.torczuk.webwalker.application

import com.github.torczuk.webwalker.common.Fakes
import com.github.torczuk.webwalker.common.Stubs
import com.github.torczuk.webwalker.domain.model.WebPage
import spock.lang.Specification

class ConcurrentFetchWebPageExecutorTest extends Specification {

    def 'should return empty WebPage when no url has been submitted' () {
        given:
        FetchWebPageTaskFactory factory = {} as FetchWebPageTaskFactory
        ConcurrentFetchWebPageExecutor executor = new ConcurrentFetchWebPageExecutor(factory)

        when:
        Optional<WebPage> webPage = executor.take()

        then:
        !webPage.isPresent()
    }

    def 'should submit url and next wait for the result' () {
        given:
        FetchWebPageTaskFactory factory = Mock(FetchWebPageTaskFactory)
        factory.create(_ as String) >> Stubs.GET_WEB_PAGE_TASK
        ConcurrentFetchWebPageExecutor executor = new ConcurrentFetchWebPageExecutor(factory)

        when:
        executor.submit(Fakes.EMPTY_WEB_PAGE.url())
        Optional<WebPage> webPage = executor.take()

        then:
        webPage.isPresent()
        webPage.get() == Fakes.EMPTY_WEB_PAGE
    }
}
