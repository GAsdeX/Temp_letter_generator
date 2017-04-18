package main;

public class Trigram {
    String w1;
    String w2;
    String w3;
    int counter = 1;

    Trigram(String w1a, String w2a, String w3a) {
        w1 = w1a;
        w2 = w2a;
        w3 = w3a;
    }

    @Override
    public String toString() {
        return String.format("%7d", counter) + " : " + w1 + " " + w2 + " " + w3;
    }
}
