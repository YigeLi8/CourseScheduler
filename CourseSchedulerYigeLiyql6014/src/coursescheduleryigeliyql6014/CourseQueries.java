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
    private static PreparedStatement getAllCourseCodes;
    private static PreparedStatement getAllCourses;
    private static PreparedStatement getCourseSeats;
    private static PreparedStatement addCourse;
    private static ResultSet resultSet;

    public static ArrayList<CourseEntry> getAllCourses(String name) {
        connection = DBConnection.getConnection();
        ArrayList<CourseEntry> courses = new ArrayList<CourseEntry>();

        try {
            getAllCourses = connection.prepareStatement("select * from app.course where semester = ? ");
            getAllCourses.setString(1, name);
            resultSet = getAllCourses.executeQuery();

            while (resultSet.next()) {
                courses.add(new CourseEntry(
                        resultSet.getString("semester"),
                        resultSet.getString("coursecode"),
                        resultSet.getString("description"),
                        resultSet.getInt("seats")
                ));
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return courses;
    }

    public static void addCourse(CourseEntry course) {
        connection = DBConnection.getConnection();

        try {
            addCourse = connection.prepareStatement("insert into app.course (semester, coursecode, description, seats) values (?,?,?,?)");
            addCourse.setString(1, course.Semester);
            addCourse.setString(2, course.CourseCode);
            addCourse.setString(3, course.Description);
            addCourse.setInt(4, course.seats);
            addCourse.executeUpdate();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();

        }

    }

    public static ArrayList<String> getAllCourseCodes(String semester) {
        connection = DBConnection.getConnection();
        ArrayList<String> courseCodes = new ArrayList<String>();

        try {
            getAllCourseCodes = connection.prepareStatement("select coursecode from app.course where semester = ?");
            getAllCourseCodes.setString(1, semester);
            resultSet = getAllCourseCodes.executeQuery();

            while (resultSet.next()) {
                courseCodes.add(resultSet.getString("coursecode"));
            }

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

        return courseCodes;
    }

    public static int getCourseSeats(String semester, String courseCode) {
        connection = DBConnection.getConnection();
        int seats = 0;
        try {
            getCourseSeats = connection.prepareStatement("select seats from app.course where semester = ? and coursecode = ?");
            getCourseSeats.setString(1, semester);
            getCourseSeats.setString(2, courseCode);
            resultSet = getCourseSeats.executeQuery();
            while (resultSet.next()) {
                seats = resultSet.getInt("seats");
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

        return seats;
    }

}
