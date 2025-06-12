# AuthService

Spring Boot 기반 인증 및 권한 관리 서비스입니다.  
회원가입, 로그인, JWT 기반 인증, 관리자 권한 부여 기능을 제공합니다.

---

## 주요 기능
- 회원가입 (이메일, 비밀번호, 닉네임)
- 로그인 (JWT 토큰 발급)
- 관리자 권한 부여 (PATCH /admin/{userId})
- JWT 인증 필터 적용
- Swagger API 문서 제공
- H2 인메모리 DB 및 JPA Auditing 지원

---

## 실행 방법

1. 프로젝트를 클론하거나 다운로드 받습니다.
2. `application.yml`에서 JWT 시크릿 키 등 환경 설정을 확인합니다.</br>
   맨 아래 application.yml 파일 내용을 확인하세요.
3. IDE에서 `AuthserviceApplication` 클래스를 실행합니다.
4. 애플리케이션 구동 후, 아래 URL로 접속하여 Swagger UI를 확인할 수 있습니다</br>
   http://localhost:8080/swagger-ui/index.html
5. H2 콘솔 접속 URL: http://localhost:8080/h2-console
- JDBC URL: `jdbc:h2:mem:testdb`

---

## API 테스트 방법 및 순서

### 1. Swagger API 문서 접속
- `http://localhost:8080/swagger-ui/index.html` 접속 후 API 명세 확인

### 2. 회원가입 테스트 (사용자 2명 등록)
- `/sign-up` POST 요청으로 사용자 2명 가입
- 예시 요청 바디:
```json
{
"email": "test1@example.com",
"password": "test1",
"nickname": "test1"
} 
```
### 3. H2 콘솔 접속 및 권한 수동 변경
http://localhost:8080/h2-console 접속

SQL 쿼리 실행: UPDATE users SET role = 'ADMIN' WHERE id = 1;

### 4. 관리자 사용자 로그인
   /sign-in POST 요청

사용자 1의 이메일과 비밀번호로 로그인</br>
로그인 성공 시 JWT 토큰을 응답받음

### 5. Swagger Authorize에 JWT 토큰 등록
Swagger UI 상단 Authorize 버튼 클릭</br>
Bearer 접두사 없이 토큰 문자열만 입력 후 확인

### 6. 관리자 권한 부여 API 실행
/admin/{userId} PATCH 요청

경로 변수에 2 입력 (두 번째 사용자 ID)</br>
실행 후, 204 No Content 응답 확인</br>

참고 사항
JWT 토큰은 Authorization 헤더에 Bearer <토큰> 형식으로 전달되어야 합니다.</br>
/sign-up, /sign-in, Swagger 관련 URL, H2 콘솔은 인증 없이 접근 가능합니다.</br>
관리자 권한 부여 API는 ADMIN 권한 사용자만 접근 가능합니다.</br>

### 프로젝트 구조
domain.entity : 도메인 엔티티 및 역할(Role) 정의</br>
domain.repository : 사용자 저장소 인터페이스</br>
infrastructure.repository : JPA 기반 사용자 저장소 구현체</br>
infrastructure.security : JWT 생성 및 검증, 인증 필터, 시큐리티 설정</br>
application.service : 비즈니스 로직 처리</br>
presentation.controller : REST API 컨트롤러 및 DTO</br>

### application.yml 파일

```yaml
spring:
  application:
    name: auth-service
  jpa:
    hibernate:
      ddl-auto: create
    show-sql: true
    open-in-view: false
  h2:
    console:
      enabled: true
      path: /h2-console
  datasource:
    url: jdbc:h2:mem:testdb
    driver-class-name: org.h2.Driver
    username: auth
    password:

security:
  jwt:
    secret: EmVW5EwQoEkcwJi7fbf0TeG29WYfVRux/iLgXkgGeSr1KlI1duU6ekZC2nTcmcv7EWoiD280bOYUfu35DlcqMQ==
```

