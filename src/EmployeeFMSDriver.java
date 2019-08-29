
import java.io.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class EmployeeFMSDriver implements EmployeeCRUD {

    static final String EMPLOYEE_FILENAME = "employees.csv";

    @Override
    public void create(Employee employee) {
        boolean found = false;
        try {
            Scanner in = new Scanner(new FileInputStream(EMPLOYEE_FILENAME));
            while (in.hasNextLine()) {
                String line = in.nextLine();
                // ignoring deleted employees
                if (line.charAt(0) == '#')
                    continue;
                String data[] = line.split(",");
                int id = Integer.parseInt(data[0]);
                if (id == employee.getId()) {
                    found = true;
                    break;
                }
            }
            in.close();
        }
        catch (FileNotFoundException ex) {
        }
        if (found)
            System.out.println("Employee with same id already exists!");
        else {
            try {
                PrintStream out = new PrintStream(new FileOutputStream(EMPLOYEE_FILENAME, true));
                out.println(employee);
                out.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        System.out.println("employee created");
    }

    @Override
    public Employee read(int id) {
        try {
            Scanner in = new Scanner(new FileInputStream(EMPLOYEE_FILENAME));
            while (in.hasNextLine()) {
                String line = in.nextLine();
                // ignoring deleted employees
                if (line.charAt(0) == '#')
                    continue;
                String data[] = line.split(",");
                int key = Integer.parseInt(data[0]);
                if (id == key) {
                    String name = data[1];
                    String dep = data[2];
                    Employee employee = new Employee(id, name, dep);
                    in.close();
                    if (isNull(employee)){
                        System.out.println("No employee with that ID.");
                    }
                    return employee;
                }
            }
            in.close();
        }
        catch (FileNotFoundException ex) {
            System.out.println("File not found");
            // ignoring ...}
        }
        return null;
    }

    @Override
    public void update(int id, Employee employee) {

        ArrayList<Employee> newData = new ArrayList<Employee>();

        try {
            Scanner in = new Scanner(new FileInputStream(EMPLOYEE_FILENAME));
            while (in.hasNextLine()) {
                String line = in.nextLine();
                if (line.charAt(0) == '#') {
                    continue;
                }

                String data[] = line.split(",");
                int idFound = Integer.parseInt(data[0]);

                if (idFound == id){
                    newData.add(employee);
                }
                else {
                    Employee temp = new Employee(idFound, data[1], data[2]);
                    newData.add(temp);
                }
            }
            in.close();
        }
        catch (FileNotFoundException ex) {
            System.out.println("Employee does not exists!");

        }
        try{
            PrintStream out = new PrintStream(new FileOutputStream(EMPLOYEE_FILENAME));

            for(int i = 0; i < newData.size(); i++ ) {
                out.println(newData.get(i));
            }

            out.close();

        }
        catch(FileNotFoundException ex2) {
            System.out.println("File not found");
        }

    }

    @Override
    public void delete(int id) {

        ArrayList<Employee> newData = new ArrayList<Employee>();

        try {
            Scanner in = new Scanner(new FileInputStream(EMPLOYEE_FILENAME));
            while (in.hasNextLine()) {
                String line = in.nextLine();
                if (line.charAt(0) == '#') {
                    continue;
                }

                String data[] = line.split(",");
                int idFound = Integer.parseInt(data[0]);

                if (idFound == id){
                    continue;
                }
                else {
                    Employee temp = new Employee(idFound, data[1], data[2]);
                    newData.add(temp);
                }
            }
            in.close();
        }
        catch (FileNotFoundException ex) {
            System.out.println("Employee does not exists!");

        }

        try{
            PrintStream out = new PrintStream(new FileOutputStream(EMPLOYEE_FILENAME));

            for(int i = 0; i < newData.size(); i++ ) {
                out.println(newData.get(i));
            }

            out.close();

        }
        catch(FileNotFoundException ex2) {
            System.out.println("File not found");
        }

    }

    private boolean isNull(Object obj) {
        return obj == null;
    }


    public static void main(String[] args) {
        EmployeeFMSDriver empl = new EmployeeFMSDriver();
        Scanner userInput = new Scanner(System.in);

        String choice = "";


        while(!choice.equals("exit")) {
            System.out.println("Would you like either Create an employee, read an employee id, update an employee or delete an employee or exit?");
            System.out.println("Type -  create / read / update / delete / exit");
            choice = userInput.nextLine();

            if (choice.equals("create")) {
                System.out.println("What is the employee's name?");
                String newName = userInput.nextLine();
                System.out.println("What is the employee's department?");
                String newDept = userInput.nextLine();
                int newId = 1;

                try {
                    Scanner inRead = new Scanner(new FileInputStream(EMPLOYEE_FILENAME));
                    ArrayList<Integer> existingIds = new ArrayList<Integer>();
                    while (inRead.hasNextLine()) {
                        String line = inRead.nextLine();
                        if (line.charAt(0) == '#')
                            continue;
                        String data[] = line.split(",");
                        int key = Integer.parseInt(data[0]);
                        existingIds.add(key);
                    }
                    for (int i=0; i < existingIds.size(); i++){
                        if(existingIds.contains(newId)){
                            newId += 1;
                        }
                        else {
                            break;
                        }
                    }
                    inRead.close();
                }
                catch(FileNotFoundException e) {
                    System.out.println("File not found.");
                }
                Employee tempCreateEmployee = new Employee(newId,newName, newDept);
                empl.create(tempCreateEmployee);
            }

            else if (choice.equals("read")) {

                boolean isInteger = false;
                while (isInteger == false) {
                    System.out.println("what user id would you like to read");
                    String readId = userInput.nextLine();
                    try {
                        int readIdInt = Integer.parseInt(readId);
                        empl.read(readIdInt);
                        isInteger = true;
                        System.out.println(empl.read(readIdInt));

                    }
                    catch(Exception e) {
                        System.out.println("This is not an integer.");
                        continue;
                    }
                }
            }

            else if (choice.equals("update")) {
                boolean idToUpdateBool = false;
                while (idToUpdateBool == false) {
                    System.out.println("What user id would you like to update?");

                    try {
                        String newName = "#";
                        String newDept = "#";
                        int intIdToUpdate = userInput.nextInt();
                        userInput.nextLine();

                        System.out.println("What would you like to change employee # " +intIdToUpdate + "'s name to?" );
                        newName = userInput.nextLine();

                        System.out.println("What would you like to change employee # " +intIdToUpdate + "'s department to?" );
                        newDept = userInput.nextLine();
                        Employee employeeToUpdate = new Employee(intIdToUpdate, newName, newDept);
                        empl.update(intIdToUpdate, employeeToUpdate);
                        idToUpdateBool = true;
                    }
                    catch(InputMismatchException e) {
                        System.out.println("Input Mismatch, try again.");
                        continue;
                    }
                }
            }

            else if (choice.equals("delete")) {
                System.out.println("What is the id of the employee you want to delete?");
                try{
                    int idToDelete = userInput.nextInt();
                    userInput.nextLine();
                    empl.delete(idToDelete);
                }
                catch(InputMismatchException e){
                    System.out.println("Please type in an integer.");
                    continue;
                }

            }
            else if (choice.equals("exit")) {
                break;
            }
            else{
                System.out.println("Could not recognize user Input please try again or type exit to stop the program.");
                continue;
            }

        }

        userInput.close();
        System.exit(0);

    }
}
