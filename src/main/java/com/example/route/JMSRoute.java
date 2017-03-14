package com.example.route;

import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


/**
 * Created by Junior on 12/03/17.
 */
@Component
public class JMSRoute extends RouteBuilder {

    static final Logger log = LoggerFactory.getLogger(JMSRoute.class);


    @Override
    public void configure() throws Exception {
        from("{{inbound.endpoint}}")
                .transacted()
                .log(LoggingLevel.INFO, log, "Msg recebida")
                .process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {
                        log.info("Exchange {}" + exchange);
                    }
                })
                .loop()
                .simple("{{outbound.loop.count}}")
                .to("{{outbound.endpoint}}")
                .log(LoggingLevel.INFO, log, "Msg enviada, iterações: ${property.CamelLoopIndex}")
                .end();


    }
}
