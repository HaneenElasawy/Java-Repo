package model;

public class Book extends LibraryItem {
    private String author;
    private int pages;

    public Book(int id, String title, String author, int pages, int copies) {
        super(id, title, copies);
        this.author = author;
        this.pages = pages;
    }

    @Override
    public String getItemDetails() {
        return "Book [id=" + getId() +
                ", title=" + getTitle() +
                ", author=" + author +
                ", pages=" + pages +
                ", available=" + getAvailableCopies() + "/" + getTotalCopies() + "]";
    }
}
