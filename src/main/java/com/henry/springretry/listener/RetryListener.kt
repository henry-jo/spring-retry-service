package com.henry.springretry.listener

import mu.KLogging
import org.springframework.retry.RetryCallback
import org.springframework.retry.RetryContext
import org.springframework.retry.listener.RetryListenerSupport

class RetryListener: RetryListenerSupport() {

    companion object : KLogging()

    override fun <T : Any?, E : Throwable?> onError(context: RetryContext?, callback: RetryCallback<T, E>?, throwable: Throwable?) {
        logger.info("error retry! retry cnt: ${context?.retryCount} exception: $throwable")
        super.onError(context, callback, throwable)
    }

    override fun <T : Any?, E : Throwable?> close(context: RetryContext?, callback: RetryCallback<T, E>?, throwable: Throwable?) {
        logger.info("retry finish")
    }
}