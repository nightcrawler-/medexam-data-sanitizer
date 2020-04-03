import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.LinkedHashSet;

public class Main {

    private static ArrayList<Workplace> workplaces = new ArrayList<>();
    private static ArrayList<Employee> employees = new ArrayList<>();

    private static ObjectMapper mapper = new ObjectMapper();

    public static void main(String... args) {
        try {
            processRawFile("data_raw.csv");
            deDuplicateWorkplaces();
            deDuplicateEmployees();
            buildWorkforce();
            result();
            generateJson();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Extract Employer and Employee info, chuck duplicates and have in an export ready format, i.e. matches json body required
     */
    public static void processRawFile(String filePath) throws IOException {
        //Headers:

        // employee_name,
        // employee_dob,
        // employee_female,
        // employee_national_id,
        // employee_employment_number,
        // employee_id,
        // employee_employer,

        // employer_name,
        // employer_registration_number,
        // employer_location,
        // employer_tel,
        // employer_address,
        // employer_email,
        // employer_id,
        //
        // session_date,
        // session_health_risk,
        // session_exam,
        // session_id,
        // session_name_and_address_of_employer

        Reader in = new FileReader(filePath);
        Iterable<CSVRecord> records = CSVFormat.EXCEL.withHeader().parse(in);

        for (CSVRecord record : records) {
            String eName = record.get("employee_name");
            String eAge = record.get("employee_dob");
            String eFemale = record.get("employee_female");
            String eNationalId = record.get("employee_national_id");
            String ePn = record.get("employee_employment_number");
            String eId = record.get("employee_id");
            String eEmployer = record.get("employee_employer");

            String wName = record.get("employer_name");
            String wReg = record.get("employer_registration_number");
            String wLoc = record.get("employer_location");
            String wPhone = record.get("employer_tel");
            String wAddress = record.get("employer_address");
            String wEmail = record.get("employer_email");
            String wId = record.get("employer_id");

            String sDate = record.get("session_date");

            Workplace workplace = new Workplace(wName, wReg, wLoc, wPhone, wAddress, wEmail, wId);
            Employee employee = new Employee(eName, getAgeOrZero(eAge), eFemale, eNationalId, ePn, eId, workplace.hashCode(), sDate);

            //Add all work places, duplicates allowed, to workplaces arraylist
            workplaces.add(workplace);
            employees.add(employee);
        }
    }

    private static int getAgeOrZero(String age) {

        try {
            return Integer.parseInt(age);
        } catch (NumberFormatException ex) {
            return 0;
        }
    }

    private static void deDuplicateWorkplaces() {
        System.out.println("###### Total Workplaces Before De-Dup: " + workplaces.size());
        LinkedHashSet<Workplace> workplacesSet = new LinkedHashSet<>(workplaces);
        workplaces = new ArrayList<Workplace>(workplacesSet);
        System.out.println("###### Total Workplaces After De-Dup: " + workplaces.size());
    }

    private static void deDuplicateEmployees() {
        System.out.println("###### Total Employees Before De-Dup: " + employees.size());
        LinkedHashSet<Employee> employeesSet = new LinkedHashSet<>(employees);
        employees = new ArrayList<Employee>(employeesSet);
        System.out.println("###### Total Employees After De-Dup: " + employees.size());
    }

    //Generates the employees for each workplace, can it be more efficient?
    private static void buildWorkforce() {
        for (Workplace workplace : workplaces) {
            for (Employee employee : employees) {
                if (employee.employer == workplace.hashCode()) {
                    workplace.addEmployee(employee);
                }
            }
        }
    }

    private static void result() {
        for (Workplace workplace : workplaces) {
            System.out.println(workplace.name + ": " + workplace.getEmployees().size());
        }
    }

    private static void generateJson() throws IOException {
        System.out.println(mapper.writeValueAsString(workplaces));
        mapper.writeValue(new File("dump.json"), workplaces);

    }

}
