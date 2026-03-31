import java.util.*;
import java.util.stream.Collectors;
public class LibraryBookStatistics {
	public static void main(String[] args) {
        List<Book> books =Arrays.asList(
            new Book("Book A", "Science", 400),
            new Book("Book B", "Science", 350),
            new Book("Book C", "History", 500)
        );

        Map<String, IntSummaryStatistics> statsByGenre =books.stream().collect(Collectors.groupingBy(
                Book::getGenre, Collectors.summarizingInt(Book::getPages)
            ));

        statsByGenre.forEach((genre, stats) -> {
            System.out.println("Genre- " + genre);
            System.out.println("  Total pages- " +stats.getSum());
            System.out.println("  Average pages- " +stats.getAverage());
            System.out.println("  Max pages- " +stats.getMax());
        });
    }
}

class Book {
    String title;
    String genre;
    int pages;

    Book(String title, String genre, int pages) {
        this.title=title;
        this.genre=genre;
        this.pages=pages;
    }

    public String getGenre() {
        return genre;
    }

    public int getPages() {
        return pages;
    }

    public String getTitle() {
        return title;
    }
}
