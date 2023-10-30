package it.almaviva.impleme.bolite.utils;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Setter
@Getter
@Component
@PropertySource("classpath:/config/pmpay.properties")
@ConfigurationProperties("pmpay")
public class PmPayProperties {
	
	@Value("#{${pmpay.structure.key.name}}")
	private HashMap<String, String> pmPayProperties;



}
