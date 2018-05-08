package com.dazito.oauthexample.utils;

import org.modelmapper.ModelMapper;

public class ModelConverter {
	private static final ModelMapper modelMapper = new ModelMapper();
	
	public static <T> T convert(Object input, Class<T> clazz) {
		return modelMapper.map(input, clazz);
	}
}
