package model;

import java.util.*;

import static model.common.Constants.MAX_SPAMREPORT_COUNT;

/* Changes
Added synchronisation to ensure thread safety for singleton pattern
 */

public class SpamService {
    private static SpamService instance;
    private final Map<String, Integer> spamCount = new HashMap<>();
    private final Set<String> spamNumbers = new HashSet<>();

    private SpamService() {}

    public static synchronized SpamService getInstance() {
        if (instance == null) {
            instance = new SpamService();
        }
        return instance;
    }

    public void reportSpam(String phNo, String reportingNumber, String reason) {
        int count = spamCount.getOrDefault(phNo, 0) + 1;
        spamCount.put(phNo, count);
        if (count >= Constants.MAX_SPAMREPORT_COUNT) {
            spamNumbers.add(phNo);
        }
    }

    public boolean isSpam(String phNo) {
        return spamNumbers.contains(phNo);
    }
}

//public class Spam {
//
//    public static Spam spam;
//    public HashMap<String, Integer> spamCount = new HashMap<>();
//    public Set<String> spamNumbers = new HashSet<>();
//
//    public static Spam getSpamInstance() {
//       if(spam == null) {
//           spam = new Spam();
//       }
//       return spam;
//    }
//
//    public void addSpam(String spamNumber, String ReportingNumber, String reason){
//        int currentCount = spamCount.getOrDefault(spamNumber, 0) + 1;
//        spamCount.put(spamNumber, currentCount);
//
//        // If count exceeds the threshold, add to blocked list
//        if (currentCount >= MAX_SPAMREPORT_COUNT) {
//            spamNumbers.add(spamNumber);
//        }
//    }
//
//    public boolean isSpam(String spamNumber){
//        return spamNumbers.contains(spamNumber);
//    }
//}

/*
The hashmap and set can be replaced by BloomFilters and CountingBloomFilters
 */
