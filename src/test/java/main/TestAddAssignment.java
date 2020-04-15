package main;

import domain.Tema;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import repository.NotaXMLRepo;
import repository.StudentXMLRepo;
import repository.TemaXMLRepo;
import service.Service;
import validation.NotaValidator;
import validation.StudentValidator;
import validation.TemaValidator;
import validation.ValidationException;

public class TestAddAssignment {

    private Service service;

    @Before
    public void init() {
        StudentValidator studentValidator = new StudentValidator();
        TemaValidator temaValidator = new TemaValidator();

        String filenameStudent = "fisiere/StudentiTest.xml";
        String filenameTema = "fisiere/TemeTest.xml";
        String filenameNota = "fisiere/NoteTest.xml";

        StudentXMLRepo studentXMLRepository = new StudentXMLRepo(filenameStudent);
        TemaXMLRepo temaXMLRepository = new TemaXMLRepo(filenameTema);
        NotaValidator notaValidator = new NotaValidator(studentXMLRepository, temaXMLRepository);
        NotaXMLRepo notaXMLRepository = new NotaXMLRepo(filenameNota);
        service = new Service(studentXMLRepository, studentValidator, temaXMLRepository, temaValidator, notaXMLRepository, notaValidator);
    }

    @Test
    public void assignmentSuccess() {
        String nrTema = "9999";
        String descriere = "descrierea";
        int deadline = 4;
        int primire = 3;

        Tema tema = new Tema(nrTema, descriere, deadline, primire);
        service.addTema(tema);
    }

    @Test(expected = ValidationException.class)
    public void assignmentInvalidIdNull() {
        String nrTema = null;
        String descriere = "descriere";
        int deadline = 4;
        int primire = 3;

        Tema tema = new Tema(nrTema, descriere, deadline, primire);

        service.addTema(tema);
    }

    @Test(expected = ValidationException.class)
    public void assignmentInvalidId() {
        String nrTema = "";
        String descriere = "descriere";
        int deadline = 4;
        int primire = 3;

        Tema tema = new Tema(nrTema, descriere, deadline, primire);

        service.addTema(tema);

    }

    @Test(expected = ValidationException.class)
    public void assignmentInvalidDescription() {
        String nrTema = "111";
        String descriere = "";
        int deadline = 4;
        int primire = 3;

        Tema tema = new Tema(nrTema, descriere, deadline, primire);

        service.addTema(tema);

    }

    @Test(expected = ValidationException.class)
    public void assignmentInvalidDeadlineLwr() {
        String nrTema = "111";
        String descriere = "blablalba";
        int deadline = -4;
        int primire = 3;

        Tema tema = new Tema(nrTema, descriere, deadline, primire);

        service.addTema(tema);

    }

    @Test(expected = ValidationException.class)
    public void assignmentInvalidDeadlineHig() {
        String nrTema = "111";
        String descriere = "blablabla";
        int deadline = 304;
        int primire = 3;

        Tema tema = new Tema(nrTema, descriere, deadline, primire);

        service.addTema(tema);

    }

    @Test(expected = ValidationException.class)
    public void assignmentInvalidPrimireLwr() {
        String nrTema = "111";
        String descriere = "blablabla";
        int deadline = 4;
        int primire = -3;

        Tema tema = new Tema(nrTema, descriere, deadline, primire);

        service.addTema(tema);

    }

    @Test(expected = ValidationException.class)
    public void assignmentInvalidPrimireHig() {
        String nrTema = "111";
        String descriere = "blablabla";
        int deadline = 3;
        int primire = 100;

        Tema tema = new Tema(nrTema, descriere, deadline, primire);

        service.addTema(tema);
    }

    @After
    public void drop() {
        service = null;
    }
}