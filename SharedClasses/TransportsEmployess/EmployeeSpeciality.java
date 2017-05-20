package SharedClasses.TransportsEmployess;

public class EmployeeSpeciality {
    int id;
    String specialization;

    public EmployeeSpeciality(int id, String specialization) {

        this.id = id;
        this.specialization = specialization;
    }

    public int getId() {
        return id;
    }
	public String getSpecialization() {
        return specialization;
    }

    
    @Override
	public String toString() {
		return "EmployeeSpeciality [id=" + id + ", specialization=" + specialization + "]";
	}

}
