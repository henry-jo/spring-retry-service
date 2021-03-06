package com.henry.springretry.service

import com.henry.springretry.listener.RetryListener
import mu.KLogging
import org.springframework.retry.RecoveryCallback
import org.springframework.retry.RetryCallback
import org.springframework.retry.backoff.ExponentialBackOffPolicy
import org.springframework.retry.policy.SimpleRetryPolicy
import org.springframework.retry.support.RetryTemplate
import org.springframework.stereotype.Service

@Service
class RetryableService {

    companion object : KLogging()

    @Throws(Exception::class)
    fun <T> run(
        action: () -> T,
        maxAttempts: Int = 3,
        exceptions: List<Class<out Throwable>> = listOf(Exception::class.java),
        maxInterval: Long = 2000L
    ): T {
        val retryTemplate = RetryTemplate()
        retryTemplate.registerListener(RetryListener())

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

        return retryTemplate.execute<T, Throwable> {
            action()
        }

        /** ADD RECOVERY Version
        return retryTemplate.execute(RetryCallback<T, Throwable> {
            action().also {
                logger.info("retry!")
            }
        }, RecoveryCallback<T>{
            action().also {
                logger.info("recovery!")
            }
        })
        */
    }
}