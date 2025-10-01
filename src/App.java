import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        // Lists
        List<BookObject> books = new ArrayList<BookObject>();
        List<String> booksNames = new ArrayList<String>();
        List<String> booksAuthors = new ArrayList<String>();
        List<String> booksSubjects = new ArrayList<String>();

        // Adding books
        BookObject book = new BookObject("Mindset", "Carol S. Dweck", "Educational");
        BookObject book2 = new BookObject("Test", "Test", "test");
        
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
        
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter a book name");
        String name = sc.next();
        
        boolean found = false;
        for (String iBookName : booksNames) {
            found = iBookName.contains(name);
            if (found) {
                break;
            }
        }

        // Prints to console for testing
        System.out.println("Books names: " + booksNames + " authors " + booksAuthors + " subjects " + booksSubjects);

        if (found) {
            System.out.println(name);
        } else {
            System.out.println("Could not find a book.");
        }
        sc.close();
    }
}

// Book object
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
