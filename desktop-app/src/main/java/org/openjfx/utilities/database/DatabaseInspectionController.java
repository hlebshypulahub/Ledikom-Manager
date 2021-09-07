package org.openjfx.utilities.database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.openjfx.ledicom.entities.inspection.CheckupType;
import org.openjfx.ledicom.entities.inspection.Inspection;
import org.openjfx.ledicom.entities.inspection.Question;
import org.openjfx.utilities.converters.SqlDateStringConverter;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static javax.swing.JOptionPane.showMessageDialog;

public class DatabaseInspectionController extends DatabaseController {

    private DatabaseInspectionController() {

    }

    public static ObservableList<CheckupType> CheckupTypes() {
        String sql = "select * from checkup_type;";

        ObservableList<CheckupType> observableList = FXCollections.observableArrayList();

        try (
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                CheckupType checkupType = new CheckupType();
                checkupType.setId(rs.getInt("id_checkup_type"));
                checkupType.setTypeName(rs.getString("type_name"));
                observableList.add(checkupType);
            }
            rs.close();
            return observableList;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            showMessageDialog(null, e.getMessage());
            return null;
        }
    }

    public static ObservableList<Question> CheckupQuestions() {
        String sql = "select cq.*, type_name from checkup_question cq join checkup_type on cq.id_checkup_type = checkup_type.id_checkup_type;";

        ObservableList<Question> observableList = FXCollections.observableArrayList();

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Question question = new Question();
                question.setId(rs.getInt("id_checkup_question"));
                question.setQuestion(rs.getString("question"));
                question.setCheckupType(new CheckupType(rs.getInt("id_checkup_type"), rs.getString("type_name")));
                observableList.add(question);
            }
            rs.close();
            return observableList;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            showMessageDialog(null, e.getMessage());
            return null;
        }
    }

    public static ObservableList<String> getCheckupAnswers() throws SQLException {
        String sql = "select t.typname, e.enumlabel from pg_type t, pg_enum e where t.oid = e.enumtypid and typname = 'checkup_answer';";
        return DatabaseEnumsController.getEnums(sql);
    }

    public static boolean addInspection(Inspection inspection) {
        String sql = "insert into inspection (id_employee, id_facility, date, note) " +
                "values (" + inspection.getIdEmployee() + ", " + inspection.getIdFacility()
                + ", ?, '" + inspection.getNote() + "') returning id_inspection;";

        int inspectionId = 0;
        int checkupId = 0;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDate(1, SqlDateStringConverter.stringToSqlDate(inspection.getDate()));
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                inspectionId = rs.getInt("id_inspection");
            }

            for (int i = 0; i < inspection.getCheckupList().size(); i++) {
                sql = "insert into checkup (id_inspection, id_checkup_question, answer, note) " +
                        "values (" + inspectionId + ", " + inspection.getCheckupList().get(i).getQuestion().getId() + ", '"
                        + inspection.getCheckupList().get(i).getAnswer() + "', '" + inspection.getCheckupList().get(i).getNote() + "') returning id_checkup;";

                try (PreparedStatement ps2 = conn.prepareStatement(sql)) {
                    ResultSet rs2 = ps2.executeQuery();
                    while (rs2.next()) {
                        checkupId = rs2.getInt("id_checkup");
                    }

                    if (inspection.getCheckupList().get(i).getViolation() != null) {
                        sql = "insert into violation (id_employee, id_checkup, description, action_plan, correction_term, correction_date) " +
                                "values (" + inspection.getCheckupList().get(i).getViolation().getIdEmployee() + ", "
                                + checkupId + ", '" + inspection.getCheckupList().get(i).getViolation().getDescription() + "', '"
                                + inspection.getCheckupList().get(i).getViolation().getActionPlan() + "', ?, ?);";

                        try (PreparedStatement ps3 = conn.prepareStatement(sql)) {
                            ps3.setDate(1, SqlDateStringConverter.stringToSqlDate(inspection.getCheckupList().get(i).getViolation().getCorrectionTerm()));
                            ps3.setDate(2, SqlDateStringConverter.stringToSqlDate(inspection.getCheckupList().get(i).getViolation().getCorrectionDate()));
                            ps3.execute();
                        } catch (SQLException e) {
                            e.printStackTrace();
                            showMessageDialog(null, e.getMessage());
                            return false;
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    showMessageDialog(null, e.getMessage());
                    return false;
                }
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            showMessageDialog(null, e.getMessage());
            return false;
        }

        return true;
    }
}















