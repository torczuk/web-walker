package com.github.torczuk.webwalker.adapters.commandline;

import com.github.torczuk.webwalker.adapters.common.RequestedWebPage;
import com.github.torczuk.webwalker.application.WebPagesRetrieveService;
import com.github.torczuk.webwalker.application.representation.WebPageRepresentation;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.List;

import static com.github.torczuk.webwalker.adapters.common.RequestValidator.isValid;


public class WebPageCommandLine {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = new SpringApplicationBuilder(CommandLineConfiguration.class)
                .web(false).run(args);

        if(args.length != 2) {
            throw new IllegalArgumentException("invocation ${url} ${depth}");
        }
        String url = args[0];
        Integer depth = Integer.valueOf(args[1]);

        RequestedWebPage requestedWebPage = new RequestedWebPage(url, depth);
        if(!isValid(requestedWebPage)) {
            throw new IllegalArgumentException("url is not http or depth is negative");
        }

        WebPagesRetrieveService webPagesRetrieveService = context.getBean(WebPagesRetrieveService.class);

        List<WebPageRepresentation> result = webPagesRetrieveService.retrieve(url, depth);

        for (WebPageRepresentation webPageRepresentation : result) {
            System.out.println(webPageRepresentation);
        }

        context.registerShutdownHook();
    }
}
