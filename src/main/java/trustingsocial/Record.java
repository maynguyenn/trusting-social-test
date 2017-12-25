package trustingsocial;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Record implements Comparable<Record> {
    private static Date CURRENT_DATE;

    static {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            CURRENT_DATE = sdf.parse(sdf.format(new Date()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private String phone;
    private Date activationDate;
    private Date deactivationDate;
    DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

    public Record(String phone, Date activationDate, Date deactivationDate) {
        this.phone = phone;
        this.activationDate = activationDate;
        this.deactivationDate = deactivationDate;
    }

    public Record(String phone, String activationDate, String deactivationDate) {
        this.phone = phone;
        this.activationDate = parseDate(activationDate);
        this.deactivationDate = parseDate(deactivationDate);

        DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
    }

    public Record(String phone, String activationDate) {
        this.phone = phone;
        this.activationDate = parseDate(activationDate);
    }

    private Date parseDate(String dateStr) {
        try {
            return format.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getPhone() {
        return phone;
    }

    public Date getActivationDate() {
        return activationDate;
    }

    public Date getDeactivationDate() {
        return deactivationDate;
    }

    public boolean isContractActive() {
        return (this.getDeactivationDate() == null) || (this.getDeactivationDate().compareTo(CURRENT_DATE) > 0);
    }

    /**
     *
     * @param next
     * @return return true if that record is consecutive before this record
     */
    public boolean isConsecutiveBeforeRecord(Record next) {
        return this.getDeactivationDate() == null || this.getDeactivationDate().equals(next.getActivationDate());
    }

    public String getPrintString() {
        String date = format.format(this.getActivationDate());
        return this.phone + "," + date;
    }

    @Override
    public int compareTo(Record that) {
        if (!this.phone.equals(that.phone)) {
            return that.phone.compareTo(this.phone);
        }

        if (!this.activationDate.equals(that.activationDate)) {
            return that.activationDate.compareTo(this.activationDate);
        }

        return that.deactivationDate.compareTo(this.deactivationDate);
    }

    @Override
    public String toString() {
        return this.phone + "," + this.activationDate + "," + this.deactivationDate;
    }
}
