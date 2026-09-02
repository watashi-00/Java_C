package com.lab;

public record EntryMenu(int index, String label) {
    @Override
    public String toString() {
        return "> " + index + ": " + label;
    }
}
