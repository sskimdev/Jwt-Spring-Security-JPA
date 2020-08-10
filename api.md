
**회원가입**
----
  이름, 이메일, 패스워드를 이용한 회원가입
  

* **URL**

  /v1/member/join

* **Method:**
  
  `POST`
  
*  **URL Params**

   <_If URL params exist, specify them in accordance with name mentioned in URL section. Separate into optional and required. Document data constraints._> 

   **Required:**
 
   `id=[integer]`

   **Optional:**
 
   `photo_id=[alphanumeric]`

* **Data Params**

  ```json
  {
    "email": "aaa@bbb.com",
    "password": "password1",
    "username": "홍길동"
  }
  ```

* **Success Response:**
  
  가입이 성공한 경우, sucess 필드에 true값을 전달한다.

  * **Code:** 200 <br />
    **Content:** 
    ```json
    {
      "data": "회원가입이 성공하였습니다.",
      "success": true,
      "timestamp": "2020-08-10T16:31:11.690Z"
    }
    ```
 
* **Error Response:**

  이메일 형식이 아닌경우, 패스워드 생성규칙이 맞이 않는 경우 등등...

  * **Code:** 406 NOT_ACCEPTABLE <br />
    **Content:**
    ```json
    {
      "timestamp": "2020-08-10T16:13:57.795+00:00",
      "status": 406,
      "error": "Not Acceptable",
      "message": "[Error] 유효하지 않은 값입니다... Password = 'password1'",
      "path": "/v1/member/join"
    }
    ```

  OR

  * **Code:** 409 CONFLICT <br />
    **Content:** 
    ```json
    {
      "data": "[Error] 이미 존재하는 값입니다. Email = 'aaa@korea.com'",
      "success": false,
      "timestamp": "2020-08-10T16:33:38.237Z",
      "cause": "com.bithumbhomework.member.exception.ResourceAlreadyInUseException"
    }
    ```

* **Sample Call:**

  ```bash
  curl -X POST "http://localhost:8000/v1/member/join" -H "accept: */*" -H "Content-Type: application/json" -d "{ \"email\": \"aaa@korea.com\", \"password\": \"aaa1234567890AAA\"}"
    ```

* **Notes:**

  <_This is where all uncertainties, commentary, discussion etc. can go. I recommend timestamping and identifying oneself when leaving comments here._> 
