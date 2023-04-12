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

public class IntegrationTesting {

    private Validator<Student> studentValidator = new StudentValidator();
    private Validator<Tema> temaValidator = new TemaValidator();
    private Validator<Nota> notaValidator = new NotaValidator();

    private StudentXMLRepository fileRepository1 = new StudentXMLRepository(studentValidator, "studenti.xml");
    private TemaXMLRepository fileRepository2 = new TemaXMLRepository(temaValidator, "teme.xml");
    private NotaXMLRepository fileRepository3 = new NotaXMLRepository(notaValidator, "note.xml");

    private Service service = new Service(fileRepository1, fileRepository2, fileRepository3);

    @Test
    public void tc_1_saveValidIdTema(){
        int result = service.saveTema("7","new tema",5,3);
        service.deleteTema("7");
        assertEquals(1,result);

    }

    @Test
    public void tc_2_saveExistingStudent(){
        int result = service.saveStudent("5","Alex",932);
//        service.deleteStudent("5");
        assertEquals(0,result);

    }

    @Test
    public void tc_3_saveInvalidGrade(){
        int result = service.saveNota("56","7",9,2,"feedback");
        assertEquals(-1,result);

    }

    @Test
    public void tc_4_Integrate(){

        int result1 = service.saveNota("56","7",9,2,"feedback");
        int result2 = service.saveStudent("5","Alex",932);
        int result3 = service.saveTema("7","new tema",5,3);
        service.deleteTema("7");



        assertEquals(-1,result1);
        assertEquals(0,result2);
        assertEquals(1,result3);

    }


}
