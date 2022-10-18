import java.text.SimpleDateFormat;
import java.util.Date;

public class Book extends CatalogItem {
    private String author;
    private int numberOfPages;
    private static final String type = "Book";

    public Book() {

    }

    public Book(String code, String title, Date publishDate, String author, int numberOfPages) {
        super(code, title, publishDate);
        this.author = author;
        this.numberOfPages = numberOfPages;
    }

    public String getAuthor() {
        return this.author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getNumberOfPages() {
        return numberOfPages;
    }

    public void setNumberOfPages(int numberOfPages) {
        this.numberOfPages = numberOfPages;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return type + "_" + this.getCode() + "_"
                + this.getTitle() + "_" + new SimpleDateFormat(LibrarySystem.DATE_FORMAT_YMD).format(this.getPublishDate())
                + "_" + this.author + "_" + this.numberOfPages;
    }

}
