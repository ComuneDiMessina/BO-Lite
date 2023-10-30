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
@PropertySource("classpath:bolite.properties")
@ConfigurationProperties("bolite")
public class BoliteProperties {
	
	@Value("#{${bolite.structure.key.name}}")
	private HashMap<String, String> boliteProperties;



}
