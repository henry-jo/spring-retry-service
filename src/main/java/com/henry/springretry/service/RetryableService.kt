package com.henry.springretry.service

import mu.KLogging
import org.springframework.retry.backoff.FixedBackOffPolicy
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
        backOff: Long = 2000L
    ) {
        val retryTemplate = RetryTemplate()

        val fixedBackOffPolicy = FixedBackOffPolicy()
        fixedBackOffPolicy.backOffPeriod = backOff
        retryTemplate.setBackOffPolicy(fixedBackOffPolicy)

        val exceptionMap = exceptions.map { it to true }.toMap()
        val retryPolicy = SimpleRetryPolicy(maxAttempts, exceptionMap)
        retryTemplate.setRetryPolicy(retryPolicy)

        return retryTemplate.execute<Unit, NoSuchElementException> {
            action()
            logger.info("retry!")
        }
    }
}