package com.erstedigital.meetingappbackend;

import org.junit.BeforeClass;
import org.junit.jupiter.api.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.springframework.boot.test.context.SpringBootTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
})
@SpringBootTest
class MeetingAppBackendApplicationTests {

    @BeforeClass
    public static void setUp()
    {
        System.out.println("Runs before all tests in the annotation above.");
    }


    @Test
    void contextLoads() {
    }

}
