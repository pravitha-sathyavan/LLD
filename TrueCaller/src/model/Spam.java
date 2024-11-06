package model;

import java.util.*;

import static model.common.Constants.MAX_SPAMREPORT_COUNT;

public class Spam {

    public static Spam spam;
    public HashMap<String, Integer> spamCount = new HashMap<>();
    public Set<String> spamNumbers = new HashSet<>();

    public static Spam getSpamInstance() {
       if(spam == null) {
           spam = new Spam();
       }
       return spam;
    }

    public void addSpam(String spamNumber, String ReportingNumber, String reason){
        int currentCount = spamCount.getOrDefault(spamNumber, 0) + 1;
        spamCount.put(spamNumber, currentCount);

        // If count exceeds the threshold, add to blocked list
        if (currentCount >= MAX_SPAMREPORT_COUNT) {
            spamNumbers.add(spamNumber);
        }
    }

    public boolean isSpam(String spamNumber){
        return spamNumbers.contains(spamNumber);
    }

}

/*
The hashmap and set can be replaced by BloomFilters and CountingBloomFilters
 */
