import java.util.Objects;

public class Employee {

    // employee_name,
    // employee_dob,
    // employee_female,
    // employee_national_id,
    // employee_employment_number,
    // employee_id,
    // employee_employer,

    public String name, dob, female, nationalId, pn, id;
    public int employer;

    public String gender;

    /**
     *
     * @param name
     * @param dob
     * @param female
     * @param nationalId
     * @param pn
     * @param id
     * @param employer use employer object hash for the time being, source data can't be trusted to have been strict
     *                 with the associations
     */
    public Employee(String name, String dob, String female, String nationalId, String pn, String id, int employer) {
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

    /**
     * Too bad if no id number was used during the generation of initial data, that person is now a ghost
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return nationalId.equals(employee.nationalId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nationalId);
    }
}
