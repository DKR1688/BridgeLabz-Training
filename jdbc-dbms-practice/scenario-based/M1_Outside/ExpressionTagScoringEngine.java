package M1_Outside;

import java.util.*;
import java.util.regex.*;
public class ExpressionTagScoringEngine {
	public static String evalute(String input) {
		Pattern pattern=Pattern.compile("\\[(SUM|MUL|MAX|MIN):([^\\[\\]]+)\\]");
		Matcher matcher=pattern.matcher(input);
		
		StringBuffer ans=new StringBuffer();
		while(matcher.find()) {
			String operation=matcher.group(1);
			String expression=matcher.group(2);
			
			if(!expression.matches("(-?(0|[1-9]\\d*))(,-?(0|[1-9]\\d*))+")) {
				matcher.appendReplacement(ans, "ERROR");
				continue;
			}
			
			String[] nums=expression.split(",");
			int[] values=Arrays.stream(nums).mapToInt(Integer::parseInt).toArray();
			
			int repValue;
			switch(operation) {
			case "SUM":
				repValue=Arrays.stream(values).sum();
				break;
			
			case "MUL":
				repValue=Arrays.stream(values).reduce(1, (a, b) -> a*b);
				break;
				
			case "MAX":
				repValue=Arrays.stream(values).max().getAsInt();
				break;
			
			case "MIN":
				repValue=Arrays.stream(values).min().getAsInt();
				break;
				
			default:
				repValue=0;
			}
			
			matcher.appendReplacement(ans, String.valueOf(repValue));
		}
		matcher.appendTail(ans);
		ans=new StringBuffer(ans.toString().replaceAll("\\[[^\\]]*$", "ERROR"));
	
		return ans.toString();
	}
	
	public static void main(String[] args) {
		Scanner sc=new Scanner(System.in);
		int N=Integer.parseInt(sc.nextLine());
		
		List<String> inputs=new ArrayList<>();
		for(int i=0; i<N; i++) {
			inputs.add(sc.nextLine());
		}
		
		for(int i=0; i<N; i++) {
			String input=inputs.get(i);
			System.out.println(evalute(input));
		}
	}
}
