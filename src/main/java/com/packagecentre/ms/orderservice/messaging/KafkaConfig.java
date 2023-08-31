package com.packagecentre.ms.orderservice.messaging;

import java.util.*;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.EnableKafka;
//import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.*;
import org.springframework.kafka.support.serializer.JsonSerializer;

@EnableKafka
@Configuration
public class KafkaConfig {
	
	
	@Bean
	public ProducerFactory<String, ?> producerFactory(){
		Map<String, Object> config = new HashMap<>();
		
		
		config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
		config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,JsonSerializer.class);
		
		
		return new DefaultKafkaProducerFactory<>(config);
	}
	
	
	@Bean	  
    public KafkaTemplate<String, ?> kafkaTemplate()
    {
  
        return new KafkaTemplate<>(producerFactory());
		
    }

}
