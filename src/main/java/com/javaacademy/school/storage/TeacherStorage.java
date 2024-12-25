package com.javaacademy.school.storage;

import com.javaacademy.school.dto.TeacherDto;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public interface TeacherStorage {

    Map<Integer, TeacherDto> getData();

    AtomicInteger getCounter();
}
