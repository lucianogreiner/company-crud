**Company REST Assessment**
  * **Author: Luciano Greiner**
 
**List Companies**
----
  Returns a json array containing of summarized company data objects.
* **URL**:
  /rest/companies
* **Method:**
  `GET`
*  **URL Params:**
  None
* **Data Params:**
  None
* **Success Response:**
  * **Code:** 200
     * **Description:** OK
     * **Content:**
  
  ```[{ "id" : 1, "name" : "Google", "email" : "google@google.com", "address" : { "city" : "Palo Alto", "country" : "USA" } }, ... ]```
 
* **Sample Call:**

  ```$ curl -i -X GET https://company-crud.herokuapp.com/rest/companies```
  
**Load Company Details**
----
  Returns a json object containing a company record information.
* **URL:**
  /rest/companies/:id
* **Method:**
  `GET`
*  **URL Params:**

   **Required:**
    `id=[integer]`

* **Data Params:**
  None

* **Success Response:**

  * **Code:** 200
     * **Description:** OK
     * **Content:** 
     
        ```{ "id" : 1, "name" : "Google", "email" : "google@google.com", "phone" : "123 123-45678", "address" : { "street" : "Google Street", "city" : "Palo Alto", "country" : "USA" }, "owners" : ["Larry Page", ...] }```
 
* **Error Responses:**

  * **Code:** 404
     * **Description:** NOT FOUND
     * **Detail:** Returned in case the given company id is not found
 
* **Sample Call:**

  ```$ curl -i -X GET https://company-crud.herokuapp.com/rest/companies/1```
  
**Insert Company Record**
----
  Insert a given a json object containing a company record into the database.
* **URL**:
  /rest/companies
* **Method:**
  `POST`
*  **URL Params:**
  None
* **Data Params:**
  * **Content-Type:** application/json
  * **Sample Data:** ```{ "name" : "Acme Inc", "email" : "acme@acme.com", "phone" : "123 123-45678", "address" : { "street" : "Acme Street", "city" : "Orlando", "country" : "USA" }, "owners" : ["John Smith", ...] }```
* **Success Response:**
  * **Code:** 201
     * **Description:** CREATED
     * **Headers:**
        *   **Location:** /companies/20 (Inserted company location)

* **Error Responses:**

  * **Code:** 400
     * **Description:** BAD REQUEST
     * **Detail:** Returned in case the given company object is not valid

* **Sample Call:**

  ```$ curl -H "Content-Type: application/json" -X POST -d '{"name":"Acme Inc","email":"acme@acme.com","phone":"123 123-45678","address":{"street":"Acme Street","city":"Orlando","country":"USA"},"owners":["John Smith"]}' https://company-crud.herokuapp.com/rest/companies```
  
    
**Update Company Record**
----
  Update the record of a given company ID
* **URL**:
  /rest/companies/:id
* **Method:**
  `PUT`
*  **URL Params:**

   **Required:**
    `id=[integer]`

* **Data Params:**
  * **Content-Type:** application/json
  * **Sample Data:** ```{ "name" : "Acme Inc", "email" : "acme@acme.com", "phone" : "123 123-45678", "address" : { "street" : "Acme Street", "city" : "Orlando", "country" : "USA" }, "owners" : ["Josh Town", ...] }```
* **Success Response:**
  * **Code:** 202
     * **Description:** ACCEPTED
     * **Detail:** Returned in case the given company was successfuly updated

* **Error Responses:**

  * **Code:** 404
     * **Description:** NOT FOUND
     * **Detail:** Returned in case the given company id is not found
 
  * **Code:** 400
     * **Description:** BAD REQUEST
     * **Detail:** Returned in case the given company object is not valid
 

* **Sample Call:**

  ```$ curl -H "Content-Type: application/json" -X PUT -d '{"name":"Acme Inc","email":"acme@acme.com","phone":"123 123-45678","address":{"street":"Acme Street","city":"Orlando","country":"USA"},"owners":["Josh Town"]}' https://company-crud.herokuapp.com/rest/companies/20```
  
  
      
**Add Company Owner**
----
  Add a named owner to a company record of the given ID
* **URL**:
  /rest/companies/:id/owner
* **Method:**
  `PUT`
*  **URL Params:**

   **Required:**
    `id=[integer]`

* **Data Params:**
  * **Content-Type:** application/x-www-form-urlencoded
  * **Required:**
    `name=[string]`
* **Success Response:**
     * **Code:** 202
     * **Description:** ACCEPTED
     * **Detail:** Returned in case the given company owner was successfuly added
 
* **Error Responses:**

  * **Code:** 404
     * **Description:** NOT FOUND
     * **Detail:** Returned in case the given company id is not found

* **Sample Call:**

  ```$ curl -H "Content-Type: application/json" -X PUT -d 'name=Marie+Cruz' https://company-crud.herokuapp.com/rest/companies/20/owner```