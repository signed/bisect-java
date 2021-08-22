package bisect.java;

record Suspect(Version version) {
    public static Suspect suspect(Version version) {
        return new Suspect(version);
    }
}
