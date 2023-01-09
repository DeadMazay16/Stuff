package ru.mikheev.kirill;

public class FileContentProcessor {


    public static void main(String[] args) throws Exception {
        WordCounter wordCounter = new SimpleWordCounter(args[0]);
        System.out.println(wordCounter.countWord("Пьер"));
    }
}
