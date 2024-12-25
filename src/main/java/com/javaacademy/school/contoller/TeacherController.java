package com.javaacademy.school.contoller;

import com.javaacademy.school.dto.TeacherDto;
import com.javaacademy.school.storage.TeacherStorage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@RequestMapping("/school/teacher")
@Tag(name = "Teacher controller", description = "Контроллер для работы с школьными учителями")
@RequiredArgsConstructor
public class TeacherController {
    private final TeacherStorage teacherStorage;

    @GetMapping
    @Operation(tags = "Получение учителей", summary = "Получение учителей", description = "Получение всех учителей школы")
    @ApiResponse(responseCode = "200 - Успешно", content = {
            @Content(mediaType = "plain/text", schema = @Schema(implementation = String.class)),
            @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = TeacherDto.class)))
    })
    public ResponseEntity<?> getAll() {
        //CRUD - CREATE READ UPDATE DELETE
        //API - application programming interface
        if (teacherStorage.getData().isEmpty()) {
            return ResponseEntity.ok("Нет учителей!");
        }
        return ResponseEntity.ok(new ArrayList<>(teacherStorage.getData().values()));
    }

    @GetMapping("/{id}")
    public TeacherDto getById(@PathVariable @Parameter(description = "id учителя") Integer id) {
        return teacherStorage.getData().get(id);
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public TeacherDto create(@RequestBody TeacherDto dto) {
        teacherStorage.getCounter().addAndGet(1);
        dto.setId(teacherStorage.getCounter().get());
        teacherStorage.getData().put(teacherStorage.getCounter().get(), dto);
        return dto;
    }

    @DeleteMapping("/{id}")
    public boolean deleteById(@PathVariable Integer id) {
        return teacherStorage.getData().remove(id) != null;
    }

    @PatchMapping
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public TeacherDto update(@RequestBody TeacherDto newDto) {
        if (!teacherStorage.getData().containsKey(newDto.getId())) {
            throw new RuntimeException("Нет учителя с таким id: %s".formatted(newDto.getId()));
        }
        TeacherDto oldDto = teacherStorage.getData().get(newDto.getId());
        return update(oldDto, newDto);
    }

    private TeacherDto update(TeacherDto oldDto, TeacherDto newDto) {
        oldDto.setName(newDto.getName() != null ? newDto.getName() : oldDto.getName());
        oldDto.setSecondName(newDto.getSecondName() != null ? newDto.getSecondName() : oldDto.getSecondName());
        oldDto.setLastName(newDto.getLastName() != null ? newDto.getLastName() : oldDto.getLastName());
        oldDto.setSubject(newDto.getSubject() != null ? newDto.getSubject() : oldDto.getSubject());
        return oldDto;
    }

}
