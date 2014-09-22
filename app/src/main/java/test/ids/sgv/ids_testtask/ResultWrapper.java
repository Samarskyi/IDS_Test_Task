package test.ids.sgv.ids_testtask;

/**
 * Created by sgv on 21.09.14.
 */
public class ResultWrapper {

    String url;
    String title;

    public ResultWrapper() {
    }

    public ResultWrapper(String url, String title) {
        this.url = url;
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "ResultWrapper{" +
                "url='" + url + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
