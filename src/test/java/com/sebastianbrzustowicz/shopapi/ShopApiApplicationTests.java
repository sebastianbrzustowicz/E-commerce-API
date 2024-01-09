package com.sebastianbrzustowicz.shopapi;

import com.sebastianbrzustowicz.shopapi.controller.ShopController;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class ShopApiApplicationTests {

	@InjectMocks
	private ShopController shopController;

	@Test
	public void testRegisterUser_SuccessfulRegistration() {
		// Given
		//User newUser = new User(UUID.randomUUID().toString(), "loginTest", "pwTest",
		//		"test@example.com", 666777888, "user", "dateExample" );
		//Mockito.when(shopController.getAll(Mockito.anyString())).thenReturn(false);
		//Mockito.when(shopController.getAll(Mockito.any(User.class))).thenReturn(1);
//
		//// When
		//int result = shopController.getAll(newUser);
		//int expected = 1;
		//// Then
		//assertEquals(expected, result);
	}

}
