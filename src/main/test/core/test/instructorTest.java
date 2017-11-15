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
public class instructorTest {

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
        this.instruct.addHomework("Devanbu", "ECS161", 2017, "Final Exam");
        assertTrue(this.instruct.homeworkExists("ECS161", 2017, "Final Exam"));
        
        this.student.submitHomework("Student One", "Final Exam", "Failed Miserably :(", "ECS161", 2017);
        this.instruct.assignGrade("Devanbu", "ECS161", 2017, "Final Exam", "Student One", 50);
        assertTrue(this.instruct.getGrade("ECS161", 2017, "Final Exam", "Student One") == 50);
    }
    
    @Test
    public void noNameHomework() {
        this.admin.createClass("ECS161", 2017, "Devanbu", 15);
        this.instruct.addHomework("Devanbu", "ECS161", 2017, "");
        assertFalse(this.instruct.homeworkExists("ECS161", 2017, ""));
    } // Corner Case confirmed by Devanbu
    
    @Test
    public void sameNameHomework() {
        this.admin.createClass("ECS161", 2017, "Devanbu", 15);
        this.student.registerForClass("Student One", "ECS161", 2017);
        this.instruct.addHomework("Devanbu", "ECS161", 2017, "Final Exam");
        this.student.submitHomework("Student One", "Final Exam", "Failed Miserably :(", "ECS161", 2017);
        this.instruct.assignGrade("Devanbu", "ECS161", 2017, "Final Exam", "Student One", 50);
        assertNotNull(this.instruct.getGrade("ECS161", 2017, "Final Exam", "Student One"));
        
        this.instruct.addHomework("Devanbu", "ECS161", 2017, "Final Exam");
        assertNotNull(this.instruct.getGrade("ECS161", 2017, "Final Exam", "Student One"));
    }
    
    @Test
    public void wrongInstructorMake() {
        this.admin.createClass("ECS161", 2017, "Devanbu", 15);
        this.instruct.addHomework("D3u4nboo", "ECS161", 2017, "Final Exam");
        assertFalse(this.instruct.homeworkExists("ECS161", 2017, "Final Exam"));
    } // Also covers empty instructor name
    
    @Test
    public void nonexistantClass() {
        this.instruct.addHomework("Devanbu", "ECS161", 2017, "Final Exam");
        assertFalse(this.instruct.homeworkExists("ECS161", 2017, "Final Exam"));
    }
    
    @Test
    public void nonexistantHomeworkGraded() {
        this.admin.createClass("ECS161", 2017, "Devanbu", 15);
        this.student.registerForClass("Student One", "ECS161", 2017);
        this.student.submitHomework("Student One", "Final Exam", "Failed Miserably :(", "ECS161", 2017);
        this.instruct.assignGrade("Devanbu", "ECS161", 2017, "Final Exam", "Student One", 50);
        assertNull(this.instruct.getGrade("ECS161", 2017, "Final Exam", "Student One"));
    }
    
    @Test
    public void wrongInstructorGrade() {
        this.admin.createClass("ECS161", 2017, "Devanbu", 15);
        this.instruct.addHomework("Devanbu", "ECS161", 2017, "Final Exam");
        this.student.registerForClass("Student One", "ECS161", 2017);
        this.student.submitHomework("Student One", "Final Exam", "Failed Miserably :(", "ECS161", 2017);
        this.instruct.assignGrade("D3u4nboo", "ECS161", 2017, "Final Exam", "Student One", 50);
        assertFalse(this.instruct.getGrade("ECS161", 2017, "Final Exam", "Student One") == 50);
    } // Also covers empty instructor name       
    
    @Test
    public void noSubmissionGraded() {
        this.admin.createClass("ECS161", 2017, "Devanbu", 15);
        this.instruct.addHomework("Devanbu", "ECS161", 2017, "Final Exam");
        this.student.registerForClass("Student One", "ECS161", 2017);
        this.instruct.assignGrade("Devanbu", "ECS161", 2017, "Final Exam", "Student One", 50);
        assertFalse(this.instruct.getGrade("ECS161", 2017, "Final Exam", "Student One") == 50);
    }
    
    @Test
    public void nonexistentStudent() {
        this.admin.createClass("ECS161", 2017, "Devanbu", 15);
        this.instruct.addHomework("Devanbu", "ECS161", 2017, "Final Exam");
        this.instruct.assignGrade("Devanbu", "ECS161", 2017, "Final Exam", "Student One", 50);
        assertFalse(this.instruct.getGrade("ECS161", 2017, "Final Exam", "Student One") == 50);
    }
    
    @Test
    public void negativeGrade() {
        this.admin.createClass("ECS161", 2017, "Devanbu", 15);
        this.instruct.addHomework("Devanbu", "ECS161", 2017, "Final Exam");
        this.student.registerForClass("Student One", "ECS161", 2017);
        this.student.submitHomework("Student One", "Final Exam", "Failed Miserably :(", "ECS161", 2017);
        this.instruct.assignGrade("Devanbu", "ECS161", 2017, "Final Exam", "Student One", -50);
        assertFalse(this.instruct.getGrade("ECS161", 2017, "Final Exam", "Student One") == -50);
    } // Corner Case 
}
