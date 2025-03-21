package main;

public class IdGenerator {
    private static Integer newId = 0;

    public static Integer generateId() {
        newId++;
        return newId;
    }
}
