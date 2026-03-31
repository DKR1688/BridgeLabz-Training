package LibraryManagementSystem;

//custom Exception
class BookNotAvailableException extends Exception {
  BookNotAvailableException(String message) {
      super(message);
  }
}
