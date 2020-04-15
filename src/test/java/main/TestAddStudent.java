package main;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import domain.Student;
import repository.NotaXMLRepo;
import repository.StudentXMLRepo;
import repository.TemaXMLRepo;
import service.Service;
import validation.NotaValidator;
import validation.StudentValidator;
import validation.TemaValidator;
import validation.ValidationException;

import static org.junit.Assert.*;

public class TestAddStudent {

    public Service service;

    @Before
    public void setup() {
        StudentValidator studentValidator = new StudentValidator();
        TemaValidator temaValidator = new TemaValidator();
        String filenameStudent = "fisiere/StudentiTest.xml";
        String filenameTema = "fisiere/TemeTest.xml";
        String filenameNota = "fisiere/NoteTest.xml";

        //StudentFileRepository studentFileRepository = new StudentFileRepository(filenameStudent);
        //TemaFileRepository temaFileRepository = new TemaFileRepository(filenameTema);
        //NotaValidator notaValidator = new NotaValidator(studentFileRepository, temaFileRepository);
        //NotaFileRepository notaFileRepository = new NotaFileRepository(filenameNota);

        StudentXMLRepo studentXMLRepository = new StudentXMLRepo(filenameStudent);
        TemaXMLRepo temaXMLRepository = new TemaXMLRepo(filenameTema);
        NotaValidator notaValidator = new NotaValidator(studentXMLRepository, temaXMLRepository);
        NotaXMLRepo notaXMLRepository = new NotaXMLRepo(filenameNota);
        service = new Service(studentXMLRepository, studentValidator, temaXMLRepository, temaValidator, notaXMLRepository, notaValidator);
    }

    @Test
    public void studentAddSuccess() {
        Student s = new Student("123", "S1", 1, "email@email.com");
        service.addStudent(s);

        assertNotNull(service.findStudent("123"));
    }

    @Test(expected = ValidationException.class)
    public void studentAddIDNullFail() {
        Student s = new Student(null, "S1", 1, "email@email.com");
        service.addStudent(s);
    }

    @Test(expected = ValidationException.class)
    public void studentAddIDEmptyFail() {
        Student s = new Student("", "S1", 1, "email@email.com");
        service.addStudent(s);
    }

    @Test(expected = ValidationException.class)
    public void studentAddNameNullFail() {
        Student s = new Student("123", null, 1, "email@email.com");
        service.addStudent(s);
    }

    @Test(expected = ValidationException.class)
    public void studentAddNameEmptyFail() {
        Student s = new Student("123", "", 1, "email@email.com");
        service.addStudent(s);
    }

    @Test(expected = ValidationException.class)
    public void studentAddGroupInvalidFail() {
        Student s = new Student("123", "S1", -2, "email@email.com");
        service.addStudent(s);
    }

    @Test(expected = ValidationException.class)
    public void studentAddEmailNullFail() {
        Student s = new Student("123", "S1", 1, null);
        service.addStudent(s);
    }

    @Test(expected = ValidationException.class)
    public void studentAddEmailEmptyFail() {
        Student s = new Student("123", "S1", 1, "");
        service.addStudent(s);
    }

    @After
    public void drop() {
        service = null;
    }
}
