package LinearSearch;

import java.util.Scanner;
public class SearchWord {
    public static void main(String[] args) {
        Scanner scanner =new Scanner(System.in);

        System.out.print("Enter the number of sentences- ");
        int num =Integer.parseInt(scanner.nextLine());

        String[] sentences =new String[num];
        System.out.println("Enter sentences- ");
        for (int i=0; i<num; i++) {
            System.out.print("Sentence at index no "+i+ " is- ");
            sentences[i] = scanner.nextLine();
        }

        System.out.print("Enter the word you want to search- ");
        String word =scanner.nextLine();

        SearchWord search =new SearchWord();
        String ans= search.searchWord(sentences, word);

        if (ans== null) {
            System.out.println("There is not a single sentence that contains the specific word.");
        } else {
            System.out.println("The first sentence containing that word is- " +ans);
        }
        scanner.close();
    }

    //finding the first sentence containing the word
    public String searchWord(String[] sentences, String word) {
        for (String sentence : sentences) {
            //Splitting sentence into words by spaces and punctuation
            String[] words = sentence.split(" ");
            for (String w : words) {
                if (w.equalsIgnoreCase(word)) {
                    return sentence; //return the first sentence containing the exact word
                }
            }
        }
        return null;
    }
}