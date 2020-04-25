package controller;

import controller.Planner;
import parsing.*;

import java.io.IOException;

public class FlowchartMain {
    public static void main(String[] args) throws IOException {
        System.out.println("This is the main class for our whole project. Just testing things out.");
        FullCourseList courseList = new FullCourseList(); // CourseList contains a hashmap of all of UAH's courses which is used throughout this program
        Planner plan = new Planner();
        plan.drawSelectorWindow();
    }
}
