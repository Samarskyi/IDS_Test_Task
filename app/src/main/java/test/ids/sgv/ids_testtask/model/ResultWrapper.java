package test.ids.sgv.ids_testtask.model;

/**
 * Created by sgv on 21.09.14.
 */
public class ResultWrapper {

    private String url;
    private String title;
    private boolean isChecked;

    public ResultWrapper() {
    }

    public ResultWrapper(String url, String title) {
        this.url = url;
        this.title = title;
        this.isChecked = false;
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

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean isChecked) {
        this.isChecked = isChecked;
    }

    @Override
    public String toString() {
        return "ResultWrapper{" +
                "url='" + url + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
