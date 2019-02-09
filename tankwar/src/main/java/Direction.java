enum Direction {
    LeftUp("LU"),
    Up("U"),
    RightUp("RU"),
    Left("L"),
    Right("R"),
    LeftDown("LD"),
    Down("D"),
    RightDown("RD"),
    Stop("S");

    public String abbrev;

    Direction(String abbrev) {
        this.abbrev = abbrev;
    }

}