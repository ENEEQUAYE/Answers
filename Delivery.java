import algorithms.CustomArrayList;
import algorithms.CustomHashMap;
public class Delivery {
    private String packageID;
    private String origin;
    private String destination;
    private String assignedVehicleID;
    private String assignedDriverID;
    private String eta;
    private String status;

    public Delivery(String packageID, String origin, String destination, 
                   String vehicleID, String driverID, String eta, String status) {
        this.packageID = packageID;
        this.origin = origin;
        this.destination = destination;
        this.assignedVehicleID = vehicleID;
        this.assignedDriverID = driverID;
        this.eta = eta;
        this.status = status;
    }
    
    // Getters
    public String getPackageID() { 
        return packageID; 
    }
    
    public String getOrigin() { 
        return origin; 
    }
    
    public String getDestination() { 
        return destination; 
    }
    
    public String getAssignedVehicleID() { 
        return assignedVehicleID; 
    }
    
    public String getAssignedDriverID() { 
        return assignedDriverID; 
    }
    
    public String getEta() { 
        return eta; 
    }
    
    public String getStatus() { 
        return status; 
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public void setEta(String eta) {
        this.eta = eta;
    }
    
    @Override
    public String toString() {
        return "Delivery{" + "packageID='" + packageID + '\'' + 
               ", origin='" + origin + '\'' + ", destination='" + destination + '\'' + 
               ", status='" + status + '\'' + '}';
    }
}
