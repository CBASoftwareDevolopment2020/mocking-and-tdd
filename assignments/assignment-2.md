# Assignment 2 -  Integration Tests

Create a contract for your bank project. The contract should include method
calls for a potential frontend, as a traditional webserver or a RESTful api.
Use techniques from the toolbox (slides from class).

Remember to separate the layers by using DTOs and interfaces only in the
contact.

Create test to the contract as described.

## Content
- [Utilities](#Utilities)
    - [JUnit](#JUnit)
    - [Hamcrest](#Hamcrest)
- [BankingContract](#BankingContract)
    - [BankManager Interface](#BankManager-Interface)
    - [BankManagerHolder](#BankManagerHolder)
    - [BankManager Test](#BankManager-Test)
    - [Implementation](#Implementation)
- [Data Transfer Objects](#Data-Transfer-Objects)
    - [AccountDTO](#AccountDTO)
    - [BankDTO](#BankDTO)
    - [CustomerDTO](#CustomerDTO)
    - [MovementDTO](#MovementDTO)

## Utilities
### JUnit

We are using `JUnit` to test the the methods in out system. We are using `Assert` and `Assume` to validate the outcome and state of the test internal operations.

The structure of our tests are starting with and `Assume`, that validates if `manager` not is `null` using the matcher `Hamcrest`. If the `manager` is `null` the test will fail.

If the assumption doesn't fail the test is executed as usual, and ands with an `assert` or `Exception`.

### Hamcrest

As matcher we are using `Hamcrest`. This library is a collection of methods and values, where the methods can match the argument parsed into it.

This is primarily used in the `assumeThat` method from `JUnit`. The example below shows how the notations works.

```java
assumeThat(manager, not(nullValue()));
```

## BankingContract

### BankManager Interface
[BankManager.java, reference](https://github.com/SoftwareDevolopmentSem1/mocking-and-tdd/blob/master/BankingContract/src/main/java/dk/cphbusiness/banking/BankManager.java)

We created a interface for the `BankManager` we could create several test cases and implemented both the interface and the test in the project using the contract.

```java
@Remote
public interface BankManager {
    AccountDTO addAccount(String number, String cpr, String bankCVR);
    CustomerDTO addCustomer(String cpr, String name, String accountNumber, String bankCVR);
    AccountDTO getAccount(String number);
    List<AccountDTO> getCustomersAccounts(String customerId);
    List<AccountDTO> getAccounts();
    CustomerDTO getCustomer(String customerId);
    List<CustomerDTO> getCustomers(String bankId);
    MovementDTO transfer(String sourceAccountId, String targetAccountId, long amount);
}
```

### BankManagerHolder
[BankManagerHolder.java, reference](https://github.com/SoftwareDevolopmentSem1/mocking-and-tdd/blob/master/BankingContract/src/main/java/dk/cphbusiness/banking/BankManagerHolder.java)

This class is a container containing a static field with type `BankManager` called `manager`. This gives the possibility to use the `manager` as a global object. 

```java
public class BankManagerHolder {
    public static BankManager manager;
}
```

For ever test run the `manager` is initialized with `new BankManager()`. The annotation `@BeforeClass` tells `JUnit` this method should be executed before the first test case.

[ManagerTest.java, reference](https://github.com/SoftwareDevolopmentSem1/mocking-and-tdd/blob/master/Banking/src/test/java/banking/ManagerTest.java)

```java
@RunWith(Suite.class)
public class ManagerTest {
    @BeforeClass
    public static void setupClass() {
        BankManagerHolder.manager = new BankManager();
    }
}
```

### BankManager Test
[BankManagerTest.java, reference](https://github.com/SoftwareDevolopmentSem1/mocking-and-tdd/blob/master/BankingContract/src/test/java/dk/cphbusiness/banking/BankManagerTest.java)

#### Select Test Cases

We selected tree test cases, where we test an `add`, an `get` and an `abstract` method.

##### Method `testAddAccount`

This test case is selected to show how an `add` method is tested. 
Before the main operations in this test case is called, we need to know if the `manager` is `null`. We are doing this using `Assume` and `Matchers`.

After our assumption we are adding an `Account` using the managers `addAccount` method that returns an `AccountDTO`. This `DTO` is used in the `assertion`.

```java
@Test
public void testAddAccount() {
    assumeThat(manager, not(nullValue()));

    String accountNumber = "12345678";

    AccountDTO acc = manager.addAccount(accountNumber, "id123", "steph123");

    assertThat(acc, not(nullValue()));
    assertEquals(accountNumber, acc.getNumber());
}
```

##### Method `testGetCustomer`

This test case is selected to show how an `get` method is tested. 
Like the case above we are making an assumption that says `manager` isn't `null`.

After our assumption we are getting an `Customer` using the a `customerId` and the managers `getCustomer` method that returns an `CustomerDTO`. This `DTO` is used in the `assertion`.

```java
@Test
public void testGetCustomer() {
    assertThat(manager, not(nullValue()));

    String customerId = "id123";
    CustomerDTO customer = manager.getCustomer(customerId);

    assertThat(customer, not(nullValue()));
}
```

##### Method `testTransfer`

This test case is selected to show how an `abstract` method is tested. 
Like the case above we are making an assumption that says `manager` isn't `null`.

After the `manager` is validated, we need two `accountIds` for a source and a target `Account`.
This test case uses a value `long` that is transferred from source to target.

The method `transfer` is accessed trough the `manager` and returns a `MovementDTO`. This DTO is asserted to validate the method.

```java
@Test
public void testTransfer() {
    assertThat(manager, not(nullValue()));

    String source = "src123";
    String target = "trgt132";
    long amount = 100;

    MovementDTO mov = manager.transfer(source, target, amount);

    assertThat(mov, not(nullValue()));
    assertEquals(source, mov.getSourceAccount());
    assertEquals(target, mov.getTargetAccount());
    assertEquals(amount, mov.getAmount());
}
```

#### Test Cases

Below are all the test cases within the `BankManagerTest`.

| Method | Preconditions | Expectation |
|---|---|---|
|testAddAccount| `manager` isn't `null` <br> customer exits | an account with correct `number` |
|testAddCustomer| `manager` isn't `null` <br> bank exits | an account with correct `name` and `cpr` |
|testGetExistingAccount| `manager` isn't `null` <br> account exits | an account with correct `number` |
|testGetCustomerAccounts| `manager` isn't `null` <br> customer exits | a list of `Accounts` |
|testGetAccounts| `manager` isn't `null` <br> bank exits | a list of all `Accounts` |
|testGetCustomer| `manager` isn't `null` <br> customer exits | a customer with correct `id` |
|testGetCustomers| `manager` isn't `null` <br> bank exits | a list of `Customers` |
|testTransfer| `manager` isn't `null` <br> both accounts exits | a movement with correct `source`, `target` and `amount` |

### Implementation
[pom.xml, reference](https://github.com/SoftwareDevolopmentSem1/mocking-and-tdd/blob/master/BankingContract/pom.xml)

Before implementing the contract, its should be build as an artifact. The id of this artifact is selected in the `pom.xml` file inside an `artifactId` tag. Within this file the `groupId` tag should be similar to the `pom.xml` from the projects using this artifact.

#### pom.xml
```xml
...
<groupId>dk.cphbusiness</groupId>
<artifactId>BankingContract</artifactId>
...
```

The implementation of th artifact is located in the `project structure` and `modules` if you are using IntelliJ. 
In `modules` add the artifact from the contracts `out` folder, and then it should work.

## Data Transfer Objects

We made a DTO for all the entities in our system. The DTO's are simple data containers with a getter for every property value. 

### AccountDTO
[AccountDTO.java, reference](https://github.com/SoftwareDevolopmentSem1/mocking-and-tdd/blob/master/BankingContract/src/main/java/dk/cphbusiness/banking/AccountDTO.java)

### BankDTO
[BankDTO.java, reference](https://github.com/SoftwareDevolopmentSem1/mocking-and-tdd/blob/master/BankingContract/src/main/java/dk/cphbusiness/banking/BankDTO.java)

### CustomerDTO
[CustomerDTO.java, reference](https://github.com/SoftwareDevolopmentSem1/mocking-and-tdd/blob/master/BankingContract/src/main/java/dk/cphbusiness/banking/CustomerDTO.java)

### MovementDTO
[MovementDTO.java, reference](https://github.com/SoftwareDevolopmentSem1/mocking-and-tdd/blob/master/BankingContract/src/main/java/dk/cphbusiness/banking/MovementDTO.java)