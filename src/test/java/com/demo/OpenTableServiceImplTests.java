package com.demo;

import com.demo.service.OpenTableService;
import com.demo.service.impl.OpenTableServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

public class OpenTableServiceImplTests {

    @Test
    public void testIsOpenWhenBetweenIntervals() {
        OpenTableService openTableService = new OpenTableServiceImpl();

        LocalDateTime localDateTimeStart = LocalDateTime.of(2020, 4, 29, 9, 0, 1);

        LocalDateTime localDateTimeEnd = LocalDateTime.of(2020, 4, 29, 17, 59, 59);
        Assertions.assertTrue(openTableService.isWorkingDay(localDateTimeStart));
        Assertions.assertTrue(openTableService.isWorkingDay(localDateTimeEnd));
    }

    @Test
    public void testIsOpenWhenWeekend() {
        OpenTableService openTableService = new OpenTableServiceImpl();
        LocalDateTime localDateTime = LocalDateTime.of(2020, 4, 25, 17, 59, 59);

        Assertions.assertFalse(openTableService.isWorkingDay(localDateTime));
    }

    @Test
    public void testIsOpenWhenExcludingEdgeCases() {
        OpenTableService openTableService = new OpenTableServiceImpl();

        LocalDateTime localDateTimeStart = LocalDateTime.of(2020, 4, 29, 9, 0, 0);
        LocalDateTime localDateTimeEnd = LocalDateTime.of(2020, 4, 29, 18, 0, 0);
        Assertions.assertFalse(openTableService.isWorkingDay(localDateTimeStart));
        Assertions.assertFalse(openTableService.isWorkingDay(localDateTimeEnd));
    }


}
