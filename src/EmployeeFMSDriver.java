
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class EmployeeFMSDriver implements EmployeeCRUD {

    static final String EMPLOYEE_FILENAME = "employees.csv";
    static final String prompt = "00";

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
                    return employee;
                }
            }
            in.close();
        }
        catch (FileNotFoundException ex) {
            // ignoring ...}
        }
        return null;
    }

    @Override
    public void update(int id, Employee employee) {
        boolean foundUpdate = false;
        int lineCount = 0;
        try{
            Scanner in = new Scanner(new FileInputStream(EMPLOYEE_FILENAME));
            while (in.hasNextLine()) {
                String line = in.nextLine();
                // ignoring deleted employees
                if (line.charAt(0) == '#') {
                    lineCount += 1;
                    continue;
                    }
                lineCount +=1;
                String data[] = line.split(",");
                int idNum = Integer.parseInt(data[0]);
                if (idNum == employee.getId()) {
                    foundUpdate = true;
                    break;
                }
            }
            in.close();



            // line.replace()



        }catch (FileNotFoundException f){

        }

    }

    @Override
    public void delete(int id) {

        ArrayList<Employee> newData = new ArrayList<Employee>();

        try {
            PrintStream out = new PrintStream(new FileOutputStream(EMPLOYEE_FILENAME, true));
            Scanner in = new Scanner(new FileInputStream(EMPLOYEE_FILENAME));
            int lineCount = 0;
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

        }
        catch(FileNotFoundException ex2) {
            System.out.println("File not found");
        }

    }

    public static void promptCreate(String prompt, EmployeeFMSDriver emp){
        Scanner reader = new Scanner(System.in);
        if (prompt == "create") {

            String name = "";
            String department = "";
            int id = 0;

            System.out.println("WHat is the employees name?");
            name = reader.nextLine();
            System.out.println(name);
            //emp.create
        }
    }

    public static void main(String[] args) {
        EmployeeFMSDriver empl = new EmployeeFMSDriver();
        Employee temp = new Employee(0, "none", "none");

        Employee employee1 = new Employee(1,"Carl", "IT");
        empl.create(employee1);

        Employee employee2 = new Employee(2,"Bob", "sales");
        empl.create(employee2);

        Employee employee3 = new Employee(3,"john", "accounting");
        empl.create(employee3);


        empl.delete(2);

        try {
            temp = empl.read(5);
            System.out.println(temp.getDepartment());

        }
        catch(NullPointerException n) {
            System.out.println("No Employee exists with that ID");
        }
        //promptCreate("create", empl);



        // create a menu of options here or hard code some examples
        // to illustrate that your code works!

    }
}
