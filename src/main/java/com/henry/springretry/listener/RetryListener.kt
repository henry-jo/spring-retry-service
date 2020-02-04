package com.henry.springretry.listener

import mu.KLogging
import org.springframework.retry.RetryCallback
import org.springframework.retry.RetryContext
import org.springframework.retry.listener.RetryListenerSupport

class RetryListener: RetryListenerSupport() {

    companion object : KLogging()

    override fun <T : Any?, E : Throwable?> onError(context: RetryContext?, callback: RetryCallback<T, E>?, throwable: Throwable?) {
        if (context?.retryCount == 1) return // onError 리스너이기 때문에 오류가 나고 재시도하기 전에 불림

        logger.info("error retry! retry cnt: ${context?.retryCount?.minus(1)} exception: $throwable")
        super.onError(context, callback, throwable)
    }

    override fun <T : Any?, E : Throwable?> close(context: RetryContext?, callback: RetryCallback<T, E>?, throwable: Throwable?) {
        logger.info("retry finish")
    }
}