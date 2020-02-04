package com.henry.springretry.service

import mu.KLogging
import org.springframework.retry.backoff.ExponentialBackOffPolicy
import org.springframework.retry.policy.SimpleRetryPolicy
import org.springframework.retry.support.RetryTemplate
import org.springframework.stereotype.Service

@Service
class RetryableService {

    companion object : KLogging()

    fun run(
        action: () -> Unit,
        maxAttempts: Int = 3,
        exceptions: List<Class<out Throwable>> = listOf(Exception::class.java),
        maxInterval: Long = 2000L
    ) {
        val retryTemplate = RetryTemplate()

//        val fixedBackOffPolicy = FixedBackOffPolicy() // fixed back off
        val exponentialBackOffPolicy = ExponentialBackOffPolicy().apply {
            this.initialInterval = 300L
            this.maxInterval = maxInterval
            this.multiplier = 2.0
        } // exponential back off
        retryTemplate.setBackOffPolicy(exponentialBackOffPolicy)

        val exceptionMap = exceptions.map { it to true }.toMap()
        val retryPolicy = SimpleRetryPolicy(maxAttempts, exceptionMap)
        retryTemplate.setRetryPolicy(retryPolicy)

        return retryTemplate.execute<Unit, NoSuchElementException> {
            action()
            logger.info("retry!")
        }
    }
}