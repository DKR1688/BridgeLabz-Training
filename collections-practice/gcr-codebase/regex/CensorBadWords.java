import java.util.*;
public class CensorBadWords {
	public static void main(String[] args) {
		String text ="This is a damn mad example with some stupid words.";
		
		List<String> list=new ArrayList<>();
		list.add("damn");
		list.add("stupid");
		list.add("mad");
		
		String[] textWord =text.split(" ");
		String replaceText=text;
		for(int i=0; i<textWord.length; i++) {
			if(list.contains(textWord[i])) {
				replaceText =replaceText.replaceAll(textWord[i], "****");
			}
		}
		System.out.println(replaceText);
	}
}
