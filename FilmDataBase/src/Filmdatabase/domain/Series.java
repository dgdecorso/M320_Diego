package Filmdatabase.domain;

public class Series extends MediaItem {

    private final int seasons;

    public Series(String title, int year, int seasons) {
        super(title, year);
        this.seasons = seasons;
    }

    @Override
    public String getDisplayTitle() {
        return title + " (" + seasons + " Staffeln)";
    }
}
