import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class App {
    public static void main(String[] args) {
        // Lists
        List<BookObject> books = new ArrayList<BookObject>();

        // Adding books
        BookObject book = new BookObject("Mindset", "Carol S. Dweck", "Educational");
        BookObject book2 = new BookObject("Test", "Test", "test");
        
        // Add books to list
        books.add(book);
        books.add(book2);
      
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter a book name");
        String name = sc.next();

        Pattern pattern = Pattern.compile("\\b" + name + "\\b", Pattern.CASE_INSENSITIVE);
        boolean found = false;
        
        // Loop through list and adds book info to lists
        for (BookObject iBook : books) {
            String bookName = iBook.getName();
            String bookAuthor = iBook.getAuthor();
            String bookSubject = iBook.getSubject();

            Matcher matcher = pattern.matcher(bookName);
            while (matcher.find()) {
                System.out.println("Found book: " + matcher.group() + " by " + bookAuthor + ", subject " + bookSubject);
                found = true;
            }
        }
        
        if (!found) {
            System.out.println("No matching book found.");
        }

        // Prints to console for testing
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
