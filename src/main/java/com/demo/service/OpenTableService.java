package com.demo.service;

import java.time.LocalDateTime;

public interface OpenTableService {
    /**
     * Return either is an working day or not for {@param localDateTime}
     *
     * @param localDateTime
     * @return
     */
    boolean isWorkingDay(LocalDateTime localDateTime);
}
