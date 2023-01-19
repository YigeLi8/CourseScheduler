package coursescheduleryigeliyql6014;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author yiges
 */
public class CourseQueries {

    private static Connection connection;
    private static PreparedStatement addCourse;
    private static PreparedStatement getCourseList;
    private static ResultSet resultSet;

    public static ArrayList<CourseEntry> getAllCourses(String semester) {
        connection = DBConnection.getConnection();
        ArrayList<CourseEntry> courses = new ArrayList<CourseEntry>();
        try {
            getCourseList = connection.prepareStatement("select semester, coursecode, description, seats from app.course where semester = ?");
            getCourseList.setString(1, semester);
            resultSet = getCourseList.executeQuery();

            while (resultSet.next()) {
                courses.add(new CourseEntry(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getInt(4)));
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return courses;
    }

    public static void addCourse(CourseEntry course) {
        connection = DBConnection.getConnection();
        try {
            addCourse = connection.prepareStatement("insert into app.course (semester, courseCode, description, seats) values (?, ?, ?, ?)");
            addCourse.setString(1, course.getSemester());
            addCourse.setString(2, course.getCourseCode());
            addCourse.setString(3, course.getDescription());
            addCourse.setInt(4, course.getSeats());

            addCourse.executeUpdate();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    public static ArrayList<String> getAllCourseCodes(String semester) {
        connection = DBConnection.getConnection();
        ArrayList<String> courseCodes = new ArrayList<String>();
        try {
            getCourseList = connection.prepareStatement("select coursecode from app.course where semester = ?");
            getCourseList.setString(1, semester);
            resultSet = getCourseList.executeQuery();

            while (resultSet.next()) {
                courseCodes.add(resultSet.getString(1));
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return courseCodes;
    }

    public static int getCourseSeats(String semester, String courseCode) {
        connection = DBConnection.getConnection();
        int count = 0;
        try {
            getCourseList = connection.prepareStatement("select seats from app.course where semester = ? and coursecode = ?");
            getCourseList.setString(1, semester);
            getCourseList.setString(2, courseCode);
            resultSet = getCourseList.executeQuery();

            while (resultSet.next()) {
                count = resultSet.getInt(1);
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return count;
    }

    public static void dropCourse(String semester, String courseCode) {
        connection = DBConnection.getConnection();
        try {
            getCourseList = connection.prepareStatement("delete from app.course where semester = ? and coursecode = ?");
            getCourseList.setString(1, semester);
            getCourseList.setString(2, courseCode);
            getCourseList.executeUpdate();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

}
