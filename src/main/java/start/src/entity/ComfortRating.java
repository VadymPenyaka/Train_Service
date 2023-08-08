package start.src.entity;

public enum ComfortRating {
    SEATING("Seating", 0), SECONDCLASS("Second class", 1), FIRSTCLASS("First class", 2), LUXE("Luxe", 3);
    private final String name;
    private final int comfortRating;
    ComfortRating(String name, int comfortRating){
        this.name=name;
        this.comfortRating=comfortRating;
    }

    public String toString() {
        return name;
    }

    public int getComfortRating() {
        return comfortRating;
    }
}
