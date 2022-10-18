import java.text.SimpleDateFormat;
import java.util.Date;

public class Recording extends CatalogItem {
    private String performer;
    private String format;
    private static final String type = "Recording";

    public Recording() {
    }

    public Recording(String code, String title, Date publishDate, String performer, String format) {
        super(code, title, publishDate);
        this.performer = performer;
        this.format = format;
    }

    public String getPerformer() {
        return this.performer;
    }

    public void setPerformer(String performer) {
        this.performer = performer;
    }

    public String getFormat() {
        return this.format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return type + "_" + this.getCode() + "_"
                + this.getTitle() + "_" + new SimpleDateFormat(LibrarySystem.DATE_FORMAT_YMD).format(this.getPublishDate())
                + "_" + this.performer + "_" + this.format;
    }
}
