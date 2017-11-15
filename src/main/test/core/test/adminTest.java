package core.test;

import core.api.IAdmin;
import core.api.impl.Admin;
import core.api.IStudent;
import core.api.impl.Student;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class adminTest {

    private IAdmin admin;
    private IStudent student;

    @Before
    public void setup() {
        this.admin = new Admin();
        this.student = new Student();
    }
    
    @Test
    public void standardImplementation() {
    	this.admin.createClass("ECS161", 2017, "Devanbu", 15);
        assertTrue(this.admin.classExists("ECS161", 2017));    	
        this.admin.changeCapacity("ECS161", 2017, 10);
        assertTrue(this.admin.getClassCapacity("ECS161", 2017) == 10);
    }
    
    @Test
    public void assignInstructorThree() {
    	this.admin.createClass("ECS30", 2017, "Devanbu", 15);
    	this.admin.createClass("ECS161", 2017, "Devanbu", 15);
    	this.admin.createClass("ECS189", 2017, "Devanbu", 15);
        assertTrue(this.admin.classExists("ECS30", 2017));
        assertTrue(this.admin.classExists("ECS161", 2017));
        assertFalse(this.admin.classExists("ECS189", 2017));
    }
    
    @Test
    public void negativeOrZeroCapacityMake() {
        this.admin.createClass("ECS161", 2017, "Devanbu", -1);
        assertFalse(this.admin.classExists("ECS161", 2017));
        
        this.admin.createClass("ECS30", 2017, "Devanbu", 0);
        assertFalse(this.admin.classExists("ECS30", 2017));
    } // Corner Case
    
    @Test
    public void noClassName() {
        this.admin.createClass("", 2017, "Devanbu", 0);
        assertFalse(this.admin.classExists("", 2017));
    } // Corner Case confirmed by Devanbu
    
    @Test
    public void noInstructorName() {
        this.admin.createClass("ECS161", 2017, "", 0);
        assertFalse(this.admin.classExists("ECS161", 2017));
    } // Corner Case confirmed by Devanbu
    
/*    @Test
    public void pastClassCreated() {
        this.admin.createClass("ECS161", 2000, "Devanbu", 20);
        assertFalse(this.admin.classExists("ECS161", 2000));
    } // Corner Case
    */
    
    @Test
    public void sameClassDifferentInstructor() {
        this.admin.createClass("ECS161", 2017, "Devanbu", 5);
        this.admin.createClass("ECS161", 2017, "D3u4nboo", 5);
        assertTrue(this.admin.getClassInstructor("ECS161", 2017) == "Devanbu");
    } 
    
    @Test
    public void changeCapacityToNegative() {
        this.admin.createClass("ECS161", 2017, "Devanbu", 15);
        this.admin.changeCapacity("ECS161", 2017, -1);
        assertFalse(this.admin.getClassCapacity("ECS161", 2017) == -1);
    }
    
    @Test
    public void changeCapacityLessThanEnrolled() {
    	this.admin.createClass("ECS161", 2017, "Devanbu", 15);
		this.student.registerForClass("Temp One", "ECS161", 2017);
		this.student.registerForClass("Temp Two", "ECS161", 2017);
		this.admin.changeCapacity("ECS161", 2017, 1);
        assertFalse(this.admin.getClassCapacity("ECS161", 2017) == 1);		
    }

    @Test
    public void changeCapacityNonexistantClass() {
        this.admin.changeCapacity("ECS161", 2017, 10);
        assertFalse(this.admin.getClassCapacity("ECS161", 2017) == 10);
    }

}
