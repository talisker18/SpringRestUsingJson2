package com.henz.model;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Value;

@Value //@Value is the immutable variant of @Data; all fields are made private and final by default, and setters are not generated
@AllArgsConstructor //auto made constructor using all fields
public class Stock{
	
	@NotNull //add dependency: Hibernate Validator, the bean validation reference implementation -> String company is not allowed to be null
	@Size(max = 10) //max String = 10 --> 400 bad request will be thrown if we try to post new stock with name > 10 chars --> test it with postman
	//this exception handling is possible with javax.validation.constraints.Size in combination with @Validated or @Valid annotation
	String company;
	
	@NotNull
	@DecimalMin("0.01")
	Double price;
}
