package Models;

/**
 * Created by Aayushi.Garg on 02-11-2017.
 */

public enum  DashboardSection {
    Unknown("Unknown", 0),
    TopStories("TopStories ", 1),
    World("World ", 2),
    Business("Business ", 3),
    Technology("Technology ", 4);

    private String stringValue;
    private int intValue;
    private DashboardSection(String toString, int value) {
        stringValue = toString;
        intValue = value;
    }

    @Override
    public String toString() {
        return stringValue;
    }





}
