package dk.cphbusiness.banking;

import java.io.Serializable;

public class CustomerDTO implements Serializable {
    private String cpr;
    private String name;

    public CustomerDTO(String cpr, String name){
        this.cpr = cpr;
        this.name = name;
    }

    public String getCpr() {
        return cpr;
    }

    public String getName() {
        return name;
    }
}
