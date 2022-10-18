import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class CatalogItem {
    private String code;
    private String title;
    private Date publishDate;

    CatalogItem() {

    }

    CatalogItem(String code, String title, Date publishDate) {
        this.code = code;
        this.title = title;
        this.publishDate = publishDate;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    public abstract String getType();

    @Override
    public String toString() {
        return "CatalogItem_" + code + '_'  + title + '_'
                + new SimpleDateFormat(LibrarySystem.DATE_FORMAT_YMD).format(publishDate);
    }

}
