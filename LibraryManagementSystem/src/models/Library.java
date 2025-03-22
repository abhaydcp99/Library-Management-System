package models;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class Library {
    private Map<String, Book> books; // Map to store books by ISBN
    private Set<Member> members;    // Set to store members

    // Constructor
    public Library() {
        this.books = new HashMap<>();
        this.members = new HashSet<>();
    }

    // Method to add a book
    public void addBook(Book book) {
        books.put(book.getISBN(), book);
    }

    // Method to add a member
    public void addMember(Member member) {
        members.add(member);
    }

    // Method to display all books using Iterator
    public void displayBooks() {
        Iterator<Book> iterator = books.values().iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }

    // Method to search for a book by ISBN
    public Book searchBook(String ISBN) {
        return books.get(ISBN);
    }

    // Method to get the total number of members
    public int getTotalMembers() {
        return members.size();
    }

    // Method to get the total number of books
    public int getTotalBooks() {
        return books.size();
    }

    // Method to get the number of currently borrowed books
    public int getBorrowedBooksCount() {
        int count = 0;
        for (Book book : books.values()) {
            if (!book.isAvailable()) {
                count++;
            }
        }
        return count;
    }

    // Getter for members
    public Set<Member> getMembers() {
        return members;
    }
}