import java.io.*;
import java.util.Scanner;
import algorithms.*;
import algorithms.CustomHashMap;

public class AdomLogisticsSystem {

    // Custom Data Structures
    private final CustomHashMap<String, Vehicle> vehicles = new CustomHashMap<>();
    private final CustomQueue<Driver> availableDrivers = new CustomQueue<>();
    private final CustomLinkedList<Delivery> pendingDeliveries = new CustomLinkedList<>();
    private final CustomPriorityQueue<Vehicle> maintenanceQueue = new CustomPriorityQueue<>(new java.util.Comparator<Vehicle>() {
    @Override
    public int compare(Vehicle v1, Vehicle v2) {
        // Priority based on mileage (higher mileage = higher priority)
        return Double.compare(v2.getMileage(), v1.getMileage());
    }
});
    
    // File Paths
    private static final String VEHICLES_FILE = "data/vehicles.txt";
    private static final String DRIVERS_FILE = "data/drivers.txt";
    private static final String DELIVERIES_FILE = "data/deliveries.txt";
    private static final String MAINTENANCE_FILE = "data/maintenance.txt";

    public static void main(String[] args) {
        AdomLogisticsSystem system = new AdomLogisticsSystem();
        system.loadData();
        system.run();
    }

    private void run() {
        try (Scanner scanner = new Scanner(System.in)) {
            int choice;
            do {
                System.out.println("\n--- Adom Logistics Management System ---");
                System.out.println("1. Vehicle Management");
                System.out.println("2. Driver Management");
                System.out.println("3. Delivery Management");
                System.out.println("4. Maintenance Scheduler");
                System.out.println("5. Fuel Efficiency Reports");
                System.out.println("6. Exit");
                System.out.print("Enter your choice: ");
                choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch (choice) {
                    case 1:
                        vehicleManagementMenu(scanner);
                        break;
                    case 2:
                        driverManagementMenu(scanner);
                        break;
                    case 3:
                        deliveryManagementMenu(scanner);
                        break;
                    case 4:
                        maintenanceSchedulerMenu(scanner);
                        break;
                    case 5:
                        fuelEfficiencyReportsMenu(scanner);
                        break;
                    case 6:
                        saveData();
                        System.out.println("Exiting system. Data saved.");
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                        break;
                }
            } while (choice != 6);
        }
    }

    // --- Core Functionality Methods ---

