import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class Employee {

    // employee_name,
    // employee_dob,
    // employee_female,
    // employee_national_id,
    // employee_employment_number,
    // employee_id,
    // employee_employer,

    private static final String OUT_DATE_FORMAT = "yyyy-MM-dd";//2020-04-03
    private static final String IN_DATE_FORMAT = "MM/dd/yyyy";//01/19/2019

    public String name, gender, pn;

    @JsonProperty("national_id")
    public String nationalId;

    @JsonIgnore
    private String female, id, dateString;

    @JsonIgnore
    public int employer, age;

    private String dob;//value preferred from getter

    /**
     * @param name
     * @param age
     * @param female
     * @param nationalId
     * @param pn
     * @param id
     * @param employer   use employer object hash for the time being, source data can't be trusted to have been strict
     *                   with the associations
     */
    public Employee(String name, int age, String female, String nationalId, String pn, String id, int employer, String dateString) {
        this.name = name;
        this.age = age;
        this.female = female;
        this.nationalId = nationalId;
        this.pn = pn;
        this.id = id;
        this.employer = employer;
        this.dateString = dateString;
    }

    public String getGender() {
        return female.toLowerCase().equals("true") ? "female" : "male";
    }

    /**
     * Too bad if no id number was used during the generation of initial data, that person is now a ghost
     *
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

    /**
     * Get the approximate DOB, based on age and when this was record
     *
     * @return
     * @throws ParseException
     */
    public String getDob() {

        SimpleDateFormat formatIn = new SimpleDateFormat(IN_DATE_FORMAT);
        Date date = null;
        try {
            date = formatIn.parse(dateString);
        } catch (ParseException e) {
            date = new Date();
        }

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        cal.set(Calendar.YEAR, cal.get(Calendar.YEAR) - age);

        date.setTime(cal.getTimeInMillis());

        SimpleDateFormat formatOut = new SimpleDateFormat(OUT_DATE_FORMAT);


        this.dob = formatOut.format(date);

        return dob;
    }
}
