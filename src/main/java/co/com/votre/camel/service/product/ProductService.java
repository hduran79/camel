package co.com.votre.camel.service.product;

import org.springframework.stereotype.Service;

import co.com.votre.camel.dto.product.ResponseTypeDTO;

@Service("productService")
public class ProductService {

    public ResponseTypeDTO sayHello() {
        return ResponseTypeDTO.builder()
                .name("Hugo Duran")
                .message("Hola Mundo")
                .build();
    }

    public ResponseTypeDTO hello(String name, String message) {
        return ResponseTypeDTO.builder()
                .name(name)
                .message(message)
                .build();
    }

    public ResponseTypeDTO postMessage(ResponseTypeDTO dto) {
        return ResponseTypeDTO.builder()
                .name(dto.getName())
                .message(dto.getMessage() + " - POST")
                .build();
    }
}
