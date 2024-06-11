package ru.mikheev.kirill.gen.example;

public class TestConverter {
    public String mapInteger(Integer source) {
        return source.toString() + ".0";
    }

    public String mapString(String source) {
        return "\"" + source + "\"";
    }
}
