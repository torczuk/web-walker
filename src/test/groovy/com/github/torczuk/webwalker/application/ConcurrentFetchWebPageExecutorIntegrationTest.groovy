package com.github.torczuk.webwalker.application

import com.github.torczuk.webwalker.WebWalker
import com.github.torczuk.webwalker.domain.model.WebPage
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.SpringApplicationConfiguration
import spock.lang.Specification

@SpringApplicationConfiguration(classes = WebWalker.class)
class ConcurrentFetchWebPageExecutorIntegrationTest extends Specification {
    @Autowired ConcurrentFetchWebPageExecutor concurrentGetWebPageServiceExecutor

    def 'should submit the page for fetch and next take it '() {
        given:
        String url = 'http://localhost:9090/about/index.html'

        when:
        concurrentGetWebPageServiceExecutor.submit(url)

        then:
        WebPage webPage = concurrentGetWebPageServiceExecutor.take().get()
        webPage.payload().get() == ':)'
        webPage.url() == 'http://localhost:9090/about/index.html'
    }
}
