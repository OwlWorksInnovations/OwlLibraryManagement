import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;
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
                String uuidString = tokens[2];

                UserObject user = new UserObject(username, password, uuidString, null, null);
                users.add(user);

                System.out.println(data);
            }
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        File booksFile = new File("books.txt");
        try (Scanner booksScanner = new Scanner(booksFile)) {
            while (booksScanner.hasNextLine()) {
                String data = booksScanner.nextLine();
                String[] tokens = data.split(",");
                String bookName = tokens[0];
                String bookAuthor = tokens[1];
                String bookSubject = tokens[2];
                String bookUUID = tokens[3];

                BookObject book = new BookObject(bookName, bookAuthor, bookSubject, bookUUID, false);
                books.add(book);

                System.out.println(data);
            }
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        
        File borrowedFile = new File("borrowed.txt");
        try (Scanner borrowedScanner = new Scanner(borrowedFile)) {
            while (borrowedScanner.hasNextLine()) {
                String data = borrowedScanner.nextLine();
                String[] tokens = data.split(",");
                String borrowedBookUUID = tokens[0];
                String borrowingUserUUID = tokens[1];
                BookObject borrowedBook = null;

                for (BookObject book : books) {
                    if (book.getUUID().equals(borrowedBookUUID)) {
                        book.setBorrowed(true);
                        borrowedBook = book;
                    }
                }

                for (UserObject user : users) {
                    if (user.getUUID().equals(borrowingUserUUID)) {
                        List<BookObject> books = user.getBorrowedBooks();
                        for (BookObject book : books) {
                            if (book.equals(borrowedBook)) {
                                return;
                            } else {
                                user.addBorrowedBook(borrowedBook);
                            }
                        }
                    }
                }

                System.out.println(data);
            }
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }


        File borrowedHistoryFile = new File("borrowed_history.txt");
        try (Scanner borrowedHistoryScanner = new Scanner(borrowedHistoryFile)) {
            while (borrowedHistoryScanner.hasNextLine()) {
                String data = borrowedHistoryScanner.nextLine();
                String[] tokens = data.split(",");
                String borrowedHistoryBookUUID = tokens[0];
                String borrowingHistoryUserUUID = tokens[1];
                BookObject borrowedHistoryBook = null;

                for (UserObject user : users) {
                    for (BookObject book : books) {
                        if (book.getUUID().equals(borrowedHistoryBookUUID)) {
                            borrowedHistoryBook = book;
                        }
                    }
                    if (user.getUUID().equals(borrowingHistoryUserUUID)) {
                        user.addBorrowedHistoryBook(borrowedHistoryBook);
                    }
                }

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
                    case 1 -> login();
                    case 2 -> register();
                    case 3 -> listUsers();
                    case 0 -> running = exitProgram();
                    default -> System.out.println("Invalid choice or input!");
                }
            } else if (loggedin) {
                displayLibraryMenu();
                Integer choice = getIntInput();

                switch (choice) {
                    case 1 -> searchBook();
                    case 2 -> borrowBook();
                    case 3 -> returnBook();
                    case 4 -> addBook();
                    case 5 -> bookHistory();
                    case 0 -> running = exitProgram();
                    default -> System.out.println("Invalid choice!");
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
        String selectedBookInfo = selectedBook != null
            ? selectedBook.getName() + " by " + selectedBook.getAuthor()
            : "None";

        System.out.println("\nChoose a menu option:");
        System.out.println("[1] Select book");
        System.out.println("[2] Borrow selected book");
        System.out.println("[3] Return book");
        System.out.println("[4] Add book");
        System.out.println("[5] Show book history");
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

        UUID uuid = UUID.randomUUID();
        String uuidString = uuid.toString();
        UserObject user = new UserObject(username, password, uuidString, null , null);

        try {
            File usersFile = new File("users.txt");
            if (usersFile.createNewFile()) {
                System.out.println("File created: " + usersFile.getName());

                FileWriter usersFileWriter = new FileWriter(usersFile.getName(), true);
                usersFileWriter.write(username + "," + password + "," + uuidString + "\n");
                usersFileWriter.close();
                users.add(user);
            } else {
                FileWriter usersFileWriter = new FileWriter(usersFile.getName(), true);
                System.out.println("File already exists.");
                usersFileWriter.write(username + "," + password + "," + uuidString + "\n");
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
        if (selectedBook == null) {
            System.out.println("Select a book first.");
            return;
        }
        if (selectedBook.getBorrowed()) {
            System.out.println("Book already borrowed.");
            return;
        }

        currentUser.addBorrowedBook(selectedBook);
        selectedBook.setBorrowed(true);

        try (FileWriter writer = new FileWriter("borrowed.txt", true)) {
            writer.write(selectedBook.getUUID() + "," + currentUser.getUUID() + "\n");
        } catch (IOException e) {
            System.out.println("Error writing borrowed.txt");
        }

        try (FileWriter writer = new FileWriter("borrowed_history.txt", true)) {
            writer.write(selectedBook.getUUID() + "," + currentUser.getUUID() + "\n");
        } catch (IOException e) {
            System.out.println("Error writing borrowed_history.txt");
        }

        System.out.println("Book borrowed: " + selectedBook.getName());
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

        UUID uuidBook = UUID.randomUUID();
        String uuidBookString = uuidBook.toString();

        // Adding books
        BookObject book = new BookObject(bookName, bookAuthor, bookSubject, uuidBookString, false);

        try {
            File booksFile = new File("books.txt");
            if (booksFile.createNewFile()) {
                System.out.println("File created: " + booksFile.getName());

                FileWriter booksFileWriter = new FileWriter(booksFile.getName(), true);
                booksFileWriter.write(bookName + "," + bookAuthor + "," + bookSubject + ',' + uuidBookString + "\n");
                booksFileWriter.close();
                books.add(book);
            } else {
                FileWriter booksFileWriter = new FileWriter(booksFile.getName(), true);
                System.out.println("File already exists!");
                booksFileWriter.write(bookName + "," + bookAuthor + "," + bookSubject + ',' + uuidBookString + "\n");
                booksFileWriter.close();
                books.add(book);
            }
        } catch (IOException e) {
            System.out.println("An error occured!");
            e.printStackTrace();
        }

        // Add books to list
        books.add(book);
    }

    private static void bookHistory() {
        for (UserObject user : users) {
            List<BookObject> books = user.getBorrowedHistorBooks();
            for (BookObject book : books) {
                System.out.println(book.getName() + " by " + book.getAuthor());
            }
            
        }
    }

    private static void returnBook() {
        if (selectedBook == null) {
            System.out.println("Select a book first.");
            return;
        }
        if (!selectedBook.getBorrowed()) {
            System.out.println("Book is not being borrowed.");
            return;
        }

        currentUser.removeBorrowedBook(selectedBook);
        selectedBook.setBorrowed(false);

        File borrowedFile = new File("borrowed.txt");
        List<String> remaining = new ArrayList<>();

        try (Scanner scanner = new Scanner(borrowedFile)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (!line.startsWith(selectedBook.getUUID() + ",")) {
                    remaining.add(line);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading borrowed.txt");
            return;
        }

        try (FileWriter writer = new FileWriter("borrowed.txt", false)) {
            for (String line : remaining) {
                writer.write(line + "\n");
            }
        } catch (IOException e) {
            System.out.println("Error writing borrowed.txt");
        }

        System.out.println("Book returned: " + selectedBook.getName());
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
    private String uuidString;
    private List<BookObject> borrowedBooks;
    private List<BookObject> borrowedHistoryBooks;

    public UserObject(String username, String password, String uuidString, List<BookObject> borrowedBooks, List<BookObject> borrowedHistoryBooks) {
        this.username = username;
        this.password = password;
        this.uuidString = uuidString;
        this.borrowedBooks = new ArrayList<>();
        this.borrowedHistoryBooks = new ArrayList<>();
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getUUID() {
        return uuidString;
    }

    public List<BookObject> getBorrowedBooks() {
        return borrowedBooks;
    }

    public List<BookObject> getBorrowedHistorBooks() {
        return borrowedHistoryBooks;
    }

    public void addBorrowedBook(BookObject book) {
        this.borrowedBooks.add(book);
    }

    public void removeBorrowedBook(BookObject book) {
        this.borrowedBooks.remove(book);
    }

    public void addBorrowedHistoryBook(BookObject book) {
        this.borrowedHistoryBooks.add(book);
    }
}

// Book object
class BookObject {
    private String name;
    private String author;
    private String subject;
    private String uuidBookString;
    private boolean borrowed;
    
    public BookObject(String name, String author, String subject, String uuidBookString, boolean borrowed) {
        this.name = name;
        this.author = author;
        this.subject = subject;
        this.uuidBookString = uuidBookString;
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

    public String getUUID() {
        return uuidBookString;
    }

    public boolean getBorrowed() {
        return borrowed;
    }
    
    public void setBorrowed(boolean borrowed) {
        this.borrowed = borrowed;
    }
}
