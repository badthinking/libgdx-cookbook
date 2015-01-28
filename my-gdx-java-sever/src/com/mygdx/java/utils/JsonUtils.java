package com.mygdx.java.utils;

import java.io.IOException;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;

public class JsonUtils {
	public static String getValuse(String json, String key) {
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode jsonNode = null;
		String valuse = null;
		try {
			jsonNode = objectMapper.readTree(json);
			valuse = jsonNode.path(key).toString();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return valuse;
	}
}
