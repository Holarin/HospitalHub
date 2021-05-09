package model;

public enum Time {
    TIME_8_13,
    TIME_14_19;


    public static final String TIME_8_13String = "с 8:00 до 13:00";
    public static final String TIME_14_19String = "с 14:00 до 19:00";
    @Override
    public String toString() {
        switch (this) {
            case TIME_8_13: return TIME_8_13String;
            case TIME_14_19: return TIME_14_19String;
            default: throw new IllegalArgumentException();
        }
    }

    static public Time parse(String value) {
        switch (value) {
            case TIME_8_13String: return TIME_8_13;
            case TIME_14_19String: return TIME_14_19;
            default: throw new IllegalArgumentException();
        }
    }
}
