package com.example.tabbeddemo.dto;
public class BookDTO {
    private int txtID;
    private String txtTitle;
    private int txtPrice;

    public BookDTO(int txtID, String txtTitle, int txtPrice) {
        this.txtID = txtID;
        this.txtTitle = txtTitle;
        this.txtPrice = txtPrice;
    }

    public BookDTO() {
    }

    public int getTxtID() {
        return txtID;
    }

    public void setTxtID(int txtID) {
        this.txtID = txtID;
    }

    public String getTxtTitle() {
        return txtTitle;
    }

    public void setTxtTitle(String txtTitle) {
        this.txtTitle = txtTitle;
    }

    public int getTxtPrice() {
        return txtPrice;
    }

    public void setTxtPrice(int txtPrice) {
        this.txtPrice = txtPrice;
    }
}
