package com.rdshinde.microservices.inventory;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Import;
import org.testcontainers.containers.MySQLContainer;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Import(TestcontainersConfiguration.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class InventoryServiceApplicationTests {

	@ServiceConnection
	static MySQLContainer mySQLContainer = new MySQLContainer("mysql:8.3.0");
	@LocalServerPort
	private Integer port;

	@BeforeEach
	void setup(){
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = port;
	}
	static {
		mySQLContainer.start();
	}

	@Test
	void shouldReadInventory() {
		var responsePos = RestAssured.given()
				.when()
				.get("/api/inventory?skuCode=iPhone_14&quantity=100")
				.then()
				.log()
				.all()
				.statusCode(200)
				.extract().response().as(Boolean.class);
		assertTrue(responsePos);

		var responseNeg = RestAssured.given()
				.when()
				.get("/api/inventory?skuCode=iPhone_14&quantity=1000")
				.then()
				.log()
				.all()
				.statusCode(200)
				.extract().response().as(Boolean.class);
		assertFalse(responseNeg);

	}

}
