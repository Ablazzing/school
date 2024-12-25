package com.javaacademy.school.web;

import com.javaacademy.school.dto.TeacherDto;
import com.javaacademy.school.web.storage.TeacherStorageTestImpl;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.common.mapper.TypeRef;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class TeacherControllerTest {
    @Autowired
    private TeacherStorageTestImpl teacherStorage;
    private final RequestSpecification requestSpecification = new RequestSpecBuilder()
            .setBasePath("/school/teacher")
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();
    private final ResponseSpecification responseSpecification = new ResponseSpecBuilder()
            .log(LogDetail.ALL)
            .build();

    @Test
    @DisplayName("Получение всех учителей")
    public void getAllSuccess() {
        List<TeacherDto> teacherDtos = given(requestSpecification)
                .get()
                .then()
                .spec(responseSpecification)
                .statusCode(200)
                .extract()
                .body()
                .as(new TypeRef<>() {});

        assertEquals(1, teacherDtos.size());
        TeacherDto teacherDto = teacherDtos.get(0);
        assertEquals("ivan", teacherDto.getName());
        assertEquals("ivanov", teacherDto.getLastName());
        assertEquals("ivanovich", teacherDto.getSecondName());
        assertEquals("physics", teacherDto.getSubject());
    }

    @Test
    @DisplayName("Получение всех учителей")
    public void getAllEmptySuccess() {
        teacherStorage.getData().clear();
        String response = given(requestSpecification)
                .get()
                .then()
                .contentType(ContentType.TEXT)
                .log().all()
                .statusCode(200)
                .extract()
                .body()
                .asPrettyString();
        Assertions.assertEquals("Нет учителей!", response);
    }

    @Test
    @DisplayName("Получение учителя по id")
    public void getTeacherByIdSuccess() {
        TeacherDto result = given(requestSpecification)
                .get("/1")
                .then()
                .spec(responseSpecification)
                .statusCode(200)
                .extract()
                .body()
                .as(TeacherDto.class);

        assertEquals("ivan", result.getName());
        assertEquals("ivanov", result.getLastName());
        assertEquals("ivanovich", result.getSecondName());
        assertEquals("physics", result.getSubject());
    }

    @Test
    @DisplayName("Удаление учителя по id")
    public void deleteTeacherIdSuccess() {
        Boolean result = given(requestSpecification)
                .delete("/1")
                .then()
                .spec(responseSpecification)
                .statusCode(200)
                .extract()
                .body()
                .as(Boolean.class);

        assertTrue(result);
        assertFalse(teacherStorage.getData().containsKey(1));
    }

    @Test
    @DisplayName("Создание учителя")
    public void createTeacherSuccess() {
        TeacherDto teacherDto = TeacherDto.builder()
                .id(null)
                .name("aleksandr")
                .secondName("radionovich")
                .lastName("radionov")
                .subject("mathematics")
                .build();

        given(requestSpecification)
                .body(teacherDto)
                .post()
                .then()
                .spec(responseSpecification)
                .statusCode(201)
                .body("name", equalTo("aleksandr"))
                .body("second_name", equalTo("radionovich"))
                .body("last_name", equalTo("radionov"))
                .body("subject", equalTo("mathematics"))
                .body("id", equalTo(2));
    }

    @Test
    @DisplayName("Обновление предмета учителя")
    public void updateTeacherSuccess() {
        TeacherDto requestDto = TeacherDto.builder()
                .id(1)
                .subject("mathematics")
                .build();
        given(requestSpecification)
                .body(requestDto)
                .patch()
                .then()
                .spec(responseSpecification)
                .statusCode(202)
                .body("name", equalTo("ivan"))
                .body("second_name", equalTo("ivanovich"))
                .body("last_name", equalTo("ivanov"))
                .body("subject", equalTo("mathematics"))
                .body("id", equalTo(1));

        TeacherDto teacherDto = teacherStorage.getData().get(1);
        assertEquals("mathematics", teacherDto.getSubject());
    }
}
