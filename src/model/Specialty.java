package model;

public enum Specialty {
    GASTROENTEROLOGIST,
    PEDIATRICIAN,
    PSYCHIATRIST,
    ORTHOPEDIST,
    OCULIST,
    DERMATOLOGIST,
    TRAUMA_SPECIALIST,
    GYNECOLOGIST,
    UROLOGIST,
    ENT;

    @Override
    public String toString() {
        switch (this) {
            case ENT: return "ЛОР";
            case OCULIST: return "Окулист";
            case UROLOGIST: return "Уролог";
            case ORTHOPEDIST: return "Ортопед";
            case GYNECOLOGIST: return "Гинеколог";
            case PEDIATRICIAN: return "Педиатр";
            case PSYCHIATRIST: return "Психиатр";
            case DERMATOLOGIST: return "Дерматолог";
            case TRAUMA_SPECIALIST: return "Травматолог";
            case GASTROENTEROLOGIST: return "Гастроэнтеролог";
            default: throw new IllegalArgumentException();
        }
    }

    static public Specialty parse(String specialty) {
        switch (specialty) {
            case "ЛОР": return ENT;
            case "Окулист": return OCULIST;
            case "Уролог": return UROLOGIST;
            case "Ортопед": return ORTHOPEDIST;
            case "Гинеколог": return GYNECOLOGIST;
            case "Педиатр": return PEDIATRICIAN;
            case "Психиатр": return PSYCHIATRIST;
            case "Дерматолог": return DERMATOLOGIST;
            case "Травматолог": return TRAUMA_SPECIALIST;
            case "Гастроэнтеролог": return GASTROENTEROLOGIST;
            default: throw new IllegalArgumentException();
        }
    }
}
