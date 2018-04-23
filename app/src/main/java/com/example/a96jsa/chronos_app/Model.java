package com.example.a96jsa.chronos_app;

public class Model{

    private final int iconEdit = R.id.edit_button ;
    private final int iconDelete = R.id.delete_button ;
    private final int iconStats = R.id.stats_button ;

    private String title;
    private String text;

    private boolean isGroupHeader = false;

    public Model(String title) {
        this(title,false);
        isGroupHeader = true;
    }
    public Model(String title, boolean isTitle) {
        super();
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isGroupHeader() {
        return isGroupHeader;
    }

    public void setGroupHeader(boolean groupHeader) {
        isGroupHeader = groupHeader;
    }


}