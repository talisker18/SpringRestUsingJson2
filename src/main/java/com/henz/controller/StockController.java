package com.henz.controller;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.henz.model.Stock;

@RestController
@RequestMapping(value="/rest", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
public class StockController {
	
	//static database, in an anonymous class of HashMap
		private static final Map<String,Stock> STOCK_DB = new HashMap<String, Stock>(){
			{
				put("NASDAQ:AAPL", new Stock("NASDAQ:AAPL",975.63));
				put("NASDAQ:GOOG", new Stock("NASDAQ:GOOG",155.45));
				put("NASDAQ:AMZN", new Stock("NASDAQ:AMZN",1006.73));
			}
		};
		
		// /rest/stock?company=NASDAQ:AAPL
		@GetMapping("/stock") //it returns json by default because under the hood jackson deserializes to json
		public Stock getStockByCompanyName(@RequestParam(value = "company") String company) {
			Stock stock = STOCK_DB.get(company);
			return stock;
		}
		
		@GetMapping("stock/{company}")
		public Stock getInfo(@PathVariable("company") String company) {
			
			Stock stock;
			if(STOCK_DB.containsKey(company)) {
				stock = STOCK_DB.get(company);
			}else {
				// the GlobalExceptionHandler catches all RuntimeExceptions, like IllegalStateException
				throw new IllegalStateException("sorry we do not have stock information for company "+company);
			}
			
			return stock;
		}
		
		@GetMapping("stock/getAll")
		public Map<String,Stock> getAll() {
			
			return STOCK_DB;
		}
		
		// localhost:8080/rest/stock/NASDAQ:FB
		/*
		 * {
    "company": "NASDAQ:FB",
    "price": 1488.63
}
		 * 
		 * */
		@PostMapping("/stock/{company}")
		public void storeInfo(@PathVariable("company") String company, @Valid @RequestBody Stock stock) { //use @Valid or @Validated
			STOCK_DB.put(company, stock);
		}
		
		// if using post to update, there will be an update on existing record + new record (duplicate)
		@PutMapping("/stock/{company}")
		public void updateInfo(@PathVariable("company") String company, @Validated @RequestBody Stock stock) {
			STOCK_DB.put(company, stock);
		}

}
