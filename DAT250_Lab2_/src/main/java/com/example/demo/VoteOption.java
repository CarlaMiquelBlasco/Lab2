package com.example.demo;

public class VoteOption {
    private String caption;
    private int presentationOrder;

    // Parameterless constructor
    public VoteOption() {}

    // Getters and setters
    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public int getPresentationOrder() {
        return presentationOrder;
    }

    public void setPresentationOrder(int presentationOrder) {
        this.presentationOrder = presentationOrder;
    }
}
