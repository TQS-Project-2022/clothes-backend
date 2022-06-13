package pt.ua.clothesbackend.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import pt.ua.clothesbackend.dto.CreateProductDto;
import pt.ua.clothesbackend.model.Product;
import pt.ua.clothesbackend.repositories.ProductRepository;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Testcontainers
class ProductControllerTest {

    @Container
    public static MySQLContainer mySQLContainer = new MySQLContainer("mysql")
            .withDatabaseName("database")
            .withUsername("user")
            .withPassword("password");

    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mySQLContainer::getJdbcUrl);
        registry.add("spring.datasource.password", mySQLContainer::getPassword);
        registry.add("spring.datasource.username", mySQLContainer::getUsername);
    }

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup(){
        productRepository.deleteAll();
    }

    @Test
    public void givenProductDto_whenCreateProduct_thenReturnSavedProduct() throws Exception {

        CreateProductDto createProductDto = CreateProductDto.builder()
                .category("shoes")
                .name("name1")
                .price(1)
                .build();

        ResultActions response = mockMvc.perform(post("/products/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createProductDto)));

        System.out.println(objectMapper.writeValueAsString(createProductDto));

        response.andDo(print()).andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is(createProductDto.getName())))
                .andExpect(jsonPath("$.price", is(createProductDto.getPrice())))
                .andExpect(jsonPath("$.category", is(createProductDto.getCategory())));
    }

    @Test
    public void givenListOfProducts_whenGetAllProducts_thenReturnProductList() throws Exception {

        Product product1 = Product.builder()
                .category("shoes")
                .name("name1")
                .price(1)
                .build();
        Product product2 = Product.builder()
                .category("t-shirt")
                .name("name2")
                .price(5)
                .build();

        productRepository.save(product1);
        productRepository.save(product2);

        ResultActions response = mockMvc.perform(get("/products"));

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(2)));

    }

    @Test
    public void givenProductId_whenGetProductById_thenReturnProduct() throws Exception {
        Product product1 = Product.builder()
                .category("shoes")
                .name("name1")
                .price(1)
                .build();
        productRepository.save(product1);

        ResultActions response = mockMvc.perform(get("/products/{id}", product1.getId()));

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(product1.getName())))
                .andExpect(jsonPath("$.price", is(product1.getPrice())))
                .andExpect(jsonPath("$.category", is(product1.getCategory())));
    }

    @Test
    public void givenInvalidProductId_whenGetProductById_thenReturnBadRequest() throws Exception {
        Product product1 = Product.builder()
                .category("shoes")
                .name("name1")
                .price(1)
                .build();
        productRepository.save(product1);

        ResultActions response = mockMvc.perform(get("/products/{id}", product1.getId() + 999));

        response.andExpect(status().isBadRequest());
    }

}