package com.github.torczuk.webwalker.adapters.rest;

import com.github.torczuk.webwalker.adapters.common.RequestedWebPage;
import com.github.torczuk.webwalker.application.WebPagesRetrieveService;
import com.github.torczuk.webwalker.application.representation.WebPageRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import static com.github.torczuk.webwalker.adapters.common.RequestValidator.isValid;
import static java.lang.String.format;
import static org.springframework.http.HttpStatus.SEE_OTHER;
import static org.springframework.http.ResponseEntity.*;

@RestController
@RequestMapping("/")
public class WebPageController {
    private static final ExecutorService EXECUTOR = Executors.newFixedThreadPool(10);
    private static final AtomicInteger ID_SEQ = new AtomicInteger();
    private static final Map<Integer, List<WebPageRepresentation>> CACHE = new HashMap<>();

    @Autowired ApplicationContext context;

    @RequestMapping(value = "webPage", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<?> create(@RequestBody RequestedWebPage requestedWebPage) {
        if(!isValid(requestedWebPage)) {
            return badRequest().build();
        }
        int queueId = fetch(requestedWebPage);
        return accepted()
                .header("location", format("/queue/%d", queueId))
                .build();
    }

    @RequestMapping(value = "webPage/{id}", method = RequestMethod.GET)
    ResponseEntity<?> get(@PathVariable Integer id) {
        if (CACHE.containsKey(id)) {
            System.out.println(CACHE.get(id));
            return ResponseEntity.ok().body(CACHE.get(id));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @RequestMapping(value = "queue/{id}", method = RequestMethod.GET)
    ResponseEntity<?> queue(@PathVariable Integer id) {
        if (!CACHE.containsKey(id)) {
            return
                    ok().body("FETCHING...");
        } else {
            return status(SEE_OTHER)
                    .header("location", format("/webPage/%d", id))
                    .build();
        }
    }

    private int fetch(RequestedWebPage requestedWebPage) {
        int id = ID_SEQ.getAndIncrement();
        WebPagesRetrieveService webPagesRetrieveService = context.getBean(WebPagesRetrieveService.class);

        Runnable callback = () -> {
            List<WebPageRepresentation> result = webPagesRetrieveService.retrieve(requestedWebPage.getUrl(), requestedWebPage.getDepth());
            CACHE.put(id, result);
        };
        EXECUTOR.submit(callback);
        return id;
    }
}
