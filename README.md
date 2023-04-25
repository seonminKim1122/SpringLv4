

## ✅ Lv 1. 요구사항

### 1. 전체 게시글 목록 조회 API
    - 제목, 작성자명, 작성 내용, 작성 날짜를 조회하기
    - 작성 날짜 기준 내림차순으로 정렬하기
### 2. 게시글 작성 API     
    - 제목, 작성자명, 비밀번호, 작성 내용을 저장하고
    - 저장된 게시글을 Client 로 반환하기
### 3. 선택한 게시글 조회 API 
    - 선택한 게시글의 제목, 작성자명, 작성 날짜, 작성 내용을 조회하기 
    (검색 기능이 아닙니다. 간단한 게시글 조회만 구현해주세요.)
### 4. 선택한 게시글 수정 API
    - 수정을 요청할 때 수정할 데이터와 비밀번호를 같이 보내서 서버에서 비밀번호 일치 여부를 확인 한 후
    - 제목, 작성자명, 작성 내용을 수정하고 수정된 게시글을 Client 로 반환하기
### 5. 선택한 게시글 삭제 API
    - 삭제를 요청할 때 비밀번호를 같이 보내서 서버에서 비밀번호 일치 여부를 확인 한 후
    - 선택한 게시글을 삭제하고 Client 로 성공했다는 표시 반환하기



# 항해 주특기 1주차 과제 (게시글 CRUD)

### 1. 수정, 삭제 API의 request를 어떤 방식으로 사용하셨나요? (param, query, body)
> body를 사용했습니다. 수정할 게시글의 id와 내용을 담은 BoardRequestDto 객체가 request body에 담겨 전송되고, 
  삭제 API에서는 삭제할 게시글의 id와 해당 게시글의 비밀번호를 담은 BoardRequestDto 객체가 requestbody에 담겨 전송시킵니다.

### 2. 어떤 상황에 어떤 방식의 request를 써야하나요?
>(1).Path Parmeter(스프링에서는 @PathVariable 이렇게 어노테이션 사용하여 씀)
경로의 일부분을 변수로 사용할 때 사용하는 어노테이션
URL 경로에서 중괄호({})로 감싼 부분을 변수로 사용할 수 있음
>>예시: @GetMapping("/users/{id}") -> id 변수는 @PathVariable("id")로 가져올 수 있음

>(2).Request Body(스프링에서는 @RequestBody 이렇게 어노테이션 사용하여 씀)
HTTP 요청 본문에 포함된 데이터를 자바 객체로 변환할 때 사용하는 어노테이션
요청 본문에 JSON, XML 등으로 전달되는 데이터를 Java 객체로 바인딩해서 사용할 수 있음
>>예시: @PostMapping("/users") -> 요청 본문에 담긴 데이터를 User 객체로 변환해서 @RequestBody로 받음

>(3).Query Parameter (스프링에서는 @RequestParam 이렇게 어노테이션 사용하여 씀)
HTTP 요청 파라미터를 받을 때 사용하는 어노테이션
URL 뒤에 ? 로 시작하는 파라미터를 처리할 때 사용함
파라미터 이름과 값을 직접 매핑해서 받을 수 있음
>>예시: @GetMapping("/users") -> URL 뒤에 ?name=John&age=30 과 같은 파라미터를 @RequestParam("name") String name, @RequestParam("age") int age 로 받음

### 3. RESTful한 API를 설계했나요? 어떤 부분이 그런가요? 어떤 부분이 그렇지 않나요?
RESTful한 API 설계 원칙중 일부
>-자원(Resource) - URI는 정보의 자원을 표현해야 한다. (/board : 게시글 리스트 자원,/board/{id} : 게시글 자원 )

>-행위(Verb) - HTTP Method로 표현한다.
(GET:데이터 조회, 
POST:데이터 생성, 
PUT:데이터 수정, 
DELETE:데이터 삭제)

>-표현(Representations) - 리소스에 대한 표현이어야 한다.
DELETE /board/{id} : 비밀번호를 RequestBody에 포함시켜서 작성하여서 보안적인 문제가 발생할거 같다....

### 4. 적절한 관심사 분리를 적용하였나요? (Controller, Repository, Service)
>-Controller:웹 페이지와의 상호작용(응답과 요청)을 담당하고 있으며, DB와 직접적으로 연관된 부분은 Service 계층으로 넘겨주어 로직이 처리되도록 설계함

>-Service: Repository 계층에서 받아온 데이터를 가공하거나 다른 Service 계층을 호출하는 등의 작업을 수행하여, Entity가 보이지 않도록 설계함

>-Repository:DB와 직접적으로 연관된 부분을 담당하고, Entity와 Dto를 이용하여 DB와의 데이터 변환을 처리해서 
            Service 계층에서는 Entity를 볼 필요 없이 Dto 객체만을 이용하여 데이터를 처리하도록 설계함
--------------------------------------------------------------------------------------------------------------------------------------------------------------

