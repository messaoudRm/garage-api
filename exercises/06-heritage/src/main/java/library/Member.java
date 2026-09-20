package library;

public class Member extends AbstractEntity {

    private final String name;

    public Member(String name) {
        this.name = name;
    }

    public String badge() {
        return name.toUpperCase();
    }
}
