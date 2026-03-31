package M1_Outside;

import java.util.*;
public class DocumentRedactionEngine {
	public static  String processLine(String input) {
		input = input.replaceAll("ID:[A-Z]{3}\\d{6}", "ID:XXX******");
		input =input.replaceAll("ACCT-\\d{4}-\\d{4}-(\\d{4})","ACCT---$1");
//		input =input.replaceAll("(?i)\\b(\\w+)(\\s+\\1)+\\b", "$1");
//		input= input.replaceAll("([!?\\.])\\1{2,}$", "$1");
		
		String[] words=input.split("\\s+");
		StringBuilder sb=new StringBuilder();
		String prev="";
		for(String w:words) {
			if(!w.equalsIgnoreCase(prev)) {
				if(sb.length()>0) {
					sb.append(" ");
				}
				sb.append(w);
			}
			prev=w;
		}
		input=sb.toString();
		
		
		if(input.endsWith("!!!")||input.endsWith("??")||input.endsWith("...")) {
			char last=input.charAt(input.length()-1);
			int pos=input.length()-1;
			while(pos>0 && input.charAt(pos)==last) {
				pos--;
			}
			input=input.substring(0, pos+1)+last;
		}
		return input;
	}
	
	public static void main(String[] args) {
		Scanner sc=new Scanner(System.in);
		int N=Integer.parseInt(sc.nextLine());
		
		List<String> inputs=new ArrayList<>();
		for(int i=0; i<N; i++) {
			inputs.add(sc.nextLine());
		}

		for(int i=0; i<N; i++) {
			System.out.println(processLine(inputs.get(i)));
		}
	}
}
