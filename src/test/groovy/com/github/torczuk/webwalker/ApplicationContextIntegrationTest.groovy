package com.github.torczuk.webwalker

import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.AnnotationConfigApplicationContext
import spock.lang.Specification

class ApplicationContextIntegrationTest extends Specification {

    def 'should build application context'() {
        given:
        ApplicationContext context = new AnnotationConfigApplicationContext(WebWalker.class);

        expect:
        context != null
    }
}
