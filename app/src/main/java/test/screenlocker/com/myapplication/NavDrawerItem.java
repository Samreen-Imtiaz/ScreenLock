package test.screenlocker.com.myapplication;

/**
 * Created by samreen on 10/19/2016.
 */
public class NavDrawerItem {
    private boolean showNotify;
    private String title;
    private String albumId, albumTitle;
    // boolean flag to check for recent album
    private boolean isRecentAlbum = false;

    public NavDrawerItem() {

    }

    public NavDrawerItem(boolean showNotify, String title) {
        this.showNotify = showNotify;
        this.title = title;
    }

    public boolean isShowNotify() {
        return showNotify;
    }

    public void setShowNotify(boolean showNotify) {
        this.showNotify = showNotify;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }



    public NavDrawerItem(String albumId, String albumTitle) {
        this.albumId = albumId;
        this.albumTitle = albumTitle;
    }

    public NavDrawerItem(String albumId, String albumTitle,
                         boolean isRecentAlbum) {
        this.albumTitle = albumTitle;
        this.isRecentAlbum = isRecentAlbum;
    }

    public String getAlbumId() {
        return albumId;
    }

    public void setAlbumId(String albumId) {
        this.albumId = albumId;
    }

    public boolean isRecentAlbum() {
        return isRecentAlbum;
    }

    public void setRecentAlbum(boolean isRecentAlbum) {
        this.isRecentAlbum = isRecentAlbum;
    }
}
