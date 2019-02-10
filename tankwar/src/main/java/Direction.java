enum Direction {
    LeftUp("LU"),
    Up("U"),
    RightUp("RU"),
    Left("L"),
    Right("R"),
    LeftDown("LD"),
    Down("D"),
    RightDown("RD");

    public String abbrev;

    Direction(String abbrev) {
        this.abbrev = abbrev;
    }

}