import java.util.ArrayList;
import java.util.List;

public class App {
    public static void main(String[] args) {
        // Lists
        List<BookObject> books = new ArrayList<BookObject>();
        List<String> booksNames = new ArrayList<String>();
        List<String> booksAuthors = new ArrayList<String>();
        List<String> booksSubjects = new ArrayList<String>();

        // Adding books
        BookObject book = new BookObject("Mindset", "Carol S. Dweck", "Educational");
        BookObject book2 = new BookObject("Test", "te S. Dweck", "test");
        
        // Add books to list
        books.add(book);
        books.add(book2);
        
        // Loop through list and adds book info to lists
        for (BookObject iBook : books) {
            String bookName = iBook.getName();
            String bookAuthor = iBook.getAuthor();
            String bookSubject = iBook.getSubject();

            booksNames.add(bookName);
            booksAuthors.add(bookAuthor);
            booksSubjects.add(bookSubject);
        }

        // Prints to console for testing
        System.out.println("Books names: " + booksNames + " authors " + booksAuthors + " subjects " + booksSubjects);
    }
}

// Book class
class BookObject {
    private String name;
    private String author;
    private String subject;
    
    public BookObject(String name, String author, String subject) {
        this.name = name;
        this.author = author;
        this.subject = subject;
    }

    public String getName() {
        return name;
    }

    public String getAuthor() {
        return author;
    }

    public String getSubject() {
        return subject;
    }
}
