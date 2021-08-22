package bisect.java;

record BisectResult(Suspect lastGood, Suspect firstBad) {
    public static BisectResult bisectResult(Suspect lastGood, Suspect firstBad) {
        return new BisectResult(lastGood, firstBad);
    }
}
