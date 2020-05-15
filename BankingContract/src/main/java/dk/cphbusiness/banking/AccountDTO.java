package dk.cphbusiness.banking;

import java.io.Serializable;

public class AccountDTO implements Serializable {
    private String number;
    private String cpr;
    private String cvr;

    public AccountDTO(String number, String cpr, String cvr) {
        this.number = number;
        this.cpr = cpr;
        this.cvr = cvr;
    }

    public String getNumber() {
        return number;
    }

    public String getCpr() {
        return cpr;
    }

    public String getCvr() {
        return cvr;
    }
}