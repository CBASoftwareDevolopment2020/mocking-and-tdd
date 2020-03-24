package dk.cphbusiness.banking;

import java.io.Serializable;

public class AccountDTO implements Serializable {
    private long id;
    private String number;

    public AccountDTO(long id, String number) {
        this.id = id;
        this.number = number;
    }

    public String getNumber() {
        return number;
    }

    public long getId() {
        return id;
    }
}
