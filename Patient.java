public class Patient {
    String name, gender, disease, prescription;
    int age;

    public Patient(String name, int age, String gender, String disease, String prescription) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.disease = disease;
        this.prescription = prescription;
    }
}