package com.meli.obterdiploma;

import com.meli.obterdiploma.exception.InvalidObjectException;
import com.meli.obterdiploma.exception.StudentNotFoundException;
import com.meli.obterdiploma.model.StudentDTO;
import com.meli.obterdiploma.model.SubjectDTO;
import com.meli.obterdiploma.repository.StudentDAO;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class StudentDAOTests {

    private static StudentDAO studentDAO;
    private StudentDTO mockedStudent;

    @BeforeAll
    public static void beforeAll(){
        studentDAO = new StudentDAO();
    }

    @BeforeEach
    public void beforeEach(){
        studentDAO.resetData();
        StudentDTO mockedStudent = new StudentDTO(
                1L,
                "Mocked student",
                "",
                5.0,
                List.of(new SubjectDTO(
                        "Mock subject",
                        5.5
                ))
        );
        this.mockedStudent = mockedStudent;

        studentDAO.save(mockedStudent);
    }

    @AfterAll
    public static void afterAll(){
        studentDAO.resetData();
    }

    // SAVE
    @Test
    public void shouldSaveNewStudent(){
        StudentDTO mockedStudent = new StudentDTO(
                111L,
                "Mocked student",
                "",
                5.0,
                List.of(new SubjectDTO(
                        "Mock subject",
                        5.5
                ))
        );

        studentDAO.save(mockedStudent);
        boolean exists = studentDAO.exists(mockedStudent);

        assertTrue(exists);

        assert false;
    }

    @Test
    public void shouldSaveNewWithoutID(){
        StudentDTO mockedStudent = new StudentDTO(
                null,
                "Mocked student",
                "",
                5.0,
                List.of(new SubjectDTO(
                        "Mock subject",
                        5.5
                ))
        );

        studentDAO.save(mockedStudent);
        boolean exists = studentDAO.exists(mockedStudent);

        assertTrue(exists);

    }

    @Test
    public void shouldNotSaveNullValue(){
        assertThrows(InvalidObjectException.class, () -> studentDAO.save(null));

    }

    // DELETE
    @Test
    public void shouldDeleteStudent(){
        boolean removed = studentDAO.delete(1L);

        assertTrue(removed);
    }

    @Test
    public void shouldNotDeleteStudentWhenPassInvalidID(){
        boolean removed = studentDAO.delete(null);

        assertFalse(removed);
    }

    @Test
    public void shouldNotDeleteStudentWhenReceiveNonExistentID(){
        boolean removed = studentDAO.delete(9999L);

        assertFalse(removed);
    }

    // EXISTS
    @Test
    public void shouldCheckIfStudentExists(){

        assertTrue(studentDAO.exists(this.mockedStudent));
    }

    @Test
    public void shouldReturnFalseWhenNotFindStudent(){
        assertFalse(studentDAO.exists(new StudentDTO(
                999L,
                "Non existent",
                "",
                0.0,
                new ArrayList<>())));
    }

    @Test
    public void shouldReturnFalseWhenCheckNullValue(){
        assertThrows(NullPointerException.class, () -> studentDAO.exists(null));
    }


    // FIND_BY_ID
    @Test
    public void shouldFindStudentByID(){
        StudentDTO studentDTO = studentDAO.findById(1L);

        assertNotNull(studentDTO);
    }

    @Test
    public void shouldNotFindStudent(){
        assertThrows(StudentNotFoundException.class, () -> studentDAO.findById(999L));
    }

    @Test
    public void shouldNotFindStudentWithNullID(){
        assertThrows(StudentNotFoundException.class, () -> studentDAO.findById(null));
    }




}
