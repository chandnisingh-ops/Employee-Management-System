import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

// ================= EMPLOYEE CLASS =================
class Employee implements Serializable {
    private String id;
    private String name;
    private String department;
    private String position;
    private double salary;
    private Date joinDate;

    public Employee(String id, String name, String department, String position, double salary) {
        this.id = id;
        this.name = name;
        this.department = department;
        this.position = position;
        this.salary = salary;
        this.joinDate = new Date();
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getDepartment() { return department; }
    public String getPosition() { return position; }
    public double getSalary() { return salary; }
    public Date getJoinDate() { return joinDate; }

    public void setName(String name) { this.name = name; }
    public void setDepartment(String department) { this.department = department; }
    public void setPosition(String position) { this.position = position; }
    public void setSalary(double salary) { this.salary = salary; }
}

// ================= SYSTEM CLASS =================
class EmployeeManagementSystem {
    private ArrayList<Employee> employees = new ArrayList<>();
    private HashMap<String, Employee> employeeMap = new HashMap<>();
    private static final String DATA_FILE = "employees.dat";
    private Scanner scanner = new Scanner(System.in);

    public EmployeeManagementSystem() {
        loadEmployeesFromFile();
    }

    // ================= MENU =================
    public void start() {
        while (true) {
            System.out.println("\n=== EMPLOYEE MANAGEMENT SYSTEM ===");
            System.out.println("1. Add New Employee");
            System.out.println("2. View All Employees");
            System.out.println("3. Search Employee");
            System.out.println("4. Update Employee");
            System.out.println("5. Delete Employee");
            System.out.println("6. Generate Reports");
            System.out.println("7. Save to File");
            System.out.println("8. Load from File");
            System.out.println("9. Exit");
            System.out.print("Enter choice: ");

            int choice = getValidInt(1, 9);
            scanner.nextLine();

            switch (choice) {
                case 1: addEmployee(); break;
                case 2: displayAllEmployees(); break;
                case 3: searchEmployee(); break;
                case 4: updateEmployee(); break;
                case 5: deleteEmployee(); break;
                case 6: generateSalaryReport(); break;
                case 7:
                    saveEmployeesToFile();
                    System.out.println("Data saved successfully.");
                    break;
                case 8:
                    loadEmployeesFromFile();
                    System.out.println("Data loaded successfully.");
                    break;
                case 9:
                    System.out.println("Exiting...");
                    return;
            }
        }
    }

    // ================= ADD =================
    private void addEmployee() {
        System.out.println("\n=== ADD EMPLOYEE ===");

        String id = getUniqueId();
        String name = getNonEmptyInput("Enter Name: ");
        String dept = getNonEmptyInput("Enter Department: ");
        String pos = getNonEmptyInput("Enter Position: ");
        double salary = getValidSalary();

        Employee emp = new Employee(id, name, dept, pos, salary);
        employees.add(emp);
        employeeMap.put(id, emp);

        System.out.println("Employee added successfully.");
    }

    private String getUniqueId() {
        while (true) {
            System.out.print("Enter Employee ID: ");
            String id = scanner.nextLine().trim();

            if (!id.isEmpty() && !employeeMap.containsKey(id)) return id;
            System.out.println("Invalid or duplicate ID. Try again.");
        }
    }

