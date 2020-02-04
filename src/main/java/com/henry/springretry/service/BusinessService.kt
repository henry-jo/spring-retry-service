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
            testMultiple(inputVal)
        }, maxAttempts = 5, exceptions = listOf(RuntimeException::class.java))
    }

    fun retryTest3() {
        retryableService.run(action = {
            logger.info("비즈니스 로직")
            throw RuntimeException()
        }, maxAttempts = 5, exceptions = listOf(NoSuchElementException::class.java, IllegalStateException::class.java))
    }

    fun retryTest4() {
        retryableService.run(action = {
            logger.info("비즈니스 로직")
            throw IllegalStateException()
        }, maxAttempts = 5, exceptions = listOf(NoSuchElementException::class.java, IllegalStateException::class.java))
    }

    private fun testMultiple(inputVal: Int) = inputVal * 2
}