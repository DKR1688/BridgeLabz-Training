package LibraryManagementSystem;

class Library {
    Book[] books;

    Library(Book[] books) {
        this.books = books;
    }

    void showBooks() {
        System.out.println("Library Books are- ");
        for (Book b : books) {
            System.out.println(b);
        }
    }

    void search(String query) {
        boolean found = false;
        for (Book b : books) {
            if (b.title.toLowerCase().contains(query.toLowerCase())) {
                System.out.println(b);
                found = true;
            }
        }
        if (!found) {
            System.out.println("No match found.");
        }
    }

    boolean checkout(String title) throws BookNotAvailableException {
        for (Book b : books) {
            if (b.title.equalsIgnoreCase(title)) {
                if (!b.checkedOut) {
                    b.checkedOut = true;
                    return true;
                } else {
                    throw new BookNotAvailableException("Book '" + title + "' is not available for checkout.");
                }
            }
        }
        return false;
    }

    boolean returnBook(String title) {
        for (Book b : books) {
            if (b.title.equalsIgnoreCase(title) && b.checkedOut) {
                b.checkedOut = false;
                return true;
            }
        }
        return false;
    }
}
