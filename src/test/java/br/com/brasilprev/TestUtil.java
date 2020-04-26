package br.com.brasilprev;

import java.io.IOException;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

public class TestUtil {
	
	public static String convertObjectToJson(Object object) throws IOException {
		SimpleModule module = new SimpleModule();
		module.addSerializer(LocalDateTimeSerializer.INSTANCE);
		ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		mapper.registerModule(module);
		return mapper.writeValueAsString(object);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Object convertJsonToObject(String json, Class type) throws JsonParseException, JsonMappingException, IOException {
		SimpleModule module = new SimpleModule();
		module.addSerializer(LocalDateTimeSerializer.INSTANCE);
		ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		mapper.registerModule(module);
		return mapper.readValue(json, type);
	}
	
}
