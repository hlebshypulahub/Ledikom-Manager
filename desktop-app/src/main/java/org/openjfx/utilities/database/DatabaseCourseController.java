package org.openjfx.utilities.database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.openjfx.ledicom.entities.Course;
import org.openjfx.ledicom.entities.EmployeeCourseData;
import org.openjfx.utilities.Global;
import org.openjfx.utilities.comparators.CourseComparator;
import org.openjfx.utilities.converters.SqlDateStringConverter;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static javax.swing.JOptionPane.showMessageDialog;

public class DatabaseCourseController extends DatabaseController {

    public static int getRequiredCourseHours(String position) {
        String sql = "select * from employee_course_hours('" + position + "') as hours;";

        int hours = 0;

        try (
//                Connection conn = connect();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                hours = rs.getInt("hours");
            }
            rs.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            showMessageDialog(null, e.getMessage());
        }

        return hours;
    }

    public static void addCourse(Course course) throws SQLException {
        String sql = "insert into course (id_employee, name, description, hours, start_date, end_date) " +
                "values (" + Global.getEmployee().getId() + ", '" + course.getName() + "', '" + course.getDescription() + "', "
                + course.getHours() + ", '"
                + SqlDateStringConverter.stringToSqlDate(course.getStartDate()) + "', '"
                + SqlDateStringConverter.stringToSqlDate(course.getEndDate()) + "');";
        psExecute(sql);
    }

    public static ObservableList<Course> getEmployeeCourses() {
        String sql = "select * from course where id_employee = " + Global.getEmployee().getId() + ";";

        ObservableList<Course> courseList = FXCollections.observableArrayList();

        try (
//                Connection conn = connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Course course = new Course();
                course.setName(rs.getString("name"));
                course.setDescription(rs.getString("description"));
                course.setHours(rs.getInt("hours"));
                course.setStartDate(SqlDateStringConverter.sqlDateToString(rs.getDate("start_date")));
                course.setEndDate(SqlDateStringConverter.sqlDateToString(rs.getDate("end_date")));
                courseList.add(course);
            }
            courseList.sort(new CourseComparator());
            return courseList;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            showMessageDialog(null, e.getMessage());
            return null;
        }
    }

    public static ObservableList<EmployeeCourseData> getEmployeesCourses() {
        String sql = "select * from get_employees_courses();";

        ObservableList<EmployeeCourseData> list = FXCollections.observableArrayList();

        try (
//                Connection conn = connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                EmployeeCourseData ecd = new EmployeeCourseData();
                ecd.setEmployeeId(rs.getInt("id_employee"));
                ecd.setEmployeeName(rs.getString("employee_name"));
                ecd.setPosition(rs.getString("pos"));
                ecd.setEdu(rs.getString("edu"));
                ecd.setCategory(rs.getString("category"));
                ecd.setCourseName(rs.getString("course_name"));
                ecd.setCourseData(rs.getString("course_data"));
                ecd.setNextCourseData(rs.getString("next_course_data"));
                list.add(ecd);
                if(list.size() > 1) {
                    if(list.get(list.size()-1).getEmployeeId() == list.get(list.size()-2).getEmployeeId()) {
                        list.get(list.size()-1).setEmployeeName("");
                        list.get(list.size()-1).setPosition("");
                        list.get(list.size()-1).setEdu("");
                        list.get(list.size()-1).setCategory("");
                        list.get(list.size()-1).setNextCourseData("");
                    }
                }
            }
            return list;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            showMessageDialog(null, e.getMessage());
            return null;
        }
    }

}
