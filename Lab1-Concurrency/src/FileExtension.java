public enum FileExtension {
    TXT(".txt");

    private final String name;

    FileExtension(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
