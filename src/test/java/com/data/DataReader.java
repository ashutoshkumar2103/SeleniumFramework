package com.data;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.FileUtils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DataReader {

	public List<HashMap<String, String>> getJsonDataToHashMap(String filePath) throws IOException {

//		read json to String
		String jsonContent = FileUtils.readFileToString(new File(filePath), StandardCharsets.UTF_8);

//		String to HashMap - de serialization using jackson data bind
		ObjectMapper mapper = new ObjectMapper();
		List<HashMap<String, String>> data = mapper.readValue(jsonContent, new TypeReference<List<HashMap<String, String>>>() {});
		return data;
	}

}
