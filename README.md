# Spring Security + JWT

+ H2 Database
+ Lombok
+ Swagger 2.9
+ Security
+ JWT

## JWT 구성

> 헤더.페이로드.서명

+ 헤더에는 해싱 알고리즘과 토큰 타입 정보가 있다.

+ 페이로드는 키/벨류 한쌍의 클레임을 다수 포함하고 있으며, 클레임은 등록, 공개, 비공개 클레임으로 구분된다.

+ 서명는 토큰의 마지막 부분으로 base64 기반의 헤더인코딩과 페이로드 인코딩을 합친 후에 HMAC의 경우 다시 한번 비밀키를 사용하여 해싱합니다. RSA의 경우 private key로 서명코드를 해싱하고 public key 로 verify할 수 있다.

+ https://jwt.io/



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

JWT 테스트

```java
public class JwtTokenTest {

    private final int TOKEN_VALIDITY = 18000;
    private final String SIGNING_KEY = "senspond";
    private String token = null;

    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                   .setClaims(claims)
                   .setSubject(subject)
                   .setIssuedAt(new Date(System.currentTimeMillis()))
                   .setExpiration(new Date(System.currentTimeMillis() + TOKEN_VALIDITY * 1000))
                   .signWith(SignatureAlgorithm.HS256, SIGNING_KEY).compact();
    }

    private <T> T extractClaim(Claims claims, Function<Claims, T> claimsResolver) {
        return claimsResolver.apply(claims);
    }

    
    @Test
    public void test(){
        System.out.println("============ Get Token ==========");
        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", "admin");
        this.token = createToken(claims,"senspond");
        System.out.println(token);
    }

    @AfterEach
    public void after(){
        System.out.println("============ From Token ==========");
        Claims claims = Jwts.parser().setSigningKey(SIGNING_KEY).parseClaimsJws(token).getBody();
        
        String userName = extractClaim(claims, Claims::getSubject );
        Date date = extractClaim(claims, Claims::getExpiration );

        System.out.println(claims);
        System.out.println(userName);
        System.out.println(date);
    }
}
```
+ 테스트 결과

```bash
============ Get Token ==========
eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzZW5zcG9uZCIsInJvbGVzIjoiYWRtaW4iLCJleHAiOjE2MTYwNjY3ODUsImlhdCI6MTYxNjA0ODc4NX0.p5quta7BdzSKHSo9QGL3QipepTSfO9i3jaVDHiG0CBk
============ From Token ==========
{sub=senspond, roles=admin, exp=1616066785, iat=1616048785}
senspond
Thu Mar 18 20:26:25 KST 2021
```


### security config

```java

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = false, prePostEnabled = true, jsr250Enabled = false)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String[] AUTH_WHITELIST = {
        "/swagger-resources/**",
        "/swagger-ui.html",
        "/v2/api-docs",
        "/webjars/**",
        "/h2-console/**"
    };

    @Autowired
    private JwtAuthenticationEntryPoint unauthorizedEntryPoint;

    // 시큐리티 적용하지 않는 정적자원
    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers(AUTH_WHITELIST)
                      .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailServiceBean()).passwordEncoder(passwodEncoderBean());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors()
            .and()
            .csrf().disable().authorizeRequests()
            .antMatchers("/","/signup","/authenticate").permitAll() // 홈, 회원가입, 로그인 검증 url에 접근허용
            .anyRequest().authenticated()                           // 을 제외한 모든 요청에 인증 요구
            .and()
            .exceptionHandling().authenticationEntryPoint(unauthorizedEntryPoint).and()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
            
        http.addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);;
        //  super.configure(http);
    }

    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public JwtAuthenticationFilter authenticationTokenFilterBean() throws Exception {
        return new JwtAuthenticationFilter();
    }
    @Bean
    public CustomUserDetailService userDetailServiceBean() {
        return new CustomUserDetailService();
    }

    @Bean
    public PasswordEncoder passwodEncoderBean() {
        return new BCryptPasswordEncoder();
    }
}

```