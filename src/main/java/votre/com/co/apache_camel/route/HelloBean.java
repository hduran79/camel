package votre.com.co.apache_camel.route;

import org.springframework.stereotype.Service;

@Service("helloBean")
public class HelloBean {

    public ResponseType sayHello() {
        return ResponseType.builder()
                .name("Hugo Duran")
                .message("Hola Mundo")
                .build();
    }

    public ResponseType hello(String name, String message) {
        return ResponseType.builder()
                .name(name)
                .message(message)
                .build();
    }

    public ResponseType postMessage(ResponseType dto) {
        return ResponseType.builder()
                .name(dto.getName())
                .message(dto.getMessage() + " - POST")
                .build();
    }
}
