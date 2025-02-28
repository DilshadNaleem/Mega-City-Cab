
package Admin;


public class VehicleUniqueIdGenerator {
         public static String generateUniqueId(String lastUniqueId) {
        if (lastUniqueId != null) {
            int lastNumber = Integer.parseInt(lastUniqueId.substring(8));
            return "VEHTYPE_" + String.format("%02d", lastNumber + 1);
        }
        return "VEHTYPE_01";
    }
}
