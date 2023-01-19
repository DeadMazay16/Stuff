package ru.mikheev.kirill.counter.simple;

public class SimplePrefWordCounter extends SimpleWordCounter {

    private int[] prefixes = new int[0];

    public SimplePrefWordCounter(String filePath, String wordToCount) {
        super(filePath, wordToCount);
    }

    @Override
    protected int count(String line) {
        String preparedString = wordToCount + '#' + line;
        int n = preparedString.length();
        if(prefixes.length < n) {
            prefixes = new int[n];
        }
        for(int i = 0; i < wordToCount.length() + 1; i++) {
            prefixes[i] = 0;
        }
        int counter = 0;
        for(int i = wordToCount.length() + 1; i < n; i++) {
            int j = prefixes[i-1];
            while(j > 0 && preparedString.charAt(j) != preparedString.charAt(i)) {
                j = prefixes[j - 1];
            }
            if(preparedString.charAt(j) == preparedString.charAt(i)) j++;
            prefixes[i] = j;
            if(j == wordToCount.length()) counter++;
        }
        return counter;
    }
}
