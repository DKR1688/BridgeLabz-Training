import java.util.*;
class SentenceFormatter {
    public static void main(String[] args) {
        Scanner scanner =new Scanner(System.in);
        System.out.println("Enter a paragraph to format- ");
        String string = scanner.nextLine();

        System.out.println(getCorrectParagraph(string));
        scanner.close();
    }

    //this method is to auto-correct paragraph formatting
    static String getCorrectParagraph(String paragraph) {
        if (paragraph.trim().isEmpty()) {
            return "";
        }

        //triming spaces and collapse multiple spaces into one
        paragraph =paragraph.trim().replaceAll("\\s+", " ");

        //here will be one space after punctuation 
        paragraph =paragraph.replaceAll("([.!?,])\\s*", "$1 ");

        //here first letter of each sentence
        StringBuilder sb =new StringBuilder();
        boolean isCapital =true;

        for (char c : paragraph.toCharArray()) {
            if (isCapital && Character.isLetter(c)) {
                sb.append(Character.toUpperCase(c));
                isCapital = false; // Reseting it until next punctuation
            } else {
                sb.append(c);
            }

            if (c=='.' || c=='?' || c=='!') {
                isCapital =true;
            }
        }

        return sb.toString().trim();
    }
}