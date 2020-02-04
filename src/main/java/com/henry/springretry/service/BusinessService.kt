package com.henry.springretry.service

import mu.KLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class BusinessService {

    companion object : KLogging()

    @Autowired
    private lateinit var retryableService: RetryableService

    fun retryTest() {
        retryableService.run(action = {
            logger.info("비즈니스 로직")
            throw RuntimeException()
        }, maxAttempts = 5, exceptions = listOf(RuntimeException::class.java))
    }

    fun retryTest2(inputVal: Int): Int {
        return retryableService.run(action = {
            logger.info("비즈니스 로직")
            testMutltiple(inputVal)
        }, maxAttempts = 5, exceptions = listOf(RuntimeException::class.java))
    }

    private fun testMutltiple(inputVal: Int) = inputVal * 2
}