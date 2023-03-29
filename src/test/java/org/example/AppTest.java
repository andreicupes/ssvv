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
import org.junit.Test;

import org.example.service.Service;
import org.example.validation.*;

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
        int result =service.saveStudent("5","Alex",932);
        Iterable<Student> students = service.findAllStudents();
        assertEquals(4,students.spliterator().getExactSizeIfKnown());
        assertEquals(1,result);
    }

    @Test
    public void tc_2_saveExistingStudent(){
        int result = service.saveStudent("5","Alex",932);
        Iterable<Student> students = service.findAllStudents();
        assertEquals(4,students.spliterator().getExactSizeIfKnown());
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
        int result = service.saveStudent("7","Alex",932);
        Iterable<Student> students = service.findAllStudents();
        assertEquals(5,students.spliterator().getExactSizeIfKnown());
        assertEquals(1,result);
        service.deleteStudent("7");
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
        int result = service.saveStudent("7","Alex",932);
        Iterable<Student> students = service.findAllStudents();
        assertEquals(5,students.spliterator().getExactSizeIfKnown());
        assertEquals(1,result);
        service.deleteStudent("7");
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
        int result = service.saveStudent("7","Alex",932);
        Iterable<Student> students = service.findAllStudents();
        assertEquals(5,students.spliterator().getExactSizeIfKnown());
        assertEquals(1,result);
        service.deleteStudent("7");
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


}
