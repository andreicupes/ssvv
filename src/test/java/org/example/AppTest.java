package org.example;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

;
import org.example.domain.Nota;
import org.example.domain.Student;
import org.example.domain.Tema;
import org.example.repository.NotaXMLRepository;
import org.example.repository.StudentXMLRepository;
import org.example.repository.TemaXMLRepository;
import org.junit.Before;
import org.junit.Test;

import org.example.service.Service;
import org.example.validation.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;


import static org.junit.Assert.*;
import static org.mockito.Mockito.*;



@ExtendWith(MockitoExtension.class)
public class AppTest {
    private Validator<Student> studentValidator = new StudentValidator();
    private Validator<Tema> temaValidator = new TemaValidator();
    private Validator<Nota> notaValidator = new NotaValidator();

    private StudentXMLRepository fileRepository1 = new StudentXMLRepository(studentValidator, "studenti.xml");
    private TemaXMLRepository fileRepository2 = new TemaXMLRepository(temaValidator, "teme.xml");
    private NotaXMLRepository fileRepository3 = new NotaXMLRepository(notaValidator, "note.xml");

    private Service service = new Service(fileRepository1, fileRepository2, fileRepository3);
    @Test
    public void tc_1_saveStudent(){
        service.deleteStudent("5");
        int result =service.saveStudent("5","Alex",932);

        assertEquals(1,result);
    }

    @Test
    public void tc_2_saveExistingStudent(){
        int result = service.saveStudent("5","Alex",932);

        assertEquals(0,result);
        service.deleteStudent("5");
    }

    //student.getGrupa() <= 110 || student.getGrupa() >= 938)
    @Test
    public void tc_3_saveInvalidGroupStudent(){
        int result = 0;
        try{
            result =service.saveStudent("6","Alex",100);
            assertEquals(1,result);
        }catch (ValidationException e){
            assertEquals(0,result);
        }

        try{
            result =service.saveStudent("7","Alex",1000);
            assertEquals(1,result);
        }catch (ValidationException e){
            assertEquals(0,result);
        }

    }

    @Test
    public void tc_4_saveValidGroupStudent(){
        int result = service.saveStudent("56","Alex",932);
        service.deleteStudent("56");
        assertEquals(1,result);

    }

    @Test
    public void tc_5_saveInvalidIDStudent(){
        int result = 0;
        try{
            result =service.saveStudent(null,"Alex",155);
            assertEquals(1,result);
        }catch (ValidationException e){
            assertEquals(0,result);
        }

        try{
            result =service.saveStudent("","Alex",155);
            assertEquals(1,result);
        }catch (ValidationException e){
            assertEquals(0,result);
        }
    }

    @Test
    public void tc_6_saveValidIdStudent(){
        int result = service.saveStudent("76","Alex",932);
        service.deleteStudent("76");
        assertEquals(1,result);

    }

    @Test
    public void tc_7_saveInvalidNameStudent(){
        int result = 0;
        try{
            result =service.saveStudent("9","",155);
            assertEquals(1,result);
        }catch (ValidationException e){
            assertEquals(0,result);
        }

        try{
            result =service.saveStudent("9",null,155);
            assertEquals(1,result);
        }catch (ValidationException e){
            assertEquals(0,result);
        }
    }

    @Test
    public void tc_8_saveValidNameStudent(){
        int result = service.saveStudent("78","Alex",932);
        service.deleteStudent("78");
        assertEquals(1,result);

    }


    @Test
    public void tc_9_saveValidIdTema(){
        int result = service.saveTema("7","new tema",5,3);
        service.deleteTema("7");
        assertEquals(1,result);

    }

    @Test
    public void tc_10_saveInvalidIdTema(){
        int result = service.saveTema("","new tema",5,3);
        assertEquals(1,result);
    }

//    if (tema.getDescriere() == null || tema.getDescriere().equals("")) {
//        throw new ValidationException("Descriere invalida! \n");
//    }
//        if (tema.getDeadline() < 1 || tema.getDeadline() > 14 || tema.getDeadline() < tema.getStartline()) {
//        throw new ValidationException("Deadline invalid! \n");
//    }
//        if (tema.getStartline() < 1 || tema.getStartline() > 14 || tema.getStartline() > tema.getDeadline()) {
//        throw new ValidationException("Data de primire invalida! \n");
//    }
    @Test
    public void tc_11_saveValidTema(){
        int result = service.saveTema("7","new tema",5,3);
        service.deleteTema("7");
        assertEquals(1,result);

    }

    @Test
    public void tc_12_saveInvalidDescTema(){
        int result = service.saveTema("7","",5,3);
        assertEquals(1,result);
    }

    @Test
    public void tc_13_saveInvalidDeadlineTema(){
        int result = service.saveTema("7","new tema",0,3);
        assertEquals(1,result);
    }

    @Test
    public void tc_14_saveInvalidStartlineTema(){
        int result = service.saveTema("7","new tema",5,0);
        assertEquals(1,result);
    }

    @Test
    public void tc_15_saveTemaRepo(){
        Tema t = new Tema("99","test",9,7);
        Tema result = fileRepository2.save(t);
        assertEquals(t,result);
    }

    @Test
    public void tc_16_saveValidIdTema(){
        int result = service.saveTema("7","new tema",5,3);
        service.deleteTema("7");
        assertEquals(1,result);

    }

    @Test
    public void tc_17_saveExistingStudent(){
        int result = service.saveStudent("5","Alex",932);
//        service.deleteStudent("5");
        assertEquals(1,result);

    }

    @Test
    public void tc_18_saveInvalidGrade(){
        int result = service.saveNota("56","7",9,2,"feedback");
        assertEquals(-1,result);

    }

    @Test
    public void tc_19_Integrate(){

        int result1 = service.saveNota("56","7",9,2,"feedback");
        int result2 = service.saveStudent("5","Alex",932);
        int result3 = service.saveTema("7","new tema",5,3);
        service.deleteTema("7");



        assertEquals(-1,result1);
        assertEquals(0,result2);
        assertEquals(1,result3);

    }
    @Mock
    private Student s;
    @Mock
    private Tema t;


    @Before
    public void createMocks() {
        MockitoAnnotations.initMocks(this);
        s=mock(Student.class,withSettings()
                .defaultAnswer(RETURNS_SMART_NULLS));
        t=mock(Tema.class,withSettings()
                .defaultAnswer(RETURNS_SMART_NULLS));


    }

    @Test
    public void tc_20_Incremental1(){

//        int result2 = service.saveStudent("53","Alex",932);
//        service.deleteStudent("53");
//
//        assertEquals(1,result2);

        //        s=mock(Student.class);
        int result2 = service.saveStudent(s.getID(),s.getNume(),s.getGrupa());
        assertEquals(1,result2);
        reset(s);

    }
    @Test
    public void tc_21_Incremental2(){
//
//        int result2 = service.saveStudent("53","Alex",932);
//        int result3 = service.saveTema("74","new tema",5,3);
//        service.deleteTema("74");
//        service.deleteStudent("53");
//
//
//        assertEquals(1,result2);
//        assertEquals(1,result3);

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


//        int result2 = service.saveStudent("53","Alex",932);
//        int result3 = service.saveTema("74","new tema",5,3);
//        int result1 = service.saveNota("53","74",9,2,"feedback");
//        service.deleteTema("74");
//        service.deleteStudent("53");
//
//
//        assertEquals(1,result1);
//        assertEquals(1,result2);
//        assertEquals(1,result3);


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
