package ru.mikheev.kirill.counter.simple;

public class SimpleZFunctionWordCounter extends SimpleWordCounter {

    private int[] zValues = new int[0];

    public SimpleZFunctionWordCounter(String filePath, String wordToCount) {
        super(filePath, wordToCount);
    }

    @Override
    protected int count(String line) {
        String preparedString = wordToCount + '#' + line;
        int n = preparedString.length();
        int leftHighestBorder = -1, rightHighestBorder = -1;
        if(zValues.length < n) {
            zValues = new int[n];
        }
        int counter = 0;
        for(int i = 1; i < n; i++) {
            if(leftHighestBorder <= i && i <= rightHighestBorder) {
                zValues[i] = Math.min(zValues[i-leftHighestBorder], rightHighestBorder - i + 1);
            }else{
                zValues[i] = 0;
            }
            while (i + zValues[i] < n && preparedString.charAt(zValues[i]) == preparedString.charAt(i + zValues[i])) {
                zValues[i]++;
            }
            if(i + zValues[i] - 1 > rightHighestBorder) {
                leftHighestBorder = i;
                rightHighestBorder = i + zValues[i] - 1;
            }
            if(zValues[i] == wordToCount.length()) counter++;
        }


        return counter;
    }
}
