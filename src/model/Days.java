package model;

public enum Days {
    MONDAY, //Понедельник
    TUESDAY, //Вторник
    WEDNESDAY, //Среда
    THURSDAY, //Четверг
    FRIDAY; //Пятница

    @Override
    public String toString() {
        switch (this) {
            case MONDAY: return "Понедельник";
            case TUESDAY: return "Вторник";
            case WEDNESDAY: return "Среда";
            case THURSDAY: return "Четверг";
            case FRIDAY: return "Пятница";
            default: throw new IllegalArgumentException();
        }
    }

    static public Days parse(String day) {
        switch (day) {
            case "Понедельник": return MONDAY;
            case "Вторник": return TUESDAY;
            case "Среда": return WEDNESDAY;
            case "Четверг": return THURSDAY;
            case "Пятница": return FRIDAY;
            default: throw new IllegalArgumentException();
        }
    }

}
