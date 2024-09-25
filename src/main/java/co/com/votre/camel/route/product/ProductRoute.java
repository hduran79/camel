package co.com.votre.camel.route.product;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import co.com.votre.camel.dto.product.CampaingDTO;
import co.com.votre.camel.dto.product.ResponseTypeDTO;
import co.com.votre.camel.service.product.ProductService;

@Component
public class ProductRoute extends RouteBuilder {

        @Autowired
        ProductService productService;

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
                .outType(ResponseTypeDTO.class)
                .to("bean:productService?method=sayHello")

                .get("/hello/{name}")
                .description("Methdo Get with Param")
                .to("direct:return-message")

                .get("/hello-world/{name}/{message}")
                .description("Methdo Get consume Bean")
                .to("direct:hello-world")

                .post("/post-message")
                .consumes(MediaType.APPLICATION_JSON_VALUE)
                .type(ResponseTypeDTO.class)
                .outType(ResponseTypeDTO.class)
                .to("direct:post-message")
                
                .get("/campaign-rank/{company}/{media}")
                .description("Methdo Get consume Bean")
                .to("direct:get-mybatis")
                
                .post("/post-mybatis")
                .consumes(MediaType.APPLICATION_JSON_VALUE)
                .type(CampaingDTO.class)
                .outType(ResponseTypeDTO.class)
                .to("direct:post-mybatis");

        from("direct:return-message")
                .setBody(simple("Hello World ${header.name}"));

        from("direct:hello-world")
                .bean(productService, "hello(${header.name}, ${header.message})");

        from("direct:get-mybatis")
        .setBody(exchange -> exchange.getIn().getHeaders())
        .log("Parameters Header: ${headers.company}")
        .to("mybatis:getCampaignRank?statementType=SelectOne");

        from("direct:post-mybatis")
        .log("Parameters Body: $body}")
        .to("mybatis:getCampaignRank?statementType=SelectOne")
        .log("Parameters Body: ${body}");

    }

}
