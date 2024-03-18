//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import java.util.HashSet;

import java.util.Set;



public class StopWordFilter implements Filter {

    private Set<String> stopWords;



    public StopWordFilter() {

        // Initialize stop words set

        stopWords = new HashSet<>();

        // Add common stop words to the set

        String[] commonStopWords = {"a", "an", "the", "to", "is", "are", "from", "at", "in", "and", "on", "for", "with"};

        for (String word : commonStopWords) {

            stopWords.add(word.toLowerCase());

        }

    }



    public boolean accept(Object x) {

        if (!(x instanceof String)) {

            throw new IllegalArgumentException("Input must be a String");

        }

        String input = (String) x;

        // Split the input string into words

        String[] words = input.split("\\s+");



        // Check each word and filter out stop words

        for (String word : words) {

            // Check if the word is a stop word (ignoring case)

            if (stopWords.contains(word.toLowerCase())) {

                // Return true if a stop word is found

                return false;

            }

        }



        // Return false if no stop words are found

        return true;

    }

}