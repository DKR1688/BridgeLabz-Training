
public class ReplaceMultipleSpacesWithSingleSpace {
	public static void main(String[] args) {
		String text ="This   is an      example    with multiple     spaces.";
		
		String replaceText =text.replaceAll("\\s+", " ");
		System.out.println(replaceText);
	}
}
