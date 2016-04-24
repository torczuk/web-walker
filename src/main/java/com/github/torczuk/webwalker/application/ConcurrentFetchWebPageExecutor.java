package com.github.torczuk.webwalker.application;

import com.github.torczuk.webwalker.domain.model.GetWebPageServiceExecutor;
import com.github.torczuk.webwalker.domain.model.WebPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.*;

import static java.util.Optional.empty;

@Service
@Scope("prototype")
public class ConcurrentFetchWebPageExecutor implements GetWebPageServiceExecutor {

    private static final Logger logger = LoggerFactory.getLogger(ConcurrentFetchWebPageExecutor.class);

    public static final int N_THREADS = 5;
    public static final int POLL_TASK_TIMEOUT_IN_SECONDS = 5;

    private final FetchWebPageTaskFactory factory;
    private final CompletionService<WebPage> completionService;

    @Autowired
    public ConcurrentFetchWebPageExecutor(FetchWebPageTaskFactory factory) {
        this.factory = factory;
        ExecutorService executorService = Executors.newFixedThreadPool(N_THREADS);
        completionService = new ExecutorCompletionService<>(executorService);
    }

    @Override
    public void submit(String url) {
        logger.info("Submitting {} to fetch", url);
        completionService.submit(factory.create(url));
    }

    @Override
    public Optional<WebPage> take() {
        try {
            Future<WebPage> future = completionService.poll(POLL_TASK_TIMEOUT_IN_SECONDS, TimeUnit.SECONDS);
            return future == null ? empty(): Optional.of(future.get());
        } catch (InterruptedException | ExecutionException e) {
            logger.error("Exception when pulling the task", e);
            return empty();
        }
    }
}
