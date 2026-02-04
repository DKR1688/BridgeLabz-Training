import java.util.Arrays;
import java.util.List;

public class IoTSensor {
	protected static int thresh=300;
	public static void main(String[] args) {
		List<Integer>readings=Arrays.asList(230,432,567,787,654,342,123,23,89,887);
		readings.stream().filter(t -> t > thresh ).forEach(reading ->System.out.println(reading));

	}
}
