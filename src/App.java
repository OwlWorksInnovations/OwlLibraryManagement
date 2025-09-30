public class App {
    public static void main(String[] args) {
        BookObject book = new BookObject("Mindset", "Carol S. Dweck", "Educational");
        System.out.println("Book Created: " + book.getName() + " by " + book.getAuthor() + " for " + book.getSubject());
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
