package StringBuffer;

public class CompareBuilderAndBuffer {
	public static void main(String[] args) {
        int n =1000000; //1M for concatenations
        
        //String builder concatenation
        StringBuilder sb =new StringBuilder();
        long start =System.nanoTime();
        for (int i=0; i<n; i++) {
            sb.append("hello");
        }
        long end =System.nanoTime();
        long time = end-start;

        //String buffer concatenation
        StringBuffer sb2 =new StringBuffer();
        long start2 =System.nanoTime();
        for (int i=0; i<n; i++) {
            sb2.append("hello");
        }
        long end2 =System.nanoTime();
        long time2 = end2-start2;

        System.out.println("Time taken by StringBuilder is- " + time+ " ns");
        System.out.println("Time taken by StringBuffer is- " + time2+ " ns");
    }
}
