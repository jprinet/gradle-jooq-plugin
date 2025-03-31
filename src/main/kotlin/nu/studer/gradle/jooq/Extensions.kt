package nu.studer.gradle.jooq

import org.jooq.meta.jaxb.Configuration

operator fun <T: Configuration> T.invoke(block: T.() -> Unit) = this.apply(block)
