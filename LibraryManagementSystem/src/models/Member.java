package models;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class Member {
    private String name;
    private int memberID;
    private List<Book> borrowedBooks; // List to store borrowed books

    // Constructor
    public Member(String name, int memberID) {
        this.name = name;
        this.memberID = memberID;
        this.borrowedBooks = new ArrayList<>();
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMemberID() {
        return memberID;
    }

    public void setMemberID(int memberID) {
        this.memberID = memberID;
    }

    // Method to get the list of borrowed books
    public List<Book> getBorrowedBooks() {
        return borrowedBooks;
    }

    // Method to borrow a book
    public void borrowBook(Book book) {
        borrowedBooks.add(book);
    }

    // Method to return a book
    public void returnBook(Book book) {
        borrowedBooks.remove(book);
    }

    // Method to print borrowed books using ListIterator
    public void printBorrowedBooks() {
        ListIterator<Book> iterator = borrowedBooks.listIterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }

    @Override
    public String toString() {
        return name + " (ID: " + memberID + ")";
    }
}
