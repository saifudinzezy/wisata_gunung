package com.example.cyber_net.sig.helper;

public class Wisataku {
    private String id;
    private String judul;
    private int image;

    public Wisataku() {
    }

    public Wisataku(String id, String judul, int image) {
        this.id = id;
        this.judul = judul;
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
