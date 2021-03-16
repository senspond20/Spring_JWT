# Spring Security + JWT

+ H2 Database
+ Lombok
+ Swagger 2.9
+ Security
+ JWT


```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
</dependency>

<dependency>
    <groupId>javax.xml.bind</groupId>
    <artifactId>jaxb-api</artifactId>
    <version>2.3.1</version>
</dependency>
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt</artifactId>
    <version>0.9.1</version>
</dependency>

```

## JWT 구성

> 헤더.페이로드.서명

+ 헤더에는 해싱 알고리즘과 토큰 타입 정보가 있다.

+ 페이로드는 키/벨류 한쌍의 클레임을 다수 포함하고 있으며, 클레임은 등록, 공개, 비공개 클레임으로 구분된다.

+ 서명는 토큰의 마지막 부분으로 base64 기반의 헤더인코딩과 페이로드 인코딩을 합친 후에 HMAC의 경우 다시 한번 비밀키를 사용하여 해싱합니다. RSA의 경우 private key로 서명코드를 해싱하고 public key 로 verify할 수 있다.


```

```