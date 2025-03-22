package services;

import models.Book;
import models.Library;
import models.Member;
import exceptions.*;

public class LibraryService {
    private Library library;

    // Constructor
    public LibraryService(Library library) {
        this.library = library;
    }

    // Method to borrow a book
    public void borrowBook(int memberID, String ISBN) throws BookNotAvailableException, MemberNotFoundException, BookAlreadyBorrowedException {
        // Step 1: Find the book by ISBN
        Book book = library.searchBook(ISBN);
        if (book == null) {
            throw new BookNotAvailableException("Book with ISBN " + ISBN + " not found.");
        }

        // Step 2: Check if the book is available
        if (!book.isAvailable()) {
            throw new BookNotAvailableException("Book is not available for borrowing.");
        }

        // Step 3: Find the member by ID
        Member member = findMemberByID(memberID);
        if (member == null) {
            throw new MemberNotFoundException("Member with ID " + memberID + " not found.");
        }

        // Step 4: Check if the member has already borrowed the book
        if (member.getBorrowedBooks().contains(book)) {
            throw new BookAlreadyBorrowedException("You have already borrowed this book.");
        }

        // Step 5: Borrow the book
        book.setAvailable(false); // Mark the book as unavailable
        member.borrowBook(book);  // Add the book to the member's borrowed list
    }

    // Method to return a book
    public void returnBook(int memberID, String ISBN) throws MemberNotFoundException, BookNotAvailableException {
        // Step 1: Find the book by ISBN
        Book book = library.searchBook(ISBN);
        if (book == null) {
            throw new BookNotAvailableException("Book with ISBN " + ISBN + " not found.");
        }

        // Step 2: Find the member by ID
        Member member = findMemberByID(memberID);
        if (member == null) {
            throw new MemberNotFoundException("Member with ID " + memberID + " not found.");
        }

        // Step 3: Check if the member borrowed the book
        if (!member.getBorrowedBooks().contains(book)) {
            throw new BookNotAvailableException("You did not borrow this book.");
        }

        // Step 4: Return the book
        book.setAvailable(true); // Mark the book as available
        member.returnBook(book); // Remove the book from the member's borrowed list
    }

    // Method to find a member by ID
    private Member findMemberByID(int memberID) {
        for (Member member : library.getMembers()) {
            if (member.getMemberID() == memberID) {
                return member;
            }
        }
        return null; // Return null if the member is not found
    }

    // Method to print library statistics
    public void printStatistics() {
        System.out.println("Total Members: " + library.getTotalMembers());
        System.out.println("Total Books: " + library.getTotalBooks());
        System.out.println("Currently Borrowed Books: " + library.getBorrowedBooksCount());
    }
}