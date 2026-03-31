import java.util.Map;

@FunctionalInterface
interface LightAction {
    void activate();
}

public class SmartHomeLighting {
    public static void main(String[] args) {

        Map<String, LightAction> triggers = Map.of(
                "motion", () -> System.out.println("Motion → Lights ON"),
                "night", () -> System.out.println("Night → Lights DIM"),
                "voice", () -> System.out.println("Voice → Party Lights")
        );

        triggers.get("motion").activate();
        triggers.get("night").activate();
        triggers.get("voice").activate();
    }
}
