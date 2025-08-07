public class Driver {
    private String driverID;
    private String name;
    private int experience;
    private boolean isAvailable;

    public Driver(String driverID, String name, int experience) {
        this.driverID = driverID;
        this.name = name;
        this.experience = experience;
        this.isAvailable = true;
    }
    
    // Getters and setters
    public String getDriverID() { 
        return driverID; 
    }
    
    public String getName() { 
        return name; 
    }
    
    public int getExperience() { 
        return experience; 
    }
    
    public boolean isAvailable() { 
        return isAvailable; 
    }
    
    public void setAvailable(boolean available) { 
        isAvailable = available; 
    }
    
    @Override
    public String toString() {
        return "Driver{" + "ID='" + driverID + '\'' + ", name='" + name + '\'' + 
               ", experience=" + experience + ", available=" + isAvailable + '}';
    }
}
