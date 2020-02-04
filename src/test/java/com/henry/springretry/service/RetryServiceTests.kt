package com.henry.springretry.service

import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner::class)
class RetryServiceTests {

    @Autowired
    private lateinit var businessService: BusinessService

    @Test(expected = RuntimeException::class)
    fun retryTest() {
        businessService.retryTest()
    }

    @Test
    fun retryTest2() {
        assertEquals(4, businessService.retryTest2(2))
    }

    // 재시도하지 않음
    @Test(expected = RuntimeException::class)
    fun retryTest3() {
        assertEquals(4, businessService.retryTest3())
    }

    @Test(expected = IllegalStateException::class)
    fun retryTest4() {
        assertEquals(4, businessService.retryTest4())
    }
}