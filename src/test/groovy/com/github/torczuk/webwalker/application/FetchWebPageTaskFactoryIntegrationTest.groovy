package com.github.torczuk.webwalker.application

import com.github.torczuk.webwalker.WebWalker
import com.github.torczuk.webwalker.domain.model.WebPage
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.SpringApplicationConfiguration
import spock.lang.Specification

import java.util.concurrent.Callable

@SpringApplicationConfiguration(classes = WebWalker.class)
class FetchWebPageTaskFactoryIntegrationTest extends Specification {
    @Autowired FetchWebPageTaskFactory factory

    def 'should fetch web page in task' () {
        Callable<WebPage> callable = factory.create('http://localhost:9090/about/index.html')
    }

}
