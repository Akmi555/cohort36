package de.ait_tr.lesson_13_testing.product;

import de.ait_tr.lesson_13_testing.role.Role;
import de.ait_tr.lesson_13_testing.role.RoleRepository;
import de.ait_tr.lesson_13_testing.security.sec_dto.TokenResponseDto;
import de.ait_tr.lesson_13_testing.user.User;
import de.ait_tr.lesson_13_testing.user.UserRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProductControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ProductRepository productRepository;

    private TestRestTemplate template;
    private HttpHeaders headers;
    private ProductDto testProduct;
    private static Long testProductId;
    private String adminAccessToken;
    private String userAccessToken;

    private final String ADMIN_ROLE_TITLE = "ROLE_ADMIN";
    private final String USER_ROLE_TITLE = "ROLE_USER";
    private final String TEST_ADMIN_NAME = "Test Admin";
    private final String TEST_USER_NAME = "Test User";
    private final String TEST_PASSWORD = "Test password";
    private final String TEST_PRODUCT_TITLE = "Test product";
    private final BigDecimal TEST_PRODUCT_PRICE = new BigDecimal(777);

    private final String URL_PREFIX = "http://localhost:";
    private final String AUTHORIZATION_RESOURCE = "/auth";
    private final String PRODUCTS_RESOURCE = "/products";
    private final String LOGIN_ENDPOINT = "/login";
    private final String ALL_ENDPOINT = "/all";
    private final String ID_PARAM_TITLE = "?id=";

    private final String BEARER_PREFIX = "Bearer ";
    private final String AUTH_HEADER_NAME = "Authorization";

    @BeforeEach
    public void setUp() {
        template = new TestRestTemplate();
        headers = new HttpHeaders();

        testProduct = new ProductDto();
        testProduct.setTitle(TEST_PRODUCT_TITLE);
        testProduct.setPrice(TEST_PRODUCT_PRICE);

        BCryptPasswordEncoder encoder = null;
        Role roleAdmin;
        Role roleUser = null;

        User admin = userRepository.findByUsername(TEST_ADMIN_NAME).orElse(null);
        User user = userRepository.findByUsername(TEST_USER_NAME).orElse(null);

        if (admin == null) {
            encoder = new BCryptPasswordEncoder();
            roleAdmin = roleRepository.findByTitle(ADMIN_ROLE_TITLE).orElse(null);
            roleUser = roleRepository.findByTitle(USER_ROLE_TITLE).orElse(null);

            if (roleAdmin == null || roleUser == null) {
                throw new RuntimeException("The database doesn't have necessary roles");
            }

            admin = new User();
            admin.setUsername(TEST_ADMIN_NAME);
            admin.setPassword(encoder.encode(TEST_PASSWORD));
            admin.setRoles(Set.of(roleAdmin, roleUser));

            userRepository.save(admin);
        }

        if (user == null) {
            encoder = encoder == null ? new BCryptPasswordEncoder() : encoder;
            roleUser = roleUser == null ?
                    roleRepository.findByTitle(USER_ROLE_TITLE).orElse(null) : roleUser;

            if (roleUser == null) {
                throw new RuntimeException("The database doesn't have necessary roles");
            }

            user = new User();
            user.setUsername(TEST_USER_NAME);
            user.setPassword(encoder.encode(TEST_PASSWORD));
            user.setRoles(Set.of(roleUser));

            userRepository.save(user);
        }

        admin.setPassword(TEST_PASSWORD);
        admin.setRoles(null);

        user.setPassword(TEST_PASSWORD);
        user.setRoles(null);

        String url = URL_PREFIX + port + AUTHORIZATION_RESOURCE + LOGIN_ENDPOINT;
        HttpEntity<User> request = new HttpEntity<>(admin, headers);

        ResponseEntity<TokenResponseDto> response = template
                .exchange(url, HttpMethod.POST, request, TokenResponseDto.class);

        assertTrue(response.hasBody(), "Authorization response body is empty");
        adminAccessToken = BEARER_PREFIX + response.getBody().getAccessToken();

        request = new HttpEntity<>(user, headers);

        response = template
                .exchange(url, HttpMethod.POST, request, TokenResponseDto.class);

        assertTrue(response.hasBody(), "Authorization response body is empty");
        userAccessToken = BEARER_PREFIX + response.getBody().getAccessToken();
    }

    @Test
    public void positiveGettingAllProductsWithoutAuthorization() {

        String url = URL_PREFIX + port + PRODUCTS_RESOURCE + ALL_ENDPOINT;
        HttpEntity<Void> request = new HttpEntity<>(headers);

        ResponseEntity<ProductDto[]> response = template
                .exchange(url, HttpMethod.GET, request, ProductDto[].class);

        assertEquals(HttpStatus.OK, response.getStatusCode(), "Response has unexpected status");
        assertTrue(response.hasBody(), "Response doesn't have a body");
    }

    @Test
    public void negativeSavingProductWithoutAuthorization() {

        String url = URL_PREFIX + port + PRODUCTS_RESOURCE;
        HttpEntity<ProductDto> request = new HttpEntity<>(testProduct, headers);

        ResponseEntity<ProductDto> response = template
                .exchange(url, HttpMethod.POST, request, ProductDto.class);

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode(), "Response has unexpected status");
        assertFalse(response.hasBody(), "Response has unexpected body");
    }

    @Test
    public void negativeSavingProductWithUserAuthorization() {

        String url = URL_PREFIX + port + PRODUCTS_RESOURCE;
        headers.put(AUTH_HEADER_NAME, List.of(userAccessToken));
        HttpEntity<ProductDto> entity = new HttpEntity<>(testProduct, headers);

        ResponseEntity<ProductDto> response = template
                .exchange(url, HttpMethod.POST, entity, ProductDto.class);

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode(), "Response has unexpected status");
    }

    @Test
    @Order(1)
    public void positiveSavingProductWithAdminAuthorization() {

        String url = URL_PREFIX + port + PRODUCTS_RESOURCE;
        headers.put(AUTH_HEADER_NAME, List.of(adminAccessToken));
        HttpEntity<ProductDto> entity = new HttpEntity<>(testProduct, headers);

        ResponseEntity<ProductDto> response = template
                .exchange(url, HttpMethod.POST, entity, ProductDto.class);

        assertEquals(HttpStatus.OK, response.getStatusCode(), "Response has unexpected status");

        ProductDto savedProduct = response.getBody();
        assertNotNull(savedProduct, "Response body doesn't have a saved product");
        assertEquals(testProduct.getTitle(), savedProduct.getTitle(), "Saved product has unexpected title");

        testProductId = savedProduct.getId();
    }

    @Test
    @Order(2)
    public void negativeGettingProductByIdWithoutAuthorization() {

        String url = URL_PREFIX + port + PRODUCTS_RESOURCE + ID_PARAM_TITLE + testProductId;
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<ProductDto> response = template
                .exchange(url, HttpMethod.GET, entity, ProductDto.class);

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode(), "Response has unexpected status");
        assertFalse(response.hasBody(), "Response has unexpected body");
    }

    @Test
    @Order(3)
    public void negativeGettingProductByIdWithBasicAuthorization() {

        String url = URL_PREFIX + port + PRODUCTS_RESOURCE + ID_PARAM_TITLE + testProductId;
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<ProductDto> response = template
                .withBasicAuth(TEST_USER_NAME, TEST_PASSWORD)
                .exchange(url, HttpMethod.GET, entity, ProductDto.class);

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode(), "Response has unexpected status");
        assertFalse(response.hasBody(), "Response has unexpected body");
    }

    @Test
    @Order(4)
    public void negativeGettingProductByIdWithIncorrectToken() {

        String url = URL_PREFIX + port + PRODUCTS_RESOURCE + ID_PARAM_TITLE + 1;
        headers.put(AUTH_HEADER_NAME, List.of(userAccessToken + "a"));
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<ProductDto> response = template
                .exchange(url, HttpMethod.GET, entity, ProductDto.class);

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode(), "Response has unexpected status");
        assertFalse(response.hasBody(), "Response has unexpected body");
    }

    @Test
    @Order(5)
    public void positiveGettingProductByIdWithCorrectToken() {

        String url = URL_PREFIX + port + PRODUCTS_RESOURCE + ID_PARAM_TITLE + testProductId;
        headers.put(AUTH_HEADER_NAME, List.of(userAccessToken));
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<ProductDto> response = template
                .exchange(url, HttpMethod.GET, entity, ProductDto.class);

        assertEquals(HttpStatus.OK, response.getStatusCode(), "Response has unexpected status");

        ProductDto savedProduct = response.getBody();
        assertNotNull(savedProduct, "Response body doesn't have a saved product");
        assertEquals(testProduct.getTitle(), savedProduct.getTitle(), "Saved product has unexpected title");

        productRepository.deleteById(savedProduct.getId());
    }
}