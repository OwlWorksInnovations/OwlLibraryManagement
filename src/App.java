import java.util.ArrayList;
import java.util.List;

public class App {
    public static void main(String[] args) {
        List<BookObject> books = new ArrayList<BookObject>();
        List<String> booksNames = new ArrayList<String>();
        List<String> booksAuthors = new ArrayList<String>();
        List<String> booksSubjects = new ArrayList<String>();

        BookObject book = new BookObject("Mindset", "Carol S. Dweck", "Educational");
        BookObject book2 = new BookObject("Test", "te S. Dweck", "test");

        books.add(book);
        books.add(book2);

        for (BookObject iBook : books) {
            String bookName = iBook.getName();
            String bookAuthor = iBook.getAuthor();
            String bookSubject = iBook.getSubject();

            booksNames.add(bookName);
            booksAuthors.add(bookAuthor);
            booksSubjects.add(bookSubject);
        }
        System.out.println("Books names: " + booksNames + " authors " + booksAuthors + " subjects " + booksSubjects);
    }
}

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
