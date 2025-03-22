package ui;

import models.Book;
import models.Library;
import models.Member;
import exceptions.*;
import java.util.InputMismatchException;
import java.util.Scanner;

public class LibraryManagementSystem {
    private Library library;
    private Scanner scanner;

    // Constructor
    public LibraryManagementSystem() {
        this.library = new Library();
        this.scanner = new Scanner(System.in);
    }

    // Main method to run the program
    public static void main(String[] args) {
        LibraryManagementSystem system = new LibraryManagementSystem();
        system.run();
    }

    // Method to run the library management system
    public void run() {
        while (true) {
            try {
                // Display menu options
                System.out.println("\n--- Library Management System ---");
                System.out.println("1. Add Book");
                System.out.println("2. Add Member");
                System.out.println("3. Display Books");
                System.out.println("4. Borrow Book");
                System.out.println("5. Return Book");
                System.out.println("6. Display Borrowed Books");
                System.out.println("7. Display Library Statistics");
                System.out.println("8. Exit");
                System.out.print("Choose an option: ");

                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch (choice) {
                    case 1:
                        addBook();
                        break;
                    case 2:
                        addMember();
                        break;
                    case 3:
                        displayBooks();
                        break;
                    case 4:
                        borrowBook();
                        break;
                    case 5:
                        returnBook();
                        break;
                    case 6:
                        displayBorrowedBooks();
                        break;
                    case 7:
                        printStatistics();
                        break;
                    case 8:
                        System.out.println("Exiting...");
                        scanner.close();
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Error: Invalid option. Please try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Error: Invalid input. Please enter a number.");
                scanner.nextLine(); // Clear the invalid input
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    // Method to add a book
    private void addBook() {
        System.out.print("Enter title: ");
        String title = scanner.nextLine();
        System.out.print("Enter author: ");
        String author = scanner.nextLine();
        System.out.print("Enter ISBN: ");
        String ISBN = scanner.nextLine();
        library.addBook(new Book(title, author, ISBN));
        System.out.println("Book added: " + title + " by " + author + " (ISBN: " + ISBN + ")");
    }

    // Method to add a member
    private void addMember() {
        System.out.print("Enter name: ");
        String name = scanner.nextLine();
        System.out.print("Enter member ID: ");
        int memberID = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        library.addMember(new Member(name, memberID));
        System.out.println("Member added: " + name + " (ID: " + memberID + ")");
    }

    // Method to display all books
    private void displayBooks() {
        System.out.println("\nAvailable Books:");
        library.displayBooks();
    }

    // Method to borrow a book
    private void borrowBook() {
        System.out.print("Enter member ID: ");
        int memberID = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        System.out.print("Enter ISBN: ");
        String ISBN = scanner.nextLine();
        try {
            borrowBook(memberID, ISBN);
            System.out.println("Book borrowed successfully.");
        } catch (BookNotAvailableException | MemberNotFoundException | BookAlreadyBorrowedException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // Method to return a book
    private void returnBook() {
        System.out.print("Enter member ID: ");
        int memberID = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        System.out.print("Enter ISBN: ");
        String ISBN = scanner.nextLine();
        try {
            returnBook(memberID, ISBN);
            System.out.println("Book returned successfully.");
        } catch (MemberNotFoundException | BookNotAvailableException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // Method to display borrowed books for a member
    private void displayBorrowedBooks() {
        System.out.print("Enter member ID: ");
        int memberID = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        Member member = findMemberByID(memberID);
        if (member != null) {
            System.out.println("\nBorrowed Books:");
            member.printBorrowedBooks();
        } else {
            System.out.println("Error: Member not found.");
        }
    }

    // Method to print library statistics
    public void printStatistics() {
        System.out.println("Total Members: " + library.getTotalMembers());
        System.out.println("Total Books: " + library.getTotalBooks());
        System.out.println("Currently Borrowed Books: " + library.getBorrowedBooksCount());
    }

    // Method to borrow a book (internal logic)
    public void borrowBook(int memberID, String ISBN) throws BookNotAvailableException, MemberNotFoundException, BookAlreadyBorrowedException {
        Book book = library.searchBook(ISBN);
        if (book == null) {
            throw new BookNotAvailableException("Book with ISBN " + ISBN + " not found.");
        }

        if (!book.isAvailable()) {
            throw new BookNotAvailableException("Book is not available for borrowing.");
        }

        Member member = findMemberByID(memberID);
        if (member == null) {
            throw new MemberNotFoundException("Member with ID " + memberID + " not found.");
        }

        if (member.getBorrowedBooks().contains(book)) {
            throw new BookAlreadyBorrowedException("You have already borrowed this book.");
        }

        book.setAvailable(false);
        member.borrowBook(book);
    }

    // Method to return a book (internal logic)
    public void returnBook(int memberID, String ISBN) throws MemberNotFoundException, BookNotAvailableException {
        Book book = library.searchBook(ISBN);
        if (book == null) {
            throw new BookNotAvailableException("Book with ISBN " + ISBN + " not found.");
        }

        Member member = findMemberByID(memberID);
        if (member == null) {
            throw new MemberNotFoundException("Member with ID " + memberID + " not found.");
        }

        if (!member.getBorrowedBooks().contains(book)) {
            throw new BookNotAvailableException("You did not borrow this book.");
        }

        book.setAvailable(true);
        member.returnBook(book);
    }

    // Method to find a member by ID
    public Member findMemberByID(int memberID) {
        for (Member member : library.getMembers()) {
            if (member.getMemberID() == memberID) {
                return member;
            }
        }
        return null; // Return null if the member is not found
    }
}