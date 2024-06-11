package ru.mikheev.kirill.gen.example;

import ru.mikheev.kirill.gen.HTMLColumn;

public class TestData {
    @HTMLColumn(name = "First field", converter = TestConverter.class)
    private Integer firstField;
    @HTMLColumn(name = "Second field", converter = TestConverter.class)
    private String secondField;

    public TestData(Integer firstField, String secondField) {
        this.firstField = firstField;
        this.secondField = secondField;
    }
}
