
# Owl Library Management System

Owl Library Management is a Java-based application for managing books and members in a library. It provides a simple menu-driven interface for common library operations.

## Features

### Completed
- Add a new book (title, author, ISBN)
- View all books
- Search books by title or author
- Remove a book
- Option menu (add book, view books, delete book)
- Track book availability (borrowed or not)
- Borrow a book (if available)
- Register new member (name, member ID)
- View all registered members
- Assign borrowed books to a member
- Return a borrowed book
- Prevent borrowing if the book is already borrowed
- Save books and members to file
- Load books and members from file on startup
- Validate inputs and handle errors gracefully
- Handle user input and provide feedback
- Auto-generate unique IDs for books and members

### In Progress / Planned
- Track due dates or borrowing history
- Admin/member roles (e.g., login system)
- Switch to GUI (JavaFX/Swing)
- Unit testing for core logic

## Folder Structure

- `src/` - Java source files
- `bin/` - Compiled class files

## Getting Started

1. Compile the Java source files:
	```powershell
	javac -d bin src/*.java
	```
2. Run the application:
	```powershell
	java -cp bin App
	```

## Contributing

See the TODO file for planned features. Contributions are welcome!
