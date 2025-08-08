import algorithms.CustomHashMap;
public class Vehicle {
    private String registrationNumber;
    private String type;
    private double mileage;
    private double fuelUsage;
    private String driverID;
    private CustomHashMap<String, String> maintenanceHistory;

    public Vehicle(String regNum, String type, double mileage, double fuelUsage) {
        this.registrationNumber = regNum;
        this.type = type;
        this.mileage = mileage;
        this.fuelUsage = fuelUsage;
        this.maintenanceHistory = new CustomHashMap<>();
    }
    
    // Getters and setters
    public String getRegistrationNumber() { 
        return registrationNumber; 
    }
    
    public String getType() { 
        return type; 
    }
    
    public double getMileage() { 
        return mileage; 
    }
    
    public void setMileage(double mileage) {
        this.mileage = mileage;
    }
    
    public double getFuelUsage() { 
        return fuelUsage; 
    }
    
    public String getDriverID() { 
        return driverID; 
    }
    
    public void setDriverID(String driverID) { 
        this.driverID = driverID; 
    }
    
    public CustomHashMap<String, String> getMaintenanceHistory() {
        return maintenanceHistory;
    }
    
    @Override
    public String toString() {
        return "Vehicle{" + "regNum='" + registrationNumber + '\'' + ", type='" + type + '\'' + '}';
    }
}
