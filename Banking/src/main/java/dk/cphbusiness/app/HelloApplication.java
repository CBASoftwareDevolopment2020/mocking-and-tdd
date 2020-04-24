package dk.cphbusiness.app;

// import the rest service you created!
import dk.cphbusiness.rest.AccountResource;
import dk.cphbusiness.rest.BankResource;
import dk.cphbusiness.rest.CustomerResource;
import dk.cphbusiness.rest.MovementResource;
import dk.cphbusiness.rest.Resource;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;
public class HelloApplication extends Application {
    private Set<Object> singletons = new HashSet<Object>();
    public HelloApplication() {
        // Register our hello service
        singletons.add(new AccountResource());
        singletons.add(new BankResource());
        singletons.add(new CustomerResource());
        singletons.add(new MovementResource());
        singletons.add(new Resource());
    }
    @Override
    public Set<Object> getSingletons() {
        return singletons;
    }
}
