package model;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Availability {
    private final Set<Days> days;
    private final Time time;

    public Availability(Set<Days> days, Time time) {
        this.days = days;
        this.time = time;
    }

    static public Availability fromCsvString(String csv, String regex) {
        String[] av = csv.split(regex);
        Set<Days> days = new HashSet<>();
        for (int i = 0; i < av.length-1;i++) {
            days.add(Days.parse(av[i]));
        }

        Time time = Time.parse(av[av.length - 1]);
        return new Availability(days, time);
    }

    public String toCsvString(char regex) {
        StringBuilder sb = new StringBuilder();

        for (Days day : days) {
            sb.append(day.toString()).append(regex);
        }

        sb.append(time.toString());

        return sb.toString();
    }


    public Set<Days> getDays() {
        return days;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Availability that = (Availability) o;
        if (time != that.time) {
            return false;
        }

        for (Days day : days) {
            for (Days day1 : that.days) {
                if (day == day1) {
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(days, time);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Дни работы: ");

        for (Days day : days) {
            sb.append(day.toString()).append(", ");
        }

        return sb.toString() + " время работы: " + time.toString();
    }
}
