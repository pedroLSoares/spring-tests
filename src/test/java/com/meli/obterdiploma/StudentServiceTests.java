package com.meli.obterdiploma;


import com.meli.obterdiploma.exception.StudentNotFoundException;
import com.meli.obterdiploma.model.StudentDTO;
import com.meli.obterdiploma.model.SubjectDTO;
import com.meli.obterdiploma.repository.IStudentDAO;
import com.meli.obterdiploma.repository.IStudentRepository;
import com.meli.obterdiploma.repository.StudentDAO;
import com.meli.obterdiploma.repository.StudentRepository;
import com.meli.obterdiploma.service.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class StudentServiceTests {

    private IStudentDAO mockStudentDao;

    private IStudentRepository mockStudentRpository;

    private StudentService service;

    private final StudentDTO mockedStudent = new StudentDTO(
            111L,
            "Mocked student",
            "",
            5.0,
            List.of(new SubjectDTO(
                    "Mock subject",
                    5.5
            ))
    );

    @BeforeEach
    public void beforeEach(){
        this.mockStudentDao = Mockito.mock(StudentDAO.class);
        this.mockStudentRpository = Mockito.mock(StudentRepository.class);

        this.service = new StudentService(this.mockStudentDao, this.mockStudentRpository);
    }

    // CREATE
    @Test
    public void shouldCreateStudent(){


        assertDoesNotThrow(() -> this.service.create(mockedStudent));
        Mockito.verify(mockStudentDao, Mockito.times(1)).save(this.mockedStudent);
    }

    @Test
    public void shouldCallSaveWhenReceiveNull(){
        this.service.create(null);
        Mockito.verify(mockStudentDao, Mockito.times(1)).save(null);
    }

    //READ

    @Test
    public void shouldFindStudent(){
        Mockito.when(mockStudentDao.findById(1L)).thenReturn(mockedStudent);

        StudentDTO foundStudent = service.read(1L);

        assertEquals(mockedStudent, foundStudent);
        Mockito.verify(mockStudentDao, Mockito.times(1)).findById(1L);
    }

    @Test
    public void shouldReceiveExceptionIFNotFoundStudent(){
        Long id = 99L;
        Mockito.when(mockStudentDao.findById(id)).thenThrow(new StudentNotFoundException(id));

        assertThrows(StudentNotFoundException.class, () -> service.read(id));
        Mockito.verify(mockStudentDao, Mockito.times(1)).findById(id);
    }

    @Test
    public void shouldReceiveExceptionWhenReceiveNullID(){
        Long id = null;
        Mockito.when(mockStudentDao.findById(id)).thenThrow(new StudentNotFoundException(id));

        assertThrows(StudentNotFoundException.class, () -> service.read(id));
        Mockito.verify(mockStudentDao, Mockito.times(1)).findById(id);
    }

    // UPDATE

    @Test
    public void shouldUpdateStudent(){
        service.update(mockedStudent);
        Mockito.verify(mockStudentDao, Mockito.times(1)).save(mockedStudent);
    }

    // DELETE
    @Test
    public void shouldDeleteStudent(){

        service.delete(1L);

        Mockito.verify(mockStudentDao, Mockito.times(1)).delete(1L);
    }

    @Test
    public void shouldCallDeleteWithNullID(){
        service.delete(null);
        Mockito.verify(mockStudentDao, Mockito.times(1)).delete(null);
    }

    // GET ALL

    @Test
    public void shouldFindAllStudents(){
        Mockito.when(mockStudentRpository.findAll()).thenReturn(Set.of(mockedStudent));

        Set<StudentDTO> students = service.getAll();

        assertEquals(Set.of(mockedStudent).size(), students.size());
        Mockito.verify(mockStudentRpository, Mockito.times(1)).findAll();
    }


}
