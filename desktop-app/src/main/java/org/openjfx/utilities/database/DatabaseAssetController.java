package org.openjfx.utilities.database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.openjfx.ledicom.entities.Asset;
import org.openjfx.ledicom.entities.AssetType;
import org.openjfx.ledicom.entities.Facility;

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

    public static ObservableList<Asset> getAllAssets() throws SQLException {
        String sql = "select name, id_asset, number, split_part(number, '-', 2) as cnt from asset order by id_facility, id_asset_type, cnt";

        ObservableList<Asset> observableList = FXCollections.observableArrayList();

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                observableList.add(new Asset(rs.getInt("id_asset"), rs.getString("name"), rs.getString("number")));
            }
            rs.close();
            return observableList;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            showMessageDialog(null, e.getMessage());
            throw e;
        }
    }

    public static ObservableList<Asset> getAssets(Facility facility, AssetType assetType) throws SQLException {
        String sql;

        if (facility == null && assetType == null)
            sql = "select name, id_asset, number, split_part(number, '-', 2) as cnt, id_facility, id_asset_type from asset order by id_facility, id_asset_type, cnt";
        else if(assetType == null)
            sql = "select name, id_asset, number, split_part(number, '-', 2) as cnt, id_facility, id_asset_type from asset where id_facility = " + facility.getId() + " order by id_facility, id_asset_type, cnt";
        else if(facility == null)
            sql = "select name, id_asset, number, split_part(number, '-', 2) as cnt, id_facility, id_asset_type from asset where id_asset_type = " + assetType.getId() + " order by id_facility, id_asset_type, cnt";
        else
            sql = "select name, id_asset, number, split_part(number, '-', 2) as cnt, id_facility, id_asset_type from asset where id_asset_type = " + assetType.getId() + " and id_facility = " + facility.getId() + " order by id_facility, id_asset_type, cnt";

        ObservableList<Asset> observableList = FXCollections.observableArrayList();

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                observableList.add(new Asset(rs.getInt("id_asset"), rs.getString("name"), rs.getString("number")));
            }
            rs.close();
            return observableList;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            showMessageDialog(null, e.getMessage());
            throw e;
        }
    }

    public static void deleteAsset(int id) throws SQLException {
        String sql = "delete from asset where id_asset = " + id;

        psExecute(sql);
    }
}
