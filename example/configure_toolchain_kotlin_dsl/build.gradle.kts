import org.jooq.util.jaxb.tools.XMLAppendable

plugins {
    id("nu.studer.jooq") version "10.0"
    id("java")
}

repositories {
    mavenCentral()
}

dependencies {
    jooqGenerator("com.h2database:h2:2.3.232")
}

// allows to omit the apply() function when configuring the jOOQ configuration
operator fun <T: XMLAppendable> T.invoke(block: T.() -> Unit) = this.apply(block)

jooq {
   configurations {
        create("main") {
            jooqConfiguration {
                logging = org.jooq.meta.jaxb.Logging.WARN
                jdbc {
                    driver = "org.h2.Driver"
                    url = "jdbc:h2:~/test;AUTO_SERVER=TRUE"
                    user = "sa"
                    password = ""
                }
                generator {
                    name = "org.jooq.codegen.DefaultGenerator"
                    database {
                        name = "org.jooq.meta.h2.H2Database"
                        includes = ".*"
                        excludes = ""
                    }
µ                    target {
                        packageName = "nu.studer.sample"
                    }
                }
            }
        }
    }
}

// by default, generateJooq will use the configured java toolchain, if any
java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

tasks.named<nu.studer.gradle.jooq.JooqGenerate>("generateJooq") {
    // generateJooq can be configured to use a different/specific toolchain
    (launcher::set)(javaToolchains.launcherFor {
        languageVersion.set(JavaLanguageVersion.of(23))
    })
}
