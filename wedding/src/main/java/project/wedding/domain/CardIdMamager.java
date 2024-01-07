package project.wedding.domain;


public class CardIdMamager {
    private static int lastId = 0;

    public static int getLastId() {
        ++lastId;
        return lastId;
    }
}
