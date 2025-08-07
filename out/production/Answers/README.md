# Adom Logistics Management System

## Project Structure

This project has been refactored to follow object-oriented design principles by separating each class into its own file and implementing custom data structures instead of using Java's built-in collections.

### Main Files

- **AdomLogisticsSystem.java** - Main class containing the application logic and user interface
- **Vehicle.java** - Vehicle class with properties and methods
- **Driver.java** - Driver class for managing driver information
- **Delivery.java** - Delivery class for tracking shipments
- **MaintenanceRecord.java** - Class for storing vehicle maintenance records

### Custom Data Structures

Instead of using Java's built-in collections, this project implements custom data structures:

- **CustomHashMap.java** - Custom implementation of a hash map using chaining for collision resolution
- **CustomArrayList.java** - Dynamic array implementation with resizing capability
- **CustomQueue.java** - Queue implementation using linked list nodes
- **CustomLinkedList.java** - Doubly-linked list implementation
- **CustomPriorityQueue.java** - Priority queue implementation using a binary heap

### Key Features

1. **Vehicle Management**: Add, remove, and search vehicles using binary search on sorted data
2. **Driver Management**: Manage driver assignments using a custom queue data structure
3. **Delivery Tracking**: Track deliveries using a custom linked list
4. **Maintenance Scheduler**: Prioritize vehicle maintenance using a custom priority queue
5. **Fuel Efficiency Reports**: Generate reports with custom sorting algorithms
6. **File I/O**: Persistent data storage with **immediate saving** - data is saved to files immediately after each operation
7. **Auto-Save**: All add/remove/modify operations automatically save to their respective .txt files

### Data Structures Used

- **HashMap**: For storing vehicles with registration number as key
- **Queue**: For managing available drivers (FIFO)
- **LinkedList**: For managing pending deliveries
- **PriorityQueue**: For scheduling vehicle maintenance based on mileage
- **ArrayList**: For sorting and searching operations

### Algorithms Implemented

- **Binary Search**: For vehicle lookup
- **Insertion Sort**: For sorting vehicles by fuel efficiency
- **Heap Operations**: For priority queue maintenance scheduling

### Compilation and Execution

To compile the project:
```bash
javac *.java
```

To run the program:
```bash
java AdomLogisticsSystem
```

### Notes

- All data structures are implemented from scratch without using Java's Collections Framework
- The code is compatible with Java 8 and higher versions
- **Data is automatically saved immediately** after each operation (add, remove, modify)
- Data files: vehicles.txt, drivers.txt, deliveries.txt, maintenance.txt
- No need to exit the program to save changes - they are persisted instantly
