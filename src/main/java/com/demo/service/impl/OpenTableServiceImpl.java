package com.demo.service.impl;

import com.demo.service.OpenTableService;
import org.modelmapper.internal.Pair;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class OpenTableServiceImpl implements OpenTableService {

    /**
     * @see OpenTableService#isWorkingDay(LocalDateTime)
     */
    @Override
    public boolean isWorkingDay(LocalDateTime localDateTime) {
        Map<DayOfWeek, Pair<LocalTime, LocalTime>> openTable = new HashMap<>();
        openTable.put(DayOfWeek.MONDAY, Pair.of(LocalTime.of(9, 0), LocalTime.of(18, 0)));
        openTable.put(DayOfWeek.TUESDAY, Pair.of(LocalTime.of(9, 0), LocalTime.of(18, 0)));
        openTable.put(DayOfWeek.WEDNESDAY, Pair.of(LocalTime.of(9, 0), LocalTime.of(18, 0)));
        openTable.put(DayOfWeek.THURSDAY, Pair.of(LocalTime.of(9, 0), LocalTime.of(18, 0)));
        openTable.put(DayOfWeek.FRIDAY, Pair.of(LocalTime.of(9, 0), LocalTime.of(18, 0)));

        Pair<LocalTime, LocalTime> openTableNow = openTable.get(localDateTime.getDayOfWeek());
        if (openTableNow == null) {
            return false;
        }

        return localDateTime.toLocalTime().isAfter(openTableNow.getLeft()) && localDateTime.toLocalTime().isBefore(openTableNow.getRight());
    }
}
