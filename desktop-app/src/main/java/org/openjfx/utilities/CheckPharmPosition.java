package org.openjfx.utilities;

public final class CheckPharmPosition {
    private CheckPharmPosition() {

    }

    public static boolean isPharm() {
        return !(Global.getEmployee().getPosition() == null || !(Global.getEmployee().getPosition().contains("Провизор")
                || (Global.getEmployee().getPosition().contains("провизор") || (Global.getEmployee().getPosition().contains("Фармацевт")
                || (Global.getEmployee().getPosition().contains("фармацевт") || (Global.getEmployee().getPosition().contains("Директор")
                || Global.getEmployee().getPosition().contains("директор")))))));
    }
}
