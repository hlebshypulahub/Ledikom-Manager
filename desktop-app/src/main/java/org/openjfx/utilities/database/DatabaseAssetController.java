package org.openjfx.utilities.database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.openjfx.ledicom.entities.Asset;
import org.openjfx.ledicom.entities.AssetType;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static javax.swing.JOptionPane.showMessageDialog;

public class DatabaseAssetController extends DatabaseController {
    private DatabaseAssetController() {

    }

    public static ObservableList<AssetType> addNewType(String text) throws SQLException {
        String sql = "insert into asset_type (type) values ('" + text + "');";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.execute();
            return getAssetTypes();
        } catch (SQLException e) {
            showMessageDialog(null, e.getMessage());
            System.out.println(e.getMessage());
            throw e;
        }
    }

    public static ObservableList<AssetType> getAssetTypes() throws SQLException {
        ObservableList<AssetType> observableList = FXCollections.observableArrayList();

        String sql = "select * from asset_type;";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                observableList.add(new AssetType(rs.getString("type"), rs.getInt("id_asset_type")));
            }
            rs.close();
            return observableList;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            showMessageDialog(null, e.getMessage());
            throw e;
        }
    }

    public static Asset addAsset(Asset asset) throws SQLException {
        String sql = "insert into asset (id_asset_type, id_facility, name) values (" + asset.getAssetType().getId() +
                ", " + asset.getFacility().getId() + ", '" + asset.getName() + "') returning number;";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                asset.setNumber(rs.getString("number"));
            }
            rs.close();
            return asset;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            showMessageDialog(null, e.getMessage());
            throw e;
        }

    }
}
