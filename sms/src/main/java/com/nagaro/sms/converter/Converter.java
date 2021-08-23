package com.nagaro.sms.converter;

public interface Converter<T, R> {

	R convert(T t);

}
