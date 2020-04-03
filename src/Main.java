import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.LinkedHashSet;

public class Main {

    private static ArrayList<Workplace> workplaces = new ArrayList<>();

    public static void main(String... args) {
        try {
            processRawFile("data_raw.csv");
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
            String eDob = record.get("employee_dob");
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

            Workplace workplace = new Workplace(wName, wReg, wLoc, wPhone, wAddress, wEmail, wId);
            Employee employee = new Employee(eName, eDob, eFemale, eNationalId, ePn, eId, eEmployer);

            //Add all work places, duplicates allowed, to workplaces arraylist
            workplaces.add(workplace);

        }

        deDuplicateWorkplaces();
    }

    private static void deDuplicateWorkplaces() {
        System.out.println("###### Total Workplaces Before De-Dup: " + workplaces.size());
        LinkedHashSet<Workplace> workplacesSet = new LinkedHashSet<>(workplaces);
        workplaces = new ArrayList<Workplace>(workplacesSet);
        System.out.println("###### Total Workplaces After De-Dup: " + workplaces.size());
    }
}
