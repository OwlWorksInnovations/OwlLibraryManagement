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
        
        // Gets user input from console
        Scanner sc = new Scanner(System.in); System.out.println("Enter a book name to search.");
        String name = sc.next(); System.out.println("Enter a book name to delete."); 
        String dName = sc.next();
        
        // Pattern searching for finding books and deleting them
        Pattern searchPattern = Pattern.compile("\\b" + name + "\\b", Pattern.CASE_INSENSITIVE);
        Pattern deletePattern = Pattern.compile("\\b" + dName + "\\b", Pattern.CASE_INSENSITIVE);
        
        // For if statements
        boolean found = false;
        boolean dFound = false;
        
        // Loop through list and adds book info to lists
        java.util.Iterator<BookObject> iterator = books.iterator();
        while (iterator.hasNext()) {
            BookObject iBook = iterator.next();
            String bookName = iBook.getName();
            String bookAuthor = iBook.getAuthor();
            String bookSubject = iBook.getSubject();

            Matcher searchMatcher = searchPattern.matcher(bookName);
            while (searchMatcher.find()) {
                System.out.println("Found book: " + searchMatcher.group() + " by " + bookAuthor + ", subject " + bookSubject);
                found = true;
            }
            
            Matcher deleteMatcher = deletePattern.matcher(bookName);
            while (deleteMatcher.find()) {
                System.out.println("Deleting book: " + deleteMatcher.group() + " by " + bookAuthor + ", subject " + bookSubject);
                iterator.remove();
                dFound = true;
            }
        }
        
        if (!found) {
            System.out.println("No matching book found.");
        }

        if (!dFound) {
            System.out.println("No matching book found for deletion.");
        }

        // Debug printing
        System.out.println(books);

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
