**List Companies**
----
  Returns a json array containing of summarized company data objects.

* **URL**

  /rest/companies

* **Method:**

  `GET`
  
*  **URL Params**

  None

* **Data Params**

  None

* **Success Response:**

  * **Code:** 200 <br />
    **Content:** `[{ id : 1, name : "Google", "email" : "google@google.com", "address" : { "city" : "Palo Alto", "country" : "USA" } }, ... ]`
 
* **Sample Call:**

  ```$ curl -i -X GET https://company-crud.herokuapp.com/rest/companies```