public class Employee {

    // employee_name,
    // employee_dob,
    // employee_female,
    // employee_national_id,
    // employee_employment_number,
    // employee_id,
    // employee_employer,

    String name, dob, female, nationalId, pn, id, employer;

    private String gender;

    public Employee(String name, String dob, String female, String nationalId, String pn, String id, String employer) {
        this.name = name;
        this.dob = dob;
        this.female = female;
        this.nationalId = nationalId;
        this.pn = pn;
        this.id = id;
        this.employer = employer;
    }

    public String getGender() {
        return female.toLowerCase().equals("true") ? "female" : "male";
    }

}
