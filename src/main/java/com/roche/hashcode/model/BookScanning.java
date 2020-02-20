package com.roche.hashcode.model;

import java.util.ArrayList;
import java.util.List;

public class BookScanning {

    private int timeAvailable;
    private List<Library> libraries = new ArrayList<>();

    public int getTimeAvailable() {
        return timeAvailable;
    }

    public void setTimeAvailable(int timeAvailable) {
        this.timeAvailable = timeAvailable;
    }

    public List<Library> getLibraries() {
        return libraries;
    }

    public boolean addLibrary(Library library) {
        return this.libraries.add(library);
    }

    public void setLibraries(List<Library> libraries) {
        this.libraries = libraries;
    }

    @Override
    public String toString() {
        return "BookScanning{" +
                "timeAvailable=" + timeAvailable +
                ", libraries=" + libraries +
                '}';
    }
}
