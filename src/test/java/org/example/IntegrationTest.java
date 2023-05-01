package org.example;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.*;

;
import org.example.domain.Nota;
import org.example.domain.Student;
import org.example.domain.Tema;
import org.example.repository.NotaXMLRepository;
import org.example.repository.StudentXMLRepository;
import org.example.repository.TemaXMLRepository;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import org.example.service.Service;
import org.example.validation.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;


@ExtendWith(MockitoExtension.class)
public class IntegrationTest {

    private Validator<Student> studentValidator = new StudentValidator();
    private Validator<Tema> temaValidator = new TemaValidator();
    private Validator<Nota> notaValidator = new NotaValidator();

    private StudentXMLRepository fileRepository1 = new StudentXMLRepository(studentValidator, "studenti.xml");
    private TemaXMLRepository fileRepository2 = new TemaXMLRepository(temaValidator, "teme.xml");
    private NotaXMLRepository fileRepository3 = new NotaXMLRepository(notaValidator, "note.xml");

    private Service service = new Service(fileRepository1, fileRepository2, fileRepository3);
    private AutoCloseable closeable;


    @Mock
    private Student s;
    @Mock
    private Tema t;


    @Before
    public void createMocks() {
        MockitoAnnotations.initMocks(this);
        s=mock(Student.class,withSettings()
                .defaultAnswer(RETURNS_SMART_NULLS));
        t=mock(Tema.class);
        s.setID("53");
        t.setID("89");
        System.out.println(s.getID());
        System.out.println(t.getID());
    }

//    @AfterEach
//    void closeService() throws Exception {
//        if(s.getID() != null){
//        this.service.deleteStudent(s.getID());}
//        if(t.getID() != null){
//        this.service.deleteTema(t.getID());}
//    }

//    @BeforeEach
//    void initService() {
//        closeable = MockitoAnnotations.openMocks(this);
//
//    }
//
//    @AfterEach
//    void closeService() throws Exception {
//        closeable.close();
//    }


    @Test
    public void tc_23_Incremental1(){
//        s=mock(Student.class);
        int result2 = service.saveStudent(s.getID(),s.getNume(),s.getGrupa());
        assertEquals(1,result2);
        reset(s);

    }


    @Test
    public void tc_21_Incremental2(){
//        s=mock(Student.class);
//        t=mock(Tema.class);
        int result2 = service.saveStudent(s.getID(),s.getNume(),s.getGrupa());
        int result3 = service.saveTema(t.getID(),t.getDescriere(),t.getDeadline(),t.getStartline());
        reset(s,t);


        assertEquals(1,result2);
        assertEquals(1,result3);

    }

    @Test
    public void tc_22_Incremental3(){


//        s=mock(Student.class);
//        t=mock(Tema.class);

        int result2 = service.saveStudent(s.getID(),s.getNume(),s.getGrupa());
        int result3 = service.saveTema(t.getID(),t.getDescriere(),t.getDeadline(),t.getStartline());
        int result1 = service.saveNota(s.getID(),t.getID(),9,1,"feedback");
        reset(s,t);


        assertEquals(1,result2);
        assertEquals(1,result3);
        assertEquals(-1,result1);


    }





}
