import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class App {
    public static void main(String[] args) {
        // Lists
        List<BookObject> books = new ArrayList<BookObject>();
        Boolean running = true;

        // Gets user input from console
        Scanner sc = new Scanner(System.in);
        while (running) {
            // Choice menu
            System.out.println("Choose a menu option. [1] Search book [2] Delete book [3] List books [4] Add book [5] Exit");
            Integer choice = Integer.parseInt(sc.nextLine());

            if (choice == 1) {
                // Get info
                System.out.println("Enter a book name to search.");
                String name = sc.nextLine(); 

                // Patern searching
                Pattern searchPattern = Pattern.compile("\\b" + name + "\\b", Pattern.CASE_INSENSITIVE);

                // For if statments
                boolean found = false;

                // Loop through list and finds book through pattern matching
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
                }
                // Handles invalid or wrong input
                if (!found) {
                    System.out.println("No matching book found.");
                }
            } else if (choice == 2) {
                // Get info
                System.out.println("Enter a book name to delete."); 
                String dName = sc.nextLine();
                
                // Pattern searching
                Pattern deletePattern = Pattern.compile("\\b" + dName + "\\b", Pattern.CASE_INSENSITIVE);
                
                // For if statements
                boolean dFound = false;
                
                // Loop through list and finds book through pattern matching
                java.util.Iterator<BookObject> iterator = books.iterator();
                while (iterator.hasNext()) {
                    BookObject iBook = iterator.next();
                    String bookName = iBook.getName();
                    String bookAuthor = iBook.getAuthor();
                    String bookSubject = iBook.getSubject();

                    Matcher deleteMatcher = deletePattern.matcher(bookName);
                    while (deleteMatcher.find()) {
                        System.out.println("Deleting book: " + deleteMatcher.group() + " by " + bookAuthor + ", subject " + bookSubject);
                        iterator.remove();
                        dFound = true;
                    }
                }

                // Handles invalid or wrong input
                if (!dFound) {
                    System.out.println("No matching book found for deletion.");
                }
            } else if (choice == 3) {
                // Loop through list and finds book through pattern matching
                java.util.Iterator<BookObject> iterator = books.iterator();
                while (iterator.hasNext()) {
                    BookObject iBook = iterator.next();
                    String bookName = iBook.getName();
                    String bookAuthor = iBook.getAuthor();
                    String bookSubject = iBook.getSubject();

                    System.out.println("Book: " + bookName + " by " + bookAuthor + ", subject " + bookSubject);
                }
            } else if (choice == 4) {
                // Get book details
                System.out.println("Enter book name: ");
                String bookName = sc.nextLine();
                System.out.println("Enter book author: ");
                String bookAuthor = sc.nextLine();
                System.out.println("Enter book subject: ");
                String bookSubject = sc.nextLine();

                // Adding books
                BookObject book = new BookObject(bookName, bookAuthor, bookSubject);
                
                // Add books to list       
                books.add(book);
            } else if (choice == 5) {
                System.out.println("Exiting...");
                running = false;
            } else {
                System.out.println("Invalid choice or input.");
            }
        }
        
        // Debug printing
        System.out.println(books);
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
