package com.javaacademy.school.storage;

import com.javaacademy.school.dto.TeacherDto;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Component
@Profile("prom")
public class TeacherStorageImpl {
    private final Map<Integer, TeacherDto> data = new HashMap<>();
    private final AtomicInteger counter = new AtomicInteger();

    public Map<Integer, TeacherDto> getData() {
        return this.data;
    }

    public AtomicInteger getCounter() {
        return this.counter;
    }
}
