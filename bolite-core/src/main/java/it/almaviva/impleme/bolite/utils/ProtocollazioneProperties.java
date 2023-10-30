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
@PropertySource("classpath:/config/protocollazione2.properties")
@ConfigurationProperties("protocollazione")
public class ProtocollazioneProperties {

	@Value("#{${protocollazione.structure.key.name}}")
	private HashMap<String, String> protocollazioneProperties;

}
