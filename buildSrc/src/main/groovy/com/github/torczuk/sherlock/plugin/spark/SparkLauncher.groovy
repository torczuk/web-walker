package com.github.torczuk.sherlock.plugin.spark

import org.gradle.api.Plugin
import org.gradle.api.Project

import static spark.Spark.get
import static spark.Spark.staticFileLocation
import static spark.Spark.port
import static spark.Spark.stop

class SparkLauncher implements Plugin<Project> {
    void apply(Project project) {
        project.extensions.create('sparkConfig', SparkConfig)

        project.task('startSpark') << {
            port(9090);
            staticFileLocation(project.sparkConfig.publicFiles);
            project.sparkConfig.mapping.forEach { key, value ->
                get(key, { req, res -> value })
            }
            println 'Spark has been started ...'
        }

        project.task('stopSpark') << {
            stop();
            println 'Spark has been stopped ...'
        }
    }
}
