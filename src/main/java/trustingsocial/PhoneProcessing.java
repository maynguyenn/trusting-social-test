package trustingsocial;

import java.io.*;
import java.util.*;

public class PhoneProcessing {
    private String fileName;
    private List<Record> records;
    private Map<String, Record> results;

    public PhoneProcessing(String fileName) {
        this.fileName = fileName;
        records = new ArrayList<>();
        results = new HashMap<>();
    }

    public void processing() {
        readFromFile();
        solve();
        writeToFile();
    }

    private void readFromFile() {
        InputReader sc = null;
        try {
            sc = new InputReader(new FileInputStream(new File(fileName)));
            String line;
            Record record;

            sc.next();
            while ((line = sc.next()) != null) {
                String[] tokens = line.split(",");
                if (tokens.length == 3) {
                    record = new Record(tokens[0], tokens[1], tokens[2]);
                } else {
                    record = new Record(tokens[0], tokens[1]);
                }
                records.add(record);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void solve() {
        Collections.sort(records);

        int index = 0;
        while (index < records.size()) {
            index = solveForPhoneNumber(index);
        }
    }

    /**
     * @param index: first starting index of phone number
     * @return nex index with different phone number. if index == records.size() means we have iterated all items in list
     */
    private int solveForPhoneNumber(int index) {
        Record currentRecord, previousRecord, result;

        currentRecord = records.get(index);

        // this phone isn't being held by anyone
        if (!currentRecord.isContractActive()) {
            index = findNextPhoneIndex(index, currentRecord.getPhone());
            results.put(currentRecord.getPhone(), null);
            return index;
        }

        result = currentRecord;
        index++;

        while (index < records.size()) {
            previousRecord = currentRecord;
            currentRecord = records.get(index);

            // if this record belongs to new phone number
            if (!currentRecord.getPhone().equals(previousRecord.getPhone())) {
                break;
            }

            if (currentRecord.isConsecutiveBeforeRecord(previousRecord)) {
                result = currentRecord;
                index++;
            } else {
                break;
            }
        }

        index = findNextPhoneIndex(index, result.getPhone());
        if (results.containsKey(result.getPhone())) {
            throw new RuntimeException("Algorithm is wrong");
        }
        results.put(result.getPhone(), result);
        return index;
    }

    private int findNextPhoneIndex(int index, String phoneNumber) {
        while (index < records.size() && records.get(index).getPhone().equals(phoneNumber)) {
            index++;
        }
        return index;
    }

    private void writeToFile() {
        SortedSet<String> keys = new TreeSet<String>(results.keySet());

        PrintWriter pr = null;
        try {
            pr = new PrintWriter(new BufferedWriter(new FileWriter(new File("output.csv"))));
            pr.println("PHONE_NUMBER,REAL_ACTIVATION_DATE");
            for (String key : keys) {
                Record record = results.get(key);
                if (record != null) pr.println(record.getPrintString());
            }
            pr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
