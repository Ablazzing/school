package com.javaacademy.school.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Schema(description = "Учитель")
@Builder
public class TeacherDto {
    private Integer id;

    @Schema(description = "Имя")
    private String name;

    @JsonProperty("second_name")
    @Schema(description = "Отчество")
    private String secondName;

    @JsonProperty("last_name")
    @Schema(description = "Фамилия")
    private String lastName;

    //Предмет
    @Schema(description = "Предмет")
    private String subject;
}
