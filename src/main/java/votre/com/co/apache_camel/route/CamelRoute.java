package votre.com.co.apache_camel.route;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

@Component
public class CamelRoute extends RouteBuilder {

    @Autowired
    HelloBean helloBean;

    @Override
    public void configure() throws Exception {

        restConfiguration()
                .component("servlet")
                .enableCORS(true)
                .bindingMode(RestBindingMode.json)
                .dataFormatProperty("prettyPrint", "true");

        rest("/camel-mybatis").description("User REST service")
                .consumes("application/json")
                .produces("application/json")

                // Method Get
                .get()
                .description("Methdo Get withOut Param consume Bean")
                .outType(ResponseType.class)
                .to("bean:helloBean?method=sayHello")

                .get("/hello/{name}")
                .description("Methdo Get with Param")
                .to("direct:return-message")

                .get("/hello-world/{name}/{message}")
                .description("Methdo Get consume Bean")
                .to("direct:hello-world")

                .post("/post-message")
                .consumes(MediaType.APPLICATION_JSON_VALUE)
                .type(ResponseType.class)
                .outType(ResponseType.class)
                .to("direct:post-message");

        from("direct:return-message")
                .setBody(simple("Hello World ${header.name}"));

        from("direct:hello-world")
                .bean(helloBean, "hello(${header.name}, ${header.message})");

        from("direct:post-message")
            .bean(helloBean, "postMessage( ${body})");

    }

}
