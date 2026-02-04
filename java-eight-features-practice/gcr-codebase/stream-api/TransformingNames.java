import java.util.Arrays;
import java.util.List;

public class TransformingNames {
	public static void main(String[] args) {

		List<String> names = Arrays.asList("Abhay", "Deeepak", "Abhishek", "Arjun", "Radhika", "Anuj", "ABCDEF",
				"Jyotika", "Anshuman");

		names.stream().map(String::toUpperCase).sorted().forEach(System.out::println);
	}
}
