package com.henry.springretry.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.retry.backoff.FixedBackOffPolicy
import org.springframework.retry.policy.SimpleRetryPolicy
import org.springframework.retry.support.RetryTemplate
import java.util.Collections

@Configuration
class RetryConfig {

    /**
     * RetryTemplate 빈 등록
     * 아래의 Bean을 주입받아 사용할 수도 있지만, 해당 예제에서는 딱히 주입받을 필요가 없어 사용하지 않음
     */
    @Bean
    fun retryTemplate(): RetryTemplate {
        val retryTemplate = RetryTemplate()

        val fixedBackOffPolicy = FixedBackOffPolicy()
        fixedBackOffPolicy.backOffPeriod = 2000L // default setting
        retryTemplate.setBackOffPolicy(fixedBackOffPolicy)

        val maxAttempts = 3 // default setting
        val retryPolicy = SimpleRetryPolicy(maxAttempts, Collections.singletonMap(Exception::class.java, true))

        retryTemplate.setRetryPolicy(retryPolicy)

        return retryTemplate
    }
}