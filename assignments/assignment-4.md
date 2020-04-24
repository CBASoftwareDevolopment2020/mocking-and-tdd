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
    - [Request](#request )
    - [Responses](#responses )
  - [Get Bank | GET](#get-bank-get )
    - [Request](#request-1 )
    - [Responses](#responses-1 )
  - [Add Bank | POST](#add-bank-post )
    - [Request](#request-2 )
    - [Responses](#responses-2 )
- [Customer](#customer )
  - [Get Customers | GET](#get-customers-get )
    - [Request](#request-3 )
    - [Responses](#responses-3 )
  - [Get Customer | GET](#get-customer-get )
    - [Request](#request-4 )
    - [Responses](#responses-4 )
  - [Add Customer | POST](#add-customer-post )
    - [Request](#request-5 )
    - [Responses](#responses-5 )
- [Account](#account )
  - [Get All Accounts | GET](#get-all-accounts-get )
    - [Request](#request-6 )
    - [Responses](#responses-6 )
  - [Get Account | GET](#get-account-get )
    - [Request](#request-7 )
    - [Responses](#responses-7 )
  - [Add Account | POST](#add-account-post )
    - [Request](#request-8 )
    - [Responses](#responses-8 )
- [Movement](#movement )
  - [Get Deposits | GET](#get-deposits-get )
    - [Request](#request-9 )
    - [Responses](#responses-9 )
  - [Get Withdrawals | GET](#get-withdrawals-get )
    - [Request](#request-10 )
    - [Responses](#responses-10 )
  - [Add Movement | POST](#add-movement-post )
    - [Request](#request-11 )
    - [Responses](#responses-11 )
  
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
```json
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
```json
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
```json
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
```json
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
```json
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
```json
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
```json
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
```json
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
```json
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
```json
{ "status": "success" }
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
```json
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
  
```json
[
    { "time": Long, "amount": Long, "source": String, "target": String },
    // ...
]
```
  
_error response_
```json
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
```json
{
    "amount" Long,
    "source_number" String,
    "target_number" String
}
```
  
####  Responses
  
_success response_
```json
status: 200, ok
```
  
_error response_
```json
status: 304, not modified
```
  
