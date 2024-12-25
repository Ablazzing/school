package com.javaacademy.school.web.storage;

import com.javaacademy.school.dto.TeacherDto;
import com.javaacademy.school.storage.TeacherStorage;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class TeacherStorageTestImpl implements TeacherStorage {
    private final Map<Integer, TeacherDto> data = new HashMap<>();
    private final AtomicInteger counter = new AtomicInteger();

    @PostConstruct
    public void init() {
        TeacherDto teacherDto = TeacherDto.builder()
                .id(1)
                .name("ivan")
                .secondName("ivanovich")
                .lastName("ivanov")
                .subject("physics")
                .build();
        data.put(1, teacherDto);
        counter.addAndGet(1);
    }


    @Override
    public Map<Integer, TeacherDto> getData() {
        return data;
    }

    @Override
    public AtomicInteger getCounter() {
        return counter;
    }
}
