package com.github.torczuk.webwalker

import com.jayway.restassured.response.Response
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.boot.test.SpringApplicationConfiguration
import org.springframework.boot.test.WebIntegrationTest
import org.springframework.http.HttpStatus
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner

import static com.jayway.awaitility.Awaitility.await
import static com.jayway.restassured.RestAssured.*
import static java.lang.String.format
import static java.util.concurrent.TimeUnit.SECONDS
import static org.assertj.core.api.Assertions.assertThat
import static org.hamcrest.Matchers.equalTo
import static org.hamcrest.Matchers.hasSize
import static org.springframework.http.HttpStatus.ACCEPTED
import static org.springframework.http.HttpStatus.SEE_OTHER

@RunWith(SpringJUnit4ClassRunner)
@SpringApplicationConfiguration(classes = WebWalker.class)
@WebIntegrationTest
class WebPageSystemTest {

    @Test
    void 'should return Acceptd HTTP code with PENDING status and link to fetched WebPage'() {
        Response extract =
                given().
                        contentType("application/json").
                        when().
                        body('''
                            {"url": "http://localhost:9090/single.html", "depth": "0" }
                        ''').
                        post("http://localhost:8080/webPage")

        String queueLocation = extract.header("location")
        assertThat(queueLocation).isNotEmpty()
        assertThat(extract.statusCode).isEqualTo(ACCEPTED.value())

        waitForStatusUnder(queueLocation, SEE_OTHER)
        get(urlOf(queueLocation)).
                then().
                    assertThat().body("[0].url", equalTo('http://localhost:9090/single.html')).
                and().
                    assertThat().body("[0].hash", equalTo('500b822b32483b37c0e4c2f49a0e88fc')).
                and().
                    assertThat().body("[0].size", equalTo(254)).
                and().
                    assertThat().body("[0].pages[1]", equalTo('http://localhost:9090/simple.html')).
                and().
                    assertThat().body("[0].pages[0]", equalTo('http://localhost:9090/index.html')).
                and().
                    assertThat().body("[0].pages", hasSize(2))


    }

    static private String urlOf(String location) {
        format("http://localhost:8080%s", location)
    }

    static private waitForStatusUnder(String fetchingWebPageLocation, HttpStatus httpStatus) {
        await().atMost(10, SECONDS).until {
            when().get(format("http://localhost:8080%s", fetchingWebPageLocation)).statusCode == httpStatus.value()
        }
    }
}
