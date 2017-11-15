package core.test;

import core.api.IAdmin;
import core.api.impl.Admin;
import core.api.IInstructor;
import core.api.impl.Instructor;
import core.api.IStudent;
import core.api.impl.Student;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Vincent on 23/2/2017.
 */
public class studentTest {

    private IAdmin admin;
    private IInstructor instruct;
    private IStudent student;

    @Before
    public void setup() {
    	this.instruct = new Instructor();
        this.admin = new Admin();
        this.student = new Student();
    }
    
    @Test
    public void standardImplementation() {
    	this.admin.createClass("ECS161", 2017, "Devanbu", 15); 	
        this.student.registerForClass("Student One", "ECS161", 2017);
        assertTrue(this.student.isRegisteredFor("Student One", "ECS161", 2017));   
        
        this.instruct.addHomework("Devanbu", "ECS161", 2017, "Final Exam");
        this.student.submitHomework("Student One", "Final Exam", "Failed Miserably :(", "ECS161", 2017);
        assertTrue(this.student.hasSubmitted("Student One", "Final Exam", "ECS161", 2017));
        
        this.student.dropClass("Student One", "ECS161", 2017);
    	assertFalse(this.student.isRegisteredFor("Student One", "ECS161", 2017));
    }
    
    @Test
    public void nonexistantClass() {
        this.student.registerForClass("Student One", "ECS161", 2017);
        assertFalse(this.student.isRegisteredFor("Student One", "ECS161", 2017));
    }
    
    @Test
    public void fullClass() {
        this.admin.createClass("ECS161", 2017, "Devanbu", 1);
        this.student.registerForClass("Student One", "ECS161", 2017);
        this.student.registerForClass("Student Two", "ECS161", 2017);
        this.student.registerForClass("Student Three", "ECS161", 2017);
        assertTrue(this.student.isRegisteredFor("Student One", "ECS161", 2017));
        assertFalse(this.student.isRegisteredFor("Student Two", "ECS161", 2017));
        assertFalse(this.student.isRegisteredFor("Student Three", "ECS161", 2017));
    }
    
    @Test
    public void pastClass() {
    	this.admin.createClass("ECS161", 2000, "Devanbu", 1);
    	this.student.registerForClass("Student One", "ECS161", 2000);
    	assertFalse(this.student.isRegisteredFor("Student One", "ECS161", 2000));
    }    

    @Test
    public void noNameRegister() {
        this.admin.createClass("ECS161", 2017, "Devanbu", 15);
        this.student.registerForClass("", "ECS161", 2017);
        assertFalse(this.student.isRegisteredFor("", "ECS161", 2017));
    } // Corner Case confirmed by Devanbu
    
    @Test
    public void dropNotRegistered() {
        this.admin.createClass("ECS161", 2017, "Devanbu", 15);
        this.student.registerForClass("Student One", "ECS161", 2017);
        this.student.registerForClass("Student Two", "ECS161", 2017);
        this.student.dropClass("Student Three", "ECS161", 2017);
        assertTrue(this.student.isRegisteredFor("Student One", "ECS161", 2017));
        assertTrue(this.student.isRegisteredFor("Student Two", "ECS161", 2017));
        assertFalse(this.student.isRegisteredFor("Student Three", "ECS161", 2017));
    }
    
    @Test
    public void dropPastClass() {
        this.admin.createClass("ECS161", 2000, "Devanbu", 15);
        this.student.registerForClass("Student One", "ECS161", 2000);
        this.student.dropClass("Student One", "ECS161", 2000);
        assertTrue(this.student.isRegisteredFor("Student One", "ECS161", 2000));
    }
    
    @Test
    public void unregisteredSubmission() {
        this.admin.createClass("ECS161", 2017, "Devanbu", 15);
        this.instruct.addHomework("Devanbu", "ECS161", 2017, "Final Exam");
        this.student.submitHomework("Student One", "Final Exam", "Failed Miserably :(", "ECS161", 2017);
        assertFalse(this.student.hasSubmitted("Student One", "Final Exam", "ECS161", 2017));
    }
    
    @Test
    public void submitNonexistantHomework() {
        this.admin.createClass("ECS161", 2017, "Devanbu", 15);
        this.student.registerForClass("Student One", "ECS161", 2017);
        this.student.submitHomework("Student One", "Final Exam", "Failed Miserably :(", "ECS161", 2017);
        assertFalse(this.student.hasSubmitted("Student One", "Final Exam", "ECS161", 2017));
    }    
    
    @Test
    public void submitNotCurrentYear() {
        this.admin.createClass("ECS161", 2000, "Devanbu", 15);
        this.admin.createClass("ECS161", 2020, "Devanbu", 15);
        this.student.registerForClass("Student One", "ECS161", 2000);
        this.student.registerForClass("Student Two", "ECS161", 2020);
        this.student.submitHomework("Student One", "Final Exam", "Failed Miserably :(", "ECS161", 2000);
        this.student.submitHomework("Student Two", "Final Exam", "Aced it :)", "ECS161", 2020);
        assertFalse(this.student.hasSubmitted("Student One", "Final Exam", "ECS161", 2000));
        assertFalse(this.student.hasSubmitted("Student Two", "Final Exam", "ECS161", 2020));
    }    
}