    // ================= VIEW =================
    private void displayAllEmployees() {
        System.out.println("\n=== ALL EMPLOYEES ===");

        if (employees.isEmpty()) {
            System.out.println("No employees found.");
            return;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        System.out.printf("%-8s %-15s %-15s %-15s %-12s %-12s\n",
                "ID", "Name", "Dept", "Position", "Salary", "Join Date");
        System.out.println("-".repeat(80));

        for (Employee e : employees) {
            System.out.printf("%-8s %-15s %-15s %-15s ₹%-10.2f %-12s\n",
                    e.getId(), e.getName(), e.getDepartment(),
                    e.getPosition(), e.getSalary(),
                    sdf.format(e.getJoinDate()));
        }
    }

    // ================= SEARCH =================
    private void searchEmployee() {
        System.out.println("\n1. By ID\n2. By Name\n3. By Department");
        System.out.print("Choice: ");

        int choice = getValidInt(1, 3);
        scanner.nextLine();

        switch (choice) {
            case 1:
                System.out.print("Enter ID: ");
                Employee e = employeeMap.get(scanner.nextLine());
                System.out.println(e != null ? e.getName() : "Employee not found.");
                break;

            case 2:
                System.out.print("Enter Name: ");
                searchByField(scanner.nextLine().toLowerCase(), "name");
                break;

            case 3:
                System.out.print("Enter Department: ");
                searchByField(scanner.nextLine().toLowerCase(), "department");
                break;
        }
    }

    private void searchByField(String keyword, String type) {
        boolean found = false;

        for (Employee e : employees) {
            if ((type.equals("name") && e.getName().toLowerCase().contains(keyword)) ||
                (type.equals("department") && e.getDepartment().toLowerCase().contains(keyword))) {

                System.out.println(e.getId() + " - " + e.getName());
                found = true;
            }
        }

        if (!found) System.out.println("No results found.");
    }

    // ================= UPDATE =================
    private void updateEmployee() {
        System.out.print("Enter Employee ID: ");
        String id = scanner.nextLine();

        Employee e = employeeMap.get(id);
        if (e == null) {
            System.out.println("Employee not found.");
            return;
        }

        e.setName(getNonEmptyInput("New Name: "));
        e.setDepartment(getNonEmptyInput("New Department: "));
        e.setPosition(getNonEmptyInput("New Position: "));
        e.setSalary(getValidSalary());

        System.out.println("Employee updated.");
    }

    // ================= DELETE =================
    private void deleteEmployee() {
        System.out.print("Enter Employee ID: ");
        String id = scanner.nextLine();

        Employee e = employeeMap.remove(id);
        if (e != null) {
            employees.remove(e);
            System.out.println("Employee deleted.");
        } else {
            System.out.println("Employee not found.");
        }
    }

    // ================= REPORT =================
    private void generateSalaryReport() {
        if (employees.isEmpty()) {
            System.out.println("No data available.");
            return;
        }

        double total = 0, max = Double.MIN_VALUE, min = Double.MAX_VALUE;
        Map<String, List<Employee>> deptMap = new HashMap<>();

        for (Employee e : employees) {
            double s = e.getSalary();
            total += s;
            if (s > max) max = s;
            if (s < min) min = s;

            deptMap.putIfAbsent(e.getDepartment(), new ArrayList<>());
            deptMap.get(e.getDepartment()).add(e);
        }

        System.out.println("\n=== OVERALL REPORT ===");
        System.out.println("Total Employees: " + employees.size());
        System.out.printf("Average Salary: ₹%.2f\n", total / employees.size());
        System.out.println("Highest Salary: ₹" + max);
        System.out.println("Lowest Salary: ₹" + min);

        System.out.println("\n=== DEPARTMENT REPORT ===");
        System.out.printf("%-15s %-10s %-15s\n", "Department", "Count", "Avg Salary");
        System.out.println("-".repeat(40));

        for (String dept : deptMap.keySet()) {
            List<Employee> list = deptMap.get(dept);
            double sum = 0;

            for (Employee e : list) sum += e.getSalary();

            System.out.printf("%-15s %-10d ₹%-10.2f\n",
                    dept, list.size(), sum / list.size());
        }
    }

    // ================= VALIDATION =================
    private String getNonEmptyInput(String msg) {
        while (true) {
            System.out.print(msg);
            String input = scanner.nextLine().trim();
            if (!input.isEmpty()) return input;
            System.out.println("Input cannot be empty.");
        }
    }

    private double getValidSalary() {
        while (true) {
            try {
                System.out.print("Enter Salary: ");
                double salary = Double.parseDouble(scanner.nextLine());
                if (salary >= 0) return salary;
                System.out.println("Salary must be positive.");
            } catch (Exception e) {
                System.out.println("Invalid input.");
            }
        }
    }

    private int getValidInt(int min, int max) {
        while (true) {
            try {
                int val = scanner.nextInt();
                if (val >= min && val <= max) return val;
                System.out.print("Enter valid range: ");
            } catch (Exception e) {
                System.out.print("Invalid input: ");
                scanner.next();
            }
        }
    }

    // ================= FILE =================
    private void saveEmployeesToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
            oos.writeObject(employees);
        } catch (IOException e) {
            System.out.println("Error saving data.");
        }
    }

    @SuppressWarnings("unchecked")
    private void loadEmployeesFromFile() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(DATA_FILE))) {
            employees = (ArrayList<Employee>) ois.readObject();
            employeeMap.clear();
            for (Employee e : employees) {
                employeeMap.put(e.getId(), e);
            }
        } catch (Exception e) {
            System.out.println("No previous data found.");
        }
    }
}

// ================= MAIN =================
public class MainApp {
    public static void main(String[] args) {
        new EmployeeManagementSystem().start();
    }
}