package com.kabricks.ster.demotiv.lists;

public class Item {

    String name;
    boolean checkbox;

    public Item() {
         /*Empty Constructor*/
    }
    public  Item(String country, boolean status){
        this.name = country;
        this.checkbox = status;
    }
    //Getter and Setter
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isCheckbox() {
        return checkbox;
    }

    public void setCheckbox(boolean checkbox) {
        this.checkbox = checkbox;
    }

}
