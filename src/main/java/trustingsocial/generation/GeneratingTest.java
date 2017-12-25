package trustingsocial.generation;

import trustingsocial.Record;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class GeneratingTest {

    private List<Record> records;
    private Map<String, Record> results;
    private int totalPhoneNumber;
    private String outputName;
    private String outputResultName;

    public GeneratingTest(int totalPhoneNumber, String outputName, String outputResultName) {
        records = new ArrayList<>();
        results = new HashMap<>();
        this.totalPhoneNumber = totalPhoneNumber;
        this.outputName = outputName;
        this.outputResultName = outputResultName;
    }

    public void generate() {
        for (int i = 1; i <= totalPhoneNumber; i++) {
            generate(i + "");
        }

        Collections.shuffle(records);

        writeTestToFile();
        writeResultToFile();
    }

    private void generate(String phoneNumber) {
        Date startDate, endDate;
        startDate = RandomUtils.randomDate();

        // Will be 5 consecutive blocks
        for (int j = 0; j < 5; j++) {
            startDate = RandomUtils.randomMonth(startDate);
            // one consecutive block will have 10 elements
            for (int i = 0; i < 10; i++) {
                endDate = RandomUtils.randomDate(startDate);
                Record record = new Record(phoneNumber, startDate, endDate);
                records.add(record);
                startDate = endDate;
            }
        }

        // generate the final block which is the answer
        startDate = RandomUtils.randomMonth(startDate);
        endDate = RandomUtils.randomDate(startDate);
        Record activationRecord = new Record(phoneNumber + "", startDate, endDate);
        records.add(activationRecord);
        startDate = endDate;
        for (int i = 0; i < 10; i++) {
            endDate = RandomUtils.randomDate(startDate);
            Record record = new Record(phoneNumber, startDate, endDate);
            records.add(record);
            startDate = endDate;
        }
        Record endRecord = new Record(phoneNumber, startDate, RandomUtils.randomFutureDate());
        records.add(endRecord);
        results.put(phoneNumber, activationRecord);
    }

    private void writeTestToFile() {
        PrintWriter pr = null;
        try {
            pr = new PrintWriter(new BufferedWriter(new FileWriter(new File(outputName))));
            pr.println("PHONE_NUMBER,ACTIVATION_DATE,DEACTIVATION_DATE");

            for (Record record : records) {
                DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
                String activationDate = format.format(record.getActivationDate());
                String deActivationDate = format.format(record.getDeactivationDate());
                pr.println(record.getPhone() + "," + activationDate + "," + deActivationDate);
            }
            pr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void writeResultToFile() {
        PrintWriter pr = null;
        try {
            pr = new PrintWriter(new BufferedWriter(new FileWriter(new File(outputResultName))));
            pr.println("PHONE_NUMBER,REAL_ACTIVATION_DATE");

            SortedSet<String> keys = new TreeSet<String>(results.keySet());
            for (String key : keys) {
                Record record = results.get(key);
                pr.println(record.getPrintString());
            }
            pr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new GeneratingTest(10, "data/random_1.csv", "data/random_1_result.csv").generate();
        new GeneratingTest(1000, "data/random_2.csv", "data/random_2_result.csv").generate();
    }
}
