package com.javaacademy.school.contoller;

import com.javaacademy.school.dto.TeacherDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/school/teacher")
@Tag(name = "Teacher controller", description = "Контроллер для работы с школьными учителями")
public class TeacherController {
    private final Map<Integer, TeacherDto> data = new HashMap<>();
    private int counter = 0;

    @GetMapping
    @Operation(tags = "Получение учителей", summary = "Получение учителей", description = "Получение всех учителей школы")
    @ApiResponse(responseCode = "200 - Успешно", content = {
            @Content(mediaType = "plain/text", schema = @Schema(implementation = String.class)),
            @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = TeacherDto.class)))
    })
    public ResponseEntity<?> getAll() {
        //CRUD - CREATE READ UPDATE DELETE
        //API - application programming interface
        if (data.isEmpty()) {
            return ResponseEntity.ok("Нет учителей!");
        }
        return ResponseEntity.ok(new ArrayList<>(data.values()));
    }

    @GetMapping("/{id}")
    public TeacherDto getById(@PathVariable @Parameter(description = "id учителя") Integer id) {
        return data.get(id);
    }

    @PostMapping
    public TeacherDto create(@RequestBody TeacherDto dto) {
        counter++;
        dto.setId(counter);
        data.put(counter, dto);
        return dto;
    }

    @DeleteMapping("/{id}")
    public boolean deleteById(@PathVariable Integer id) {
        return data.remove(id) != null;
    }

    @PatchMapping
    public TeacherDto update(@RequestBody TeacherDto newDto) {
        if (!data.containsKey(newDto.getId())) {
            throw new RuntimeException("Нет учителя с таким id: %s".formatted(newDto.getId()));
        }
        TeacherDto oldDto = data.get(newDto.getId());
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
