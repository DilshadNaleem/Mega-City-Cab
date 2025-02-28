package Customer.CService;

public class UniqueIdGenerator {
    public String generateUniqueId(DatabaseUtility dbUtility) {
        String uniqueId = "CUS_01";
        try {
            String lastUniqueId = dbUtility.getLastUniqueId();
            if (lastUniqueId != null) {
                int lastNumber = Integer.parseInt(lastUniqueId.substring(4));
                uniqueId = "CUS_" + String.format("%02d", lastNumber + 1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return uniqueId;
    }
}