    // Vehicle Management
    private void vehicleManagementMenu(Scanner scanner) {
        int choice;
        do {
            System.out.println("\n--- Vehicle Management ---");
            System.out.println("1. Add Vehicle");
            System.out.println("2. Remove Vehicle");
            System.out.println("3. Search Vehicle");
            System.out.println("4. Back to Main Menu");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    addVehicle(scanner);
                    break;
                case 2:
                    removeVehicle(scanner);
                    break;
                case 3:
                    searchVehicle(scanner);
                    break;
                case 4:
                    // Back to Main Menu
                    break;
                default:
                    System.out.println("Invalid choice.");
                    break;
            }
        } while (choice != 4);
    }
    
    private void addVehicle(Scanner scanner) {
        System.out.print("Enter Registration Number: ");
        String regNum = scanner.nextLine();
        System.out.print("Enter Vehicle Type (Truck/Van): ");
        String type = scanner.nextLine();
        System.out.print("Enter Mileage: ");
        double mileage = scanner.nextDouble();
        scanner.nextLine();
        System.out.print("Enter Fuel Usage (L/100km): ");
        double fuelUsage = scanner.nextDouble();
        scanner.nextLine();
        
        vehicles.put(regNum, new Vehicle(regNum, type, mileage, fuelUsage));
        System.out.println("Vehicle added successfully.");
        updateMaintenanceQueue();
        
        // Save data immediately
        try {
            saveVehicles();
            System.out.println("Vehicle data saved to file.");
        } catch (IOException e) {
            System.err.println("Error saving vehicle data: " + e.getMessage());
        }
    }

    private void removeVehicle(Scanner scanner) {
        System.out.print("Enter Registration Number of vehicle to remove: ");
        String regNum = scanner.nextLine();
        if (vehicles.containsKey(regNum)) {
            vehicles.remove(regNum);
            System.out.println("Vehicle removed successfully.");
            updateMaintenanceQueue();
            
            // Save data immediately
            try {
                saveVehicles();
                System.out.println("Vehicle data saved to file.");
            } catch (IOException e) {
                System.err.println("Error saving vehicle data: " + e.getMessage());
            }
        } else {
            System.out.println("Vehicle not found.");
        }
    }

    private void searchVehicle(Scanner scanner) {
        System.out.print("Enter Registration Number to search: ");
        String regNum = scanner.nextLine();
        CustomArrayList<String> sortedRegNums = vehicles.keySet();
        
        // Sort the registration numbers using custom sort
        sortedRegNums.sort((s1, s2) -> s1.compareTo(s2));
        
        int index = sortedRegNums.binarySearch(regNum, (s1, s2) -> s1.compareTo(s2));
        
        if (index >= 0) {
            String foundRegNum = sortedRegNums.get(index);
            Vehicle vehicle = vehicles.get(foundRegNum);
            System.out.println("Vehicle Found: " + vehicle);
        } else {
            System.out.println("Vehicle not found.");
        }
    }
    
    // Driver Management
    private void driverManagementMenu(Scanner scanner) {
        int choice;
        do {
            System.out.println("\n--- Driver Management ---");
            System.out.println("1. Add Driver");
            System.out.println("2. Assign Driver to Vehicle");
            System.out.println("3. Back to Main Menu");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    addDriver(scanner);
                    break;
                case 2:
                    assignDriver(scanner);
                    break;
                case 3:
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        } while (choice != 3);
    }

    private void addDriver(Scanner scanner) {
        System.out.print("Enter Driver ID: ");
        String driverID = scanner.nextLine();
        System.out.print("Enter Driver Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Driver Experience (years): ");
        int experience = scanner.nextInt();
        scanner.nextLine();

        Driver newDriver = new Driver(driverID, name, experience);
        availableDrivers.add(newDriver);
        System.out.println("Driver added to available pool.");
        
        // Save data immediately
        try {
            saveDrivers();
            System.out.println("Driver data saved to file.");
        } catch (IOException e) {
            System.err.println("Error saving driver data: " + e.getMessage());
        }
    }
    
    private void assignDriver(Scanner scanner) {
        if (availableDrivers.isEmpty()) {
            System.out.println("No drivers available for assignment.");
            return;
        }
        if (vehicles.isEmpty()) {
            System.out.println("No vehicles available for assignment.");
            return;
        }

        System.out.print("Enter Vehicle Registration Number: ");
        String regNum = scanner.nextLine();
        Vehicle vehicle = vehicles.get(regNum);
        
        if (vehicle != null) {
            Driver driver = availableDrivers.poll(); // Get a driver from the queue
            if (driver != null) {
                vehicle.setDriverID(driver.getDriverID());
                driver.setAvailable(false);
                System.out.println("Driver " + driver.getName() + " assigned to vehicle " + regNum);
                
                // Save data immediately
                try {
                    saveVehicles();
                    saveDrivers();
                    System.out.println("Assignment data saved to files.");
                } catch (IOException e) {
                    System.err.println("Error saving assignment data: " + e.getMessage());
                }
            } else {
                System.out.println("No available drivers.");
            }
        } else {
            System.out.println("Vehicle not found.");
        }
    }

    // Delivery Tracking
    private void deliveryManagementMenu(Scanner scanner) {
        int choice;
        do {
            System.out.println("\n--- Delivery Tracking ---");
            System.out.println("1. Add New Delivery");
            System.out.println("2. Process Next Delivery");
            System.out.println("3. Back to Main Menu");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    addNewDelivery(scanner);
                    break;
                case 2:
                    processNextDelivery();
                    break;
                case 3:
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        } while (choice != 3);
    }
    
    private void addNewDelivery(Scanner scanner) {
        if (availableDrivers.isEmpty()) {
            System.out.println("Cannot add delivery, no drivers available.");
            return;
        }
        
        System.out.print("Enter Package ID: ");
        String packageID = scanner.nextLine();
        System.out.print("Enter Origin: ");
        String origin = scanner.nextLine();
        System.out.print("Enter Destination: ");
        String destination = scanner.nextLine();
        
        // Simplified assignment for demonstration
        Driver assignedDriver = availableDrivers.poll();
        Vehicle assignedVehicle = null;
        
        // Find vehicle assigned to this driver
        CustomArrayList<Vehicle> vehicleList = vehicles.values();
        for (int i = 0; i < vehicleList.size(); i++) {
            Vehicle v = vehicleList.get(i);
            if (v.getDriverID() != null && v.getDriverID().equals(assignedDriver.getDriverID())) {
                assignedVehicle = v;
                break;
            }
        }
        
        if (assignedVehicle != null) {
            Delivery newDelivery = new Delivery(packageID, origin, destination, 
                                              assignedVehicle.getRegistrationNumber(), 
                                              assignedDriver.getDriverID(), "TBD", "Pending");
            pendingDeliveries.add(newDelivery);
            System.out.println("New delivery added and assigned to " + assignedDriver.getName());
            
            // Save data immediately
            try {
                saveDeliveries();
                System.out.println("Delivery data saved to file.");
            } catch (IOException e) {
                System.err.println("Error saving delivery data: " + e.getMessage());
            }
        } else {
            System.out.println("Error assigning vehicle/driver.");
            availableDrivers.add(assignedDriver); // Put driver back in the queue
        }
    }

    private void processNextDelivery() {
        if (!pendingDeliveries.isEmpty()) {
            Delivery delivery = pendingDeliveries.poll();
            System.out.println("Processing delivery: " + delivery.getPackageID());
            // Simulate delivery process...
            System.out.println("Delivery to " + delivery.getDestination() + " completed.");
            // Logic to update driver status to available
            
            // Save data immediately
            try {
                saveDeliveries();
                System.out.println("Delivery data updated in file.");
            } catch (IOException e) {
                System.err.println("Error saving delivery data: " + e.getMessage());
            }
        } else {
            System.out.println("No pending deliveries.");
        }
    }
    
    // Maintenance Scheduler
    private void maintenanceSchedulerMenu(Scanner scanner) {
        int choice;
        do {
            System.out.println("\n--- Maintenance Scheduler ---");
            System.out.println("1. View Next Vehicle for Maintenance");
            System.out.println("2. Record Maintenance for a Vehicle");
            System.out.println("3. Back to Main Menu");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    viewNextMaintenanceVehicle();
                    break;
                case 2:
                    recordMaintenance(scanner);
                    break;
                case 3:
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        } while (choice != 3);
    }
    
    private void updateMaintenanceQueue() {
        maintenanceQueue.clear();
        CustomArrayList<Vehicle> vehicleList = vehicles.values();
        for (int i = 0; i < vehicleList.size(); i++) {
            maintenanceQueue.add(vehicleList.get(i));
        }
    }

    private void viewNextMaintenanceVehicle() {
        if (maintenanceQueue.isEmpty()) {
            System.out.println("No vehicles in the maintenance queue.");
            return;
        }
        Vehicle nextVehicle = maintenanceQueue.peek();
        System.out.println("Next vehicle for maintenance: " + nextVehicle.getRegistrationNumber() + " (Mileage: " + nextVehicle.getMileage() + ")");
    }

    private void recordMaintenance(Scanner scanner) {
        System.out.print("Enter Registration Number of vehicle maintained: ");
        String regNum = scanner.nextLine();
        Vehicle vehicle = vehicles.get(regNum);
        
        if (vehicle != null) {
            System.out.print("Enter parts replaced (e.g., OilChange,TireReplacement): ");
            String partsStr = scanner.nextLine();
            CustomHashMap<String, Double> parts = new CustomHashMap<>();
            double totalCost = 0.0;
            String[] partArray = partsStr.split(",");
            for (String part : partArray) {
                System.out.print("Enter cost for " + part + ": ");
                double cost = scanner.nextDouble();
                parts.put(part.trim(), cost);
                totalCost += cost;
            }
            scanner.nextLine(); // Consume newline
            
            // Assuming current date for simplicity
            String date = "2025-08-04"; 
            MaintenanceRecord record = new MaintenanceRecord(regNum, date, parts, totalCost);
            vehicle.getMaintenanceHistory().put(date, record.toString());
            System.out.println("Maintenance recorded successfully for " + regNum);
            
            // Re-add to queue to adjust priority
            updateMaintenanceQueue();
            
            // Save data immediately
            try {
                saveMaintenance();
                System.out.println("Maintenance data saved to file.");
            } catch (IOException e) {
                System.err.println("Error saving maintenance data: " + e.getMessage());
            }
        } else {
            System.out.println("Vehicle not found.");
        }
    }
    
    // Fuel Efficiency Reports
    private void fuelEfficiencyReportsMenu(Scanner scanner) {
        int choice;
        do {
            System.out.println("\n--- Fuel Efficiency Reports ---");
            System.out.println("1. View Average Fuel Usage");
            System.out.println("2. Sort Vehicles by Fuel Performance");
            System.out.println("3. Back to Main Menu");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    calculateAverageFuelUsage();
                    break;
                case 2:
                    sortVehiclesByFuelPerformance();
                    break;
                case 3:
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        } while (choice != 3);
    }
    
    private void calculateAverageFuelUsage() {
        if (vehicles.isEmpty()) {
            System.out.println("No vehicles to report on.");
            return;
        }
        double totalFuel = 0;
        CustomArrayList<Vehicle> vehicleList = vehicles.values();
        for (int i = 0; i < vehicleList.size(); i++) {
            totalFuel += vehicleList.get(i).getFuelUsage();
        }
        double average = totalFuel / vehicles.size();
        System.out.println("Average Fuel Usage (L/100km): " + String.format("%.2f", average));
    }
    
    private void sortVehiclesByFuelPerformance() {
        CustomArrayList<Vehicle> vehicleList = vehicles.values();
        
        // Insertion Sort
        for (int i = 1; i < vehicleList.size(); i++) {
            Vehicle key = vehicleList.get(i);
            int j = i - 1;
            while (j >= 0 && vehicleList.get(j).getFuelUsage() > key.getFuelUsage()) {
                vehicleList.set(j + 1, vehicleList.get(j));
                j = j - 1;
            }
            vehicleList.set(j + 1, key);
        }
        
        System.out.println("Vehicles sorted by fuel usage (most efficient first):");
        for (int i = 0; i < vehicleList.size(); i++) {
            Vehicle v = vehicleList.get(i);
            System.out.println(v.getRegistrationNumber() + " - Fuel Usage: " + String.format("%.2f", v.getFuelUsage()));
        }
    }

    // File Handling
    private void loadData() {
        try {
            loadVehicles();
            loadDrivers();
            loadDeliveries();
            loadMaintenance();
        } catch (IOException e) {
            System.err.println("Error loading data: " + e.getMessage());
        }
    }

    private void saveData() {
        try {
            saveVehicles();
            saveDrivers();
            saveDeliveries();
            saveMaintenance();
        } catch (IOException e) {
            System.err.println("Error saving data: " + e.getMessage());
        }
    }
    
    // File I/O Implementation
    private void loadVehicles() throws IOException {
        File file = new File(VEHICLES_FILE);
        if (!file.exists()) return;
        
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] parts = line.split(",");
                if (parts.length >= 4) {
                    String regNum = parts[0].trim();
                    String type = parts[1].trim();
                    double mileage = Double.parseDouble(parts[2].trim());
                    double fuelUsage = Double.parseDouble(parts[3].trim());
                    Vehicle vehicle = new Vehicle(regNum, type, mileage, fuelUsage);
                    if (parts.length > 4 && !parts[4].trim().isEmpty()) {
                        vehicle.setDriverID(parts[4].trim());
                    }
                    vehicles.put(regNum, vehicle);
                }
            }
        }
        updateMaintenanceQueue();
    }
    
    private void saveVehicles() throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(VEHICLES_FILE))) {
            CustomArrayList<Vehicle> vehicleList = vehicles.values();
            for (int i = 0; i < vehicleList.size(); i++) {
                Vehicle vehicle = vehicleList.get(i);
                writer.println(vehicle.getRegistrationNumber() + "," + 
                             vehicle.getType() + "," + 
                             vehicle.getMileage() + "," + 
                             vehicle.getFuelUsage() + "," + 
                             (vehicle.getDriverID() != null ? vehicle.getDriverID() : ""));
            }
        }
    }
    
    private void loadDrivers() throws IOException {
        File file = new File(DRIVERS_FILE);
        if (!file.exists()) return;
        
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] parts = line.split(",");
                if (parts.length >= 4) {
                    String driverID = parts[0].trim();
                    String name = parts[1].trim();
                    int experience = Integer.parseInt(parts[2].trim());
                    boolean isAvailable = Boolean.parseBoolean(parts[3].trim());
                    Driver driver = new Driver(driverID, name, experience);
                    driver.setAvailable(isAvailable);
                    if (isAvailable) {
                        availableDrivers.add(driver);
                    }
                }
            }
        }
    }

    private void saveDrivers() throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(DRIVERS_FILE))) {
            // Save available drivers
            CustomArrayList<Driver> driverList = availableDrivers.toList();
            for (int i = 0; i < driverList.size(); i++) {
                Driver driver = driverList.get(i);
                writer.println(driver.getDriverID() + "," + 
                             driver.getName() + "," + 
                             driver.getExperience() + "," + 
                             driver.isAvailable());
            }
            // Save assigned drivers (those with vehicles)
            CustomArrayList<Vehicle> vehicleList = vehicles.values();
            for (int i = 0; i < vehicleList.size(); i++) {
                Vehicle vehicle = vehicleList.get(i);
                if (vehicle.getDriverID() != null && !vehicle.getDriverID().isEmpty()) {
                    // Find if this driver is already saved
                    boolean alreadySaved = false;
                    for (int j = 0; j < driverList.size(); j++) {
                        Driver driver = driverList.get(j);
                        if (driver.getDriverID().equals(vehicle.getDriverID())) {
                            alreadySaved = true;
                            break;
                        }
                    }
                    if (!alreadySaved) {
                        writer.println(vehicle.getDriverID() + ",AssignedDriver,5,false");
                    }
                }
            }
        }
    }

    private void loadDeliveries() throws IOException {
        File file = new File(DELIVERIES_FILE);
        if (!file.exists()) return;
        
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] parts = line.split(",");
                if (parts.length >= 7) {
                    String packageID = parts[0].trim();
                    String origin = parts[1].trim();
                    String destination = parts[2].trim();
                    String vehicleID = parts[3].trim();
                    String driverID = parts[4].trim();
                    String eta = parts[5].trim();
                    String status = parts[6].trim();
                    Delivery delivery = new Delivery(packageID, origin, destination, vehicleID, driverID, eta, status);
                    if (status.equals("Pending")) {
                        pendingDeliveries.add(delivery);
                    }
                }
            }
        }
    }

    private void saveDeliveries() throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(DELIVERIES_FILE))) {
            CustomArrayList<Delivery> deliveryList = pendingDeliveries.toArrayList();
            for (int i = 0; i < deliveryList.size(); i++) {
                Delivery delivery = deliveryList.get(i);
                writer.println(delivery.getPackageID() + "," + 
                             delivery.getOrigin() + "," + 
                             delivery.getDestination() + "," + 
                             delivery.getAssignedVehicleID() + "," + 
                             delivery.getAssignedDriverID() + "," + 
                             delivery.getEta() + "," + 
                             delivery.getStatus());
            }
        }
    }

    private void loadMaintenance() throws IOException {
        File file = new File(MAINTENANCE_FILE);
        if (!file.exists()) return;
        
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] parts = line.split(",");
                if (parts.length >= 3) {
                    String vehicleID = parts[0].trim();
                    String date = parts[1].trim();
                    String maintenanceDetails = parts[2].trim();
                    Vehicle vehicle = vehicles.get(vehicleID);
                    if (vehicle != null) {
                        vehicle.getMaintenanceHistory().put(date, maintenanceDetails);
                    }
                }
            }
        }
    }

    private void saveMaintenance() throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(MAINTENANCE_FILE))) {
            CustomArrayList<Vehicle> vehicleList = vehicles.values();
            for (int i = 0; i < vehicleList.size(); i++) {
                Vehicle vehicle = vehicleList.get(i);
                CustomArrayList<CustomHashMap.Entry<String, String>> entries = vehicle.getMaintenanceHistory().entrySet();
                for (int j = 0; j < entries.size(); j++) {
                    CustomHashMap.Entry<String, String> entry = entries.get(j);
                    writer.println(vehicle.getRegistrationNumber() + "," + 
                                 entry.getKey() + "," + 
                                 entry.getValue());
                }
            }
        }
    }

    public CustomPriorityQueue<Vehicle> getMaintenanceQueue() {
        return maintenanceQueue;
    }
}