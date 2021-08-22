package bisect.java;

public record Version(String string) {
    public static Version version(String string){
        return new Version(string);
    }
}
