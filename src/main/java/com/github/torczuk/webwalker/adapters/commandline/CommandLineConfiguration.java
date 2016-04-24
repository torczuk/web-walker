package com.github.torczuk.webwalker.adapters.commandline;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
@ComponentScan("com.github.torczuk.webwalker.application")
public class CommandLineConfiguration {
}
