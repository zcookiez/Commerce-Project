package com.example.commerce;

public class MenuOption implements Printable {
    private String menuName;

    public MenuOption(String menuName) {
        this.menuName = menuName;
    }

    public String getMenuName() {
        return menuName;
    }

    @Override
    public String printFormat() {
        return this.menuName;
    }
}