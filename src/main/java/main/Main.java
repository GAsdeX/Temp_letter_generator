package main;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.OutputStream;
import java.util.*;

public class Main {
    public static void main (String[] args) {
        ObjectMapper mapper = new ObjectMapper();
        HashMap<String, Trigram> trigrams = new HashMap<String, Trigram>();

        try{
            JsonNode root = mapper.readTree(new File("letters.json"));    //считать инфу с letters.json
            for (JsonNode jsonNode : root) {
                JsonNode coverLetterNode = jsonNode.get("coverLetter");   //получить значение coverletter из letters.json

                String coverLetter = coverLetterNode.asText();            // отчистить строку ??
                String[] words = coverLetter.split("[\\n ,.\\r?/-]+");    // разделить

                for (int i = 0; i<(words.length-2); i++) {
                    String w1 = words[i];
                    String w2 = words[i+1];
                    String w3 = words[i+2];
                    String key = w1 + "_" + w2 + "_" + w3;
                    Trigram trigram = new Trigram(w1, w2, w3);
                    Trigram temp =  trigrams.get(key);
                    if (temp == null) {
                        trigrams.put(key, trigram);
                    } else {
                        Trigram existing = trigrams.get(key);
                        existing.counter = existing.counter + 1;
                    }
                }
            }


            List<Trigram> sorted = new LinkedList<Trigram>();
            for (Map.Entry<String,Trigram> entry : trigrams.entrySet()) {  // Что здесь происходит?
//                System.out.println(entry.getValue());
                sorted.add(entry.getValue());
            }
            Collections.sort(sorted, new Comparator<Trigram>() {          // Что здесь происходит?
                public int compare(Trigram o1, Trigram o2) {
                    return o2.counter - o1.counter;
                }
            });
            for (Trigram trigram : sorted) {                              // Что здесь происходит?
                System.out.println(trigram);
//                mapper.writeValue(new File("trigramsLlist.json"), trigram);
            }

            ArrayList<String> letter = new ArrayList<String>();
            letter.add("Hi");
            letter.add("I'm");

            // For words in a letter.
            for (int i = 2; i < 50; i++) {                    // Составление текста из 50 символов ???
                System.out.println(letter);
                // For each trigram in dictionary.
                int maxFreq = 0;
                Trigram champion = null;
                System.out.println("candidates:");
                for (int j = 0; j < sorted.size(); j++) {      // Составление слов
                    if (
                        sorted.get(j).w1.equals(letter.get(i-2)) &&
                        sorted.get(j).w2.equals(letter.get(i-1))
                        ) {
                        if (sorted.get(j).counter > maxFreq) {
                            maxFreq = sorted.get(j).counter;
                            champion = sorted.get(j);
                        }
                        System.out.println("\t" + sorted.get(j));
                    }
                }
                if (champion == null) {
                    System.out.println("can not proceed.");
                    break;
                } else {
                    System.out.println("\t\tchampion: " + champion);
                    letter.add(champion.w3);
                }
            }


            System.out.println(letter);


        } catch (Exception e) {

        }
    }
}
