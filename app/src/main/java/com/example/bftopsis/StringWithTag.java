package com.example.bftopsis;

public class StringWithTag {

    public String string;
    String tag;

    StringWithTag(String stringPart, String tagPart) {
        string = stringPart;
        tag = tagPart;
    }

    @Override
    public String toString() {
        return string;
    }

}