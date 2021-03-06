package com.meli.obterdiploma;

import com.meli.obterdiploma.exception.StudentNotFoundException;
import com.meli.obterdiploma.model.StudentDTO;
import com.meli.obterdiploma.model.SubjectDTO;
import com.meli.obterdiploma.repository.IStudentDAO;
import com.meli.obterdiploma.service.ObterDiplomaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class ObterDiplomaServiceTests {
    @Mock
    private IStudentDAO mockStudentDao;

    @InjectMocks
    private ObterDiplomaService service;
    private StudentDTO mockedStudent;

    @BeforeEach
    public void beforeEach(){
        this.mockedStudent = new StudentDTO(
                111L,
                "Mocked student",
                null,
                null,
                List.of(new SubjectDTO(
                        "Mock subject",
                        5.0
                ),
                        new SubjectDTO(
                                "Mock subject 2",
                                4.0
                        )  )
        );
    }

    @Test
    public void shouldAnalyzeScore(){
        Mockito.when(mockStudentDao.findById(1L)).thenReturn(mockedStudent);

        StudentDTO calculatedStudent = service.analyzeScores(1L);

        assertEquals(calculatedStudent.getAverageScore(), 4.5);
        assertTrue(calculatedStudent.getMessage().length() > 0);
        Mockito.verify(mockStudentDao, Mockito.times(1)).findById(1L);

    }

    @Test
    public void shouldAnalyzeScoreAndReturnSuccessMessage(){
        mockedStudent.setSubjects(List.of(new SubjectDTO(
                "Mock subject",
                10.0
        )));
        Mockito.when(mockStudentDao.findById(1L)).thenReturn(mockedStudent);

        StudentDTO calculatedStudent = service.analyzeScores(1L);

        assertEquals(calculatedStudent.getAverageScore(), 10.0);
        assertEquals("O aluno Mocked student obteve uma m??dia de 10. Parab??ns!", calculatedStudent.getMessage());
        Mockito.verify(mockStudentDao, Mockito.times(1)).findById(1L);

    }

    @Test
    public void shouldReceiveExceptionWhenReceiveInvalidID(){
        Mockito.when(mockStudentDao.findById(null)).thenThrow(new StudentNotFoundException(null));

        assertThrows(StudentNotFoundException.class, () -> service.analyzeScores(null));
        Mockito.verify(mockStudentDao, Mockito.times(1)).findById(null);

    }
}
