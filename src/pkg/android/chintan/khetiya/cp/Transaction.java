package pkg.android.chintan.khetiya.cp;

/**
 * Created by nearents on 4/23/14.
 */
public class Transaction {

    String amount;
    String date;
    String info;
    String debitOrCredit;

    public Transaction() {

    }

    public Transaction(String a, String d, String i, String debOrCred) {
        this.amount = a;
        this.date = d;
        this.info = i;
        this.debitOrCredit = debOrCred;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getDebitOrCredit() {
        return debitOrCredit;
    }

    public void setDebitOrCredit(String debitOrCredit) {
        this.debitOrCredit = debitOrCredit;
    }
}
