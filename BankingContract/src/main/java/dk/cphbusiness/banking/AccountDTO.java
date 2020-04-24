package dk.cphbusiness.banking;

import java.io.Serializable;

public class AccountDTO implements Serializable {
    private String number;

    public AccountDTO(String number, String cpr, String bankCVR) {
        this.number = number;
    }

    public String getNumber() {
        return number;
    }
}
