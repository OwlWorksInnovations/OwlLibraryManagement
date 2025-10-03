import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class App {
    private static List<BookObject> books = new ArrayList<BookObject>();
    private static List<UserObject> users = new ArrayList<UserObject>();
    private static UserObject currentUser = null;
    private static Scanner sc = new Scanner(System.in);
    private static BookObject selectedBook = null;
    private static Boolean loggedin = false;
    public static void main(String[] args) {
        File userFile = new File("users.txt");
        try (Scanner userScanner = new Scanner(userFile)) {
            while (userScanner.hasNextLine()) {
                String data = userScanner.nextLine();
                String[] tokens = data.split(",");
                String username = tokens[0];
                String password = tokens[1];

                UserObject user = new UserObject(username, password, null);
                users.add(user);

                System.out.println(data);
            }
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        Boolean running = true;

        while (running) {
            if (!loggedin) {
                // Choice menu
                displayLoginMenu();
                Integer choice = getIntInput();

                switch (choice) {
                    case 1:
                        login();
                        break;
                    case 2:
                        register();
                        break;
                    case 3:
                        listUsers();
                        break;
                    case 0:
                        running = exitProgram();
                        break;
                    default:
                        System.out.println("Invalid choice or input!");
                }
            } else if (loggedin) {
                displayLibraryMenu();
                Integer choice = getIntInput();

                switch (choice) {
                    case 1:
                        borrowBook();
                        break;
                    case 2:
                        returnBook();
                        break;
                    case 3:
                        addBook();
                        break;
                    case 0:
                        running = exitProgram();
                        break;
                    default:
                        System.out.println("Invalid choice or input!");
                }
            }
        }
        
        sc.close();
    }

    private static int getIntInput() {
        try {
            return Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private static void displayLoginMenu() {
        System.out.println("\nChoose a menu option:");
        System.out.println("[1] Login");
        System.out.println("[2] Register");
        System.out.println("[3] List users");
        System.out.println("[0] Exit");
        System.out.print("> ");
    }

    private static void displayLibraryMenu() {
        String selectedBookInfo;
        if (selectedBook != null) {
            selectedBookInfo = selectedBook.getName() + " by " + selectedBook.getAuthor();
        } else {
            selectedBookInfo = "None";
        }

        System.out.println("\nChoose a menu option:");
        System.out.println("[1] Borrow book");
        System.out.println("[2] Return book");
        System.out.println("[3] Add book");
        System.out.println("[0] Exit");
        System.out.println("Selected book: " + selectedBookInfo);
        System.out.print("> ");
    }

    private static void login() {
        System.out.println("\nEnter username:");
        System.out.print("> ");
        String username = sc.nextLine();
        System.out.println("\nEnter password:");
        System.out.print("> ");
        String password = sc.nextLine();

        for (UserObject user : users) {
            if (user.getUsername().equals(username)) {
                if (user.getPassword().equals(password)) {
                    loggedin = true;
                    currentUser = user;
                }
            }
        }
    }

    private static void register() {
        System.out.println("\nEnter username:");
        System.out.print("> ");
        String username = sc.nextLine();
        System.out.println("\nEnter password:");
        System.out.print("> ");
        String password = sc.nextLine();
        UserObject user = new UserObject(username, password, null);

        try {
            File usersFile = new File("users.txt");
            if (usersFile.createNewFile()) {
                System.out.println("File created: " + usersFile.getName());

                FileWriter usersFileWriter = new FileWriter(usersFile.getName(), true);
                usersFileWriter.write(username + "," + password + "\n");
                usersFileWriter.close();
                users.add(user);
            } else {
                FileWriter usersFileWriter = new FileWriter(usersFile.getName(), true);
                System.out.println("File already exists.");
                usersFileWriter.write(username + "," + password + "\n");
                usersFileWriter.close();
                users.add(user);
            }
        } catch (IOException e) {
            System.out.println("An error occured!");
            e.printStackTrace();
        }  
    }

    private static void listUsers() {
        for (UserObject user : users) {
            System.out.println(user.getUsername());
        }
    }

    private static void borrowBook() {
        String selectedBookInfo;
        if (selectedBook != null) {
            selectedBookInfo = selectedBook.getName() + " by " + selectedBook.getAuthor();
        } else {
            selectedBookInfo = "None";
        }

        System.out.println("\nChoose a menu option:");
        System.out.println("[1] Search book");
        System.out.println("[2] List books");
        System.out.println("[3] Borrow selected book");
        System.out.println("Selected book: " + selectedBookInfo);
        System.out.print("> ");
        Integer choice = getIntInput();

        switch (choice) {
            case 1:
                searchBook();
                break;
            case 2:
                listBooks();
                break;
            case 3:
                if (selectedBook != null) {
                    if (!selectedBook.getBorrowed()) {
                        currentUser.addBorrowedBook(selectedBook);
                        selectedBook.setBorrowed(true);
                        System.out.println("Book borrowed: " + selectedBook.getBorrowed() + ", " + selectedBook + ", " + currentUser.getBorrowedBooks());
                    } else {
                        System.out.println("Book is already borrowed!");
                    }
                } else {
                    System.out.println("No book selected!");
                }
                break;
            default:
                System.out.println("Invalid choice or input!");
        }
    }

    private static void searchBook() {
        // Get info
        System.out.println("\nEnter a book name to search.");
        System.out.print("> ");
        String name = sc.nextLine(); 

        // Patern searching
        Pattern searchPattern = Pattern.compile("\\b" + name + "\\b", Pattern.CASE_INSENSITIVE);

        // For if statments
        boolean found = false;
        BookObject matchedBook = null;

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
                matchedBook = iBook;
                found = true;
            }
        }

        // Handles invalid or wrong input
        if (!found) {
            System.out.println("No matching book found.");
        } else {
            System.out.println("\nDo you want to select this book?");
            System.out.println("[1] Yes");
            System.out.println("[2] No");
            System.out.print("> ");
            Integer selectChoice = Integer.parseInt(sc.nextLine());
            switch (selectChoice) {
                case 1:
                    selectedBook = matchedBook;
                    System.out.println("Selected book: " + selectedBook.getName() + " by " + selectedBook.getAuthor() + ", subject " + selectedBook.getSubject());
                    break;
                case 2:
                    break;
                default:
                    System.out.println("Invalid choice or input!");
            }
        }
    }

    private static void listBooks() {
        // Loop through list and finds book through pattern matching
        java.util.Iterator<BookObject> iterator = books.iterator();
        while (iterator.hasNext()) {
            BookObject iBook = iterator.next();
            String bookName = iBook.getName();
            String bookAuthor = iBook.getAuthor();
            String bookSubject = iBook.getSubject();

            System.out.println("Book: " + bookName + " by " + bookAuthor + ", subject " + bookSubject);
        }
    }

    private static void deleteBook() {
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
    }

    private static void addBook() {
        // Get book details
        System.out.println("\nEnter book name: ");
        System.out.print("> ");
        String bookName = sc.nextLine();
        System.out.println("\nEnter book author: ");
        System.out.print("> ");
        String bookAuthor = sc.nextLine();
        System.out.println("\nEnter book subject: ");
        System.out.print("> ");
        String bookSubject = sc.nextLine();

        // Adding books
        BookObject book = new BookObject(bookName, bookAuthor, bookSubject, false);

        // Add books to list
        books.add(book);
    }

    private static void returnBook() {
        String selectedBookInfo;
        if (selectedBook != null) {
            selectedBookInfo = selectedBook.getName() + " by " + selectedBook.getAuthor();
        } else {
            selectedBookInfo = "None";
        }

        System.out.println("\nChoose a menu option:");
        System.out.println("[1] Search book");
        System.out.println("[2] List books");
        System.out.println("[3] Return selected book");
        System.out.println("Selected book: " + selectedBookInfo);
        System.out.print("> ");
        Integer choice = getIntInput();

        switch (choice) {
            case 1:
                searchBook();
                break;
            case 2:
                listBooks();
                break;
            case 3:
                if (selectedBook != null) {
                    List<BookObject> borrowedBooks = currentUser.getBorrowedBooks();
                    if (borrowedBooks.contains(selectedBook)) {
                        currentUser.removeBorrowedBook(selectedBook);
                        selectedBook.setBorrowed(false);
                        System.out.println("Book returned: " + selectedBook.getBorrowed() + ", " + currentUser.getBorrowedBooks() + ", " + selectedBook);
                    } else {
                        System.out.println("Book is not being borrowed!");
                    }
                } else {
                    System.out.println("No book selected!");
                }
                break;
            default:
                System.out.println("Invalid choice or input!");
        }
    }

    private static boolean exitProgram() {
        System.out.println("Exiting...");
        System.out.println("Debug - Books in system: " + books);
        return false;
    }
}

// User object (member object)
class UserObject {
    private String username;
    private String password;
    private List<BookObject> borrowedBooks;

    public UserObject(String username, String password, List<BookObject> borrowedBooks) {
        this.username = username;
        this.password = password;
        this.borrowedBooks = new ArrayList<>();
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public List<BookObject> getBorrowedBooks() {
        return borrowedBooks;
    }

    public void addBorrowedBook(BookObject book) {
        this.borrowedBooks.add(book);
    }

    public void removeBorrowedBook(BookObject book) {
        this.borrowedBooks.remove(book);
    }
}

// Book object
class BookObject {
    private String name;
    private String author;
    private String subject;
    private boolean borrowed;
    
    public BookObject(String name, String author, String subject, boolean borrowed) {
        this.name = name;
        this.author = author;
        this.subject = subject;
        this.borrowed = borrowed;
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

    public boolean getBorrowed() {
        return borrowed;
    }
    
    public void setBorrowed(boolean borrowed) {
        this.borrowed = borrowed;
    }
}
