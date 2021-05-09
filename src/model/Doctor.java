package model;

import java.util.Objects;

public class Doctor {
    private String name;
    private String secondName;
    private String fathersName;
    private Specialty specialty;
    private Availability availability;
    private String currentCabinet;

    public String toCsvString() {

        return name + ";" +
                secondName + ";" +
                fathersName + ";" +
                specialty + ";" +
                availability.toCsvString(',') + ";" +
                currentCabinet;
    }

    static public Doctor fromCsvString(String csv) {
        String[] dr = csv.split(";");
        Doctor doctor = new Doctor();
        doctor.setName(dr[0]);
        doctor.setSecondName(dr[1]);
        doctor.setFathersName(dr[2]);
        doctor.setSpecialty(Specialty.parse(dr[3]));
        doctor.setAvailability(Availability.fromCsvString(dr[4], ","));
        doctor.setCurrentCabinet(dr[5]);
        return doctor;
    }

    public Doctor() {}

    public void setCurrentCabinet(String currentCabinet) {
        this.currentCabinet = currentCabinet;
    }

    public String getCurrentCabinet() {
        return currentCabinet;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getFathersName() {
        return fathersName;
    }

    public void setFathersName(String fathersName) {
        this.fathersName = fathersName;
    }

    public Specialty getSpecialty() {
        return specialty;
    }

    public void setSpecialty(Specialty specialty) {
        this.specialty = specialty;
    }

    public Availability getAvailability() {
        return availability;
    }

    public void setAvailability(Availability availability) {
        this.availability = availability;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Doctor doctor = (Doctor) o;
        if (!doctor.currentCabinet.equals(currentCabinet)) {
            return false;
        }
        return availability.equals(doctor.availability);
    }

    @Override
    public String toString() {
        return "Doctor{" +
                "name='" + name + '\'' +
                ", secondName='" + secondName + '\'' +
                ", fathersName='" + fathersName + '\'' +
                ", specialty=" + specialty +
                ", availability=" + availability +
                ", currentCabinet='" + currentCabinet + '\'' +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(availability, currentCabinet);
    }
}
