package model;

public class Magazine extends LibraryItem {
    private int issueNumber;
    private String publicationDate;

    public Magazine(int id, String title, int issueNumber, String publicationDate, int copies) {
        super(id, title, copies);
        this.issueNumber = issueNumber;
        this.publicationDate = publicationDate;
    }

    @Override
    public String getItemDetails() {
        return "Magazine [id=" + getId() +
                ", title=" + getTitle() +
                ", issue=" + issueNumber +
                ", date=" + publicationDate +
                ", available=" + getAvailableCopies() + "/" + getTotalCopies() + "]";
    }
}
