public class MaintenanceRecord {
    private String vehicleID;
    private String date;
    private CustomHashMap<String, Double> partsReplaced;
    private double cost;
    
    public MaintenanceRecord(String vehicleID, String date, 
                           CustomHashMap<String, Double> parts, double cost) {
        this.vehicleID = vehicleID;
        this.date = date;
        this.partsReplaced = parts;
        this.cost = cost;
    }
    
    // Getters
    public String getVehicleID() {
        return vehicleID;
    }
    
    public String getDate() {
        return date;
    }
    
    public CustomHashMap<String, Double> getPartsReplaced() {
        return partsReplaced;
    }
    
    public double getCost() {
        return cost;
    }
    
    @Override
    public String toString() {
        return "Date: " + date + ", Parts: " + partsReplaced + ", Cost: " + cost;
    }
}
