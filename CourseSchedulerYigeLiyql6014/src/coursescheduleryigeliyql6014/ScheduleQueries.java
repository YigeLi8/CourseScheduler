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
public class ScheduleQueries {

    private static Connection connection;
    private static PreparedStatement addScheduleEntry;
    private static PreparedStatement getScheduleByStudent;
    private static PreparedStatement getScheduledStudentCount;
    private static ResultSet resultSet;

    public static void addScheduleEntry(ScheduleEntry entry) {
        connection = DBConnection.getConnection();

        try {
            addScheduleEntry = connection.prepareStatement("insert into app.schedule (semester , studentid, coursecode, status, timestamp) values (?, ?, ?, ?, ?)");
            addScheduleEntry.setString(1, entry.getSemester());
            addScheduleEntry.setString(2, entry.getStudentID());
            addScheduleEntry.setString(3, entry.getCourseCode());
            addScheduleEntry.setString(4, entry.getStatus());
            addScheduleEntry.setTimestamp(5, entry.getTimeStamp());
            addScheduleEntry.executeUpdate();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

    }

    public static ArrayList<ScheduleEntry> getScheduleByStudent(String semester, String studentID) {

        connection = DBConnection.getConnection();
        ArrayList<ScheduleEntry> schedule = new ArrayList<>();

        try {
            getScheduleByStudent = connection.prepareStatement("select * from app.schedule where semester = ? and studentid = ?");
            getScheduleByStudent.setString(1, semester);
            getScheduleByStudent.setString(2, studentID);
            resultSet = getScheduleByStudent.executeQuery();

            while (resultSet.next()) {
                schedule.add(new ScheduleEntry(
                        resultSet.getString("semester"), resultSet.getString("coursecode"),
                        resultSet.getString("studentid"), resultSet.getString("status"),
                        resultSet.getTimestamp("timestamp")));

            }

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

        return schedule;
    }

    public static int getScheduledStudentCount(String semester, String courseCode) {
        connection = DBConnection.getConnection();
        String studentNum = "";
        try {
            getScheduledStudentCount = connection.prepareStatement("select count(studentID) from app.schedule where semester = ? and courseCode = ?");
            getScheduledStudentCount.setString(1, semester);
            getScheduledStudentCount.setString(2, courseCode);
            resultSet = getScheduledStudentCount.executeQuery();
            while (resultSet.next()) {
                studentNum = resultSet.getString(1);
            }

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();

        }

        return Integer.parseInt(studentNum);
    }
}
