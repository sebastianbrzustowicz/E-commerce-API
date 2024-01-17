package com.sebastianbrzustowicz.shopapi.controller;

import com.sebastianbrzustowicz.shopapi.service.ChatGPTHelper;
import com.sebastianbrzustowicz.shopapi.model.GPTRequest;
import com.sebastianbrzustowicz.shopapi.model.Product;
import com.sebastianbrzustowicz.shopapi.repository.AIRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ai")
public class AIController {

    private final AIRepository aiRepository;

    public AIController(AIRepository aiRepository) {
        this.aiRepository = aiRepository;
    }

    @GetMapping("/test")
    public String getAll() {
        return "ai test endpoint works";
    }

    @GetMapping("/testdb")
    public String getDBname() {
        return aiRepository.getDBname();
    }

    @PostMapping("/askGPT")
    public String ChatGPT(@RequestBody GPTRequest chatbotrequest) {
        String requestType = chatbotrequest.getRequestType();
        String clientRequest = chatbotrequest.getClientRequest();
        //String preInfo = aiRepository.preInfo();
        String preInfo = "You are E-commerce assistant. " +
                "You task is to help customers and give them information about data that will be provided to you. " +
                "Online retail information: this is shop which is mosly focused on selling drones and other robots." +
                "It is only online shop without stationary headquater. If customer want to contact with shop owner there is phone number:" +
                "123456789. List of products will be provided later. Answer only question that is about our online shop with customer language. " +
                "Reply without greeting. You have to pretend to be our shop support since now.\n";

        //String beforeProductsInfo = aiRepository.middleInfo();
        String beforeProductsInfo = "\nNow you will receive product list from our online shop.\n";

        List<Product> actualProducts = aiRepository.getAllProducts();
        String productsInfo = beforeProductsInfo + aiRepository.convertProducts(actualProducts);

        //String endInfo = aiRepository.endInfo();
        String middleInfo = "\n\nReply to the user according to the current state of the shop, but mostly focus on customer question. Question from customer is:\n";

        String question = preInfo + productsInfo + middleInfo + clientRequest;

        ChatGPTHelper chatGPTHelper = new ChatGPTHelper();
        String GPTResponse = chatGPTHelper.getGPTAdvice(question);

        return GPTResponse;
    }

}
