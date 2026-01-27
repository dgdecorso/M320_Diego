package Filmdatabase.domain;

public class Movie extends MediaItem {

    private final int runtimeMinutes;
    private final Genre genre;

    public Movie(String title, int year, int runtimeMinutes, Genre genre) {
        super(title, year);
        this.runtimeMinutes = runtimeMinutes;
        this.genre = genre;
    }

    @Override
    public String getDisplayTitle() {
        return title + " (" + year + ", " + genre + ")";
    }

  
}




