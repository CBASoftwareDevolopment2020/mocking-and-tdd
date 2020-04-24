#   Assignment 4 | REST API Test
  
Implement a RESTful API on that uses the contract.
In the case that there are discrepancies between contract methods with DTOs
and the functionality needed for the API endpoints; adjust the contract.
Remember to adjust contract tests as well.
  
Set up tests for the Rest API, using one or more of the following.
* Using your package manager (Maven)
* Creating a separate project, using setUp and tearDown methods to start the service.
* Using A CI-server
* A dedicated REST test tool (e.g. Postman)
  
_**We are using Postman to test our Rest API.**_
  
##   Content
  
  
- [Bank](#bank )
  - [Get All Banks | GET](#get-all-banks-get )
  - [Get Bank | GET](#get-bank-get )
  - [Add Bank | POST](#add-bank-post )
- [Customer](#customer )
  - [Get Customers | GET](#get-customers-get )
  - [Get Customer | GET](#get-customer-get )
  - [Add Customer | POST](#add-customer-post )
- [Account](#account )
  - [Get All Accounts | GET](#get-all-accounts-get )
  - [Get Account | GET](#get-account-get )
  - [Add Account | POST](#add-account-post )
- [Movement](#movement )
  - [Get Deposits | GET](#get-deposits-get )
  - [Get Withdrawals | GET](#get-withdrawals-get )
  - [Add Movement | POST](#add-movement-post )
  
___
##  Bank
  
  
###   Get All Banks | GET
  
This rest point request all **banks** from the database, and returns them as json format with a `status: 200`.
If an error should occur the response would be `status: 304`.
  
####  Request
  
_uri, get request_
```
/banks/all
```
  
####  Responses
  
_success response_
```
status: 200, ok
```
```js
[
    { "cvr": String, "name": String },
    // ...
]
```
  
_error response_
```
status: 304, not modified
```
  
###  Get Bank | GET
  
This rest point request a **bank** from the database using the parameter `cvr`, and returns it as json format with a `status: 200`.
If the `cvr` doesn't exits or an error should occur the response would be `status: 304`.
  
####  Request
  
_uri, get request_
```
/banks/bank/{cvr}
```
  
####  Responses
  
_success response_
```
status: 200, ok
```
```js
{ 
    "cvr": String, 
    "name": String
}
```
  
_error response_
```
status: 304, not modified
```
  
###  Add Bank | POST
  
This rest point request an insertion of a new **bank** to the database using `name` and `cvr` from a json body and returns a `status: 200`.
If the `cvr` already exits or an error should occur the response would be `status: 304`.
  
####  Request
  
_uri, post request_
```
/banks/add
```
  
_json, request body_
```js
{
    "name": String,
    "cvr": String
}
```
  
####  Responses
  
_success response_
```
status: 200, ok
```
  
_error response_
```
status: 304, not modified
```  
___
##  Customer
  
###   Get Customers | GET
  
This rest point request all **customers** from the database, and returns them as json format with a `status: 200`.
If an error should occur the response would be `status: 304`.
  
####  Request
  
_uri, get request_
```
/customers/all
```
  
####  Responses
  
_success response_
```
status: 200, ok
```
```js
[
    { "cpr": String, "name": String },
    // ...
]
```
  
_error response_
```
status: 304, not modified
```
  
###  Get Customer | GET
  
This rest point request a **customer** from the database using the parameter `cpr`, and returns it as json format with a `status: 200`.
If the `cpr` doesn't exits or an error should occur the response would be `status: 304`.
  
####  Request
  
_uri, get request_
```
/customers/customer/{cpr}
```
  
####  Responses
  
_success response_
```
status: 200, ok
```
```js
{ 
    "cpr": String, 
    "name": String
}
```
  
_error response_
```
status: 304, not modified
```
  
###  Add Customer | POST
  
This rest point request an insertion of a new **customer** to the database using `name` and `cvr` from a json body and returns a `status: 200`.
If the `cpr` already exits or an error should occur the response would be `status: 304`.
  
####  Request
  
_uri, post request_
```
/customers/add
```
  
_json, request body_
```js
{
    "name": String,
    "cpr": String,
    "cvr": String
}
```
  
####  Responses
  
_success response_
```
status: 200, ok
```
  
_error response_
```
status: 304, not modified
```
  
___
##  Account
  
###  Get All Accounts | GET
  
This rest point request all **accounts** from the database, and returns them as json format with a `status: 200`.
If an error should occur the response would be `status: 304`.
  
####  Request
  
_uri, get request_
```
/accounts/all
```
  
####  Responses
  
_success response_
```
status: 200, ok
```
```js
[
    { "cpr": String, "number": String },
    // ...
]
```
  
_error response_
```
status: 304, not modified
```
  
###  Get Account | GET
  
This rest point request a **customer** from the database using the parameter `id`, and returns it as json format with a `status: 200`.
If the `id` doesn't exits or an error should occur the response would be `status: 304`.
  
####  Request
  
_uri, get request_
```
/accounts/account/{id}
```
  
####  Responses
  
_success response_
```
status: 200, ok
```
```js
{ "number": String, "cpr": String, "cvr": String }
```
  
_error response_
```
status: 304, not modified
```
  
###  Add Account | POST
  
This rest point request an insertion of a new **account** to the database using `cpr` and `number` from a json body and returns a `status: 200`.
If the `cpr` doesn't exits, the `number` already exits or an error should occur the response would be `status: 304`.
  
####  Request
  
_uri, post request_
```
/accounts/add
```
  
_json, request body_
```js
{
    "cpr": String,
    "number": String
}
```
  
####  Responses
  
_success response_
```
status: 200, ok
```
  
_error response_
```
status: 304, not modified
```
  
___
##  Movement  
  
###  Get Deposits | GET
  
This rest point request **movements** from the database defined as deposits from an account using the parameter `acc`, and returns them as json format with a `status: 200`.
If the account doesn't exist or an error should occur the response would be `status: 304`.
  
####  Request
  
_uri, get request_
```
/movements/deposits/{acc}
```
  
####  Responses
  
_success response_
```
status: 200, ok
```
```js
[
    { "time": Long, "amount": Long, "source": String, "target": String },
    // ...
]
```
  
_error response_
```
status: 304, not modified
```
  
###  Get Withdrawals | GET
  
This rest point request **movements** from the database defined as withdrawals from an account using the parameter `acc`, and returns them as json format with a `status: 200`.
If the account doesn't exist or an error should occur the response would be `status: 304`.
  
####  Request
  
_uri, get request_
```
/movements/withdrawals/{acc}
```
  
####  Responses
  
_success response_
```
status: 200, ok
```
  
```js
[
    { "time": Long, "amount": Long, "source": String, "target": String },
    // ...
]
```
  
_error response_
```
status: 304, not modified
```
  
###  Add Movement | POST
  
This rest point request an insertion of a new **movement** to the database using `amount`, `source_number` and `target_number` from a json body and returns a `status: 200`.
If the `source_number` or `target_number` doesn't exits or an error should occur the response would be `status: 304`.
  
####  Request
  
_uri, post request_
```
/movements/add
```
  
_json, request body_
```js
{
    "amount" Long,
    "source_number" String,
    "target_number" String
}
```
  
####  Responses
  
_success response_
```
status: 200, ok
```
  
_error response_
```
status: 304, not modified
```
  
