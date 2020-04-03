import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Workplace {

    // employer_name,
    // employer_registration_number,
    // employer_location,
    // employer_tel,
    // employer_address,
    // employer_email,
    // employer_id,


    //Include hard practitioner id for rails seed

    @JsonProperty("practitioner_id")
    public int practitionerId = 1;

    public String name, registration, location, phone, address, email, id;

    public List<Employee> employees = new ArrayList<>();

    public Workplace(String name, String registration, String location, String phone, String address, String email, String id) {
        this.name = name;
        this.registration = registration;
        this.location = location;
        this.phone = phone;
        this.address = address;
        this.email = email;
        this.id = id;
    }

    /**
     * In order to de-duplicate a list, this is required. The assumption is that the same name, location and regisitration imply the same object
     *
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Workplace workplace = (Workplace) o;
        return Objects.equals(name, workplace.name) &&
                Objects.equals(registration, workplace.registration) &&
                Objects.equals(location, workplace.location);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, registration, location);
    }

    public void addEmployee(Employee employee) {
        employees.add(employee);
    }

    public List<Employee> getEmployees() {
        return employees;
    }
}
