class LegacyAPI {
    @Deprecated
    public void oldFeature() {
        System.out.println("Old feature (deprecated)");
    }
    public void newFeature() {
        System.out.println("New feature");
    }
}

public class DeprecatedAnnotation {
    public static void main(String[] args) {
        LegacyAPI api = new LegacyAPI();
        api.oldFeature(); // Should show warning
        api.newFeature();
    }
}

