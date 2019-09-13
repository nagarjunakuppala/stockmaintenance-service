package org.uhcl.edu.stockmaintenance.stockmaintenanceservice.resource;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("rest/stockmaintenance")
//@CrossOrigin(origins="http://10.0.0.96:4200")
public class StockMaintenanceResource {
	
	@Autowired
	RestTemplate restTemplate;
	
	@GetMapping("/{userName}")
	public List<Stock> getQuotes(@PathVariable("userName") final String userName){
		
		/*ResponseEntity<List<Stock>> resposnseEntity = restTemplate.exchange("http://db-service/rest/db/stock/"+userName, HttpMethod.GET, null, new ParameterizedTypeReference<List<String>>() {
		});
		
		List<Stock> stocks = resposnseEntity.getBody();
		System.out.println(stocks);*/
		
		/*return quotes.stream()
				.map(quote ->{
					Stock stock = getStocksByUserName(userName);
					return new Quote(quote, stock.getQuote().getPrice()); 
				})
				.collect(Collectors.toList());*/
		
		return getStocksByUserName(userName);
	}
	
	/*@PostMapping("/store")
	public List<Stock> storeStocks(@RequestBody final Stocks stocks) {
		stocks.getStocks().stream().forEach(stock -> {
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			Date date = new Date();
			System.out.println(dateFormat.format(date)); //2016/11/16 12:08:43
			stock.setStockDate(date);
			stock.setUserName(stocks.getUserName());
			stockRepository.save(stock);
		});
		return getStocksByUserName(stocks.getUserName());
	}*/
	
	private List<Stock> getStocksByUserName(String userName){
		ResponseEntity<List<Object>> resposnseEntity = restTemplate.exchange("http://db-service/rest/db/stock/"+userName, HttpMethod.GET, null, new ParameterizedTypeReference<List<Object>>() {
		});
		
		List<Object> stocks = resposnseEntity.getBody();
		System.out.println(stocks);
		List<Stock> stockList = new ArrayList<Stock>();
		for(Object object: stocks) {
			System.out.println(object);
			Stock s = new Stock();
			Class c = object.getClass();
			try {
				Field user = c.getField("userName");
				Field quoteName = c.getField("quoteName");
				Field price = c.getField("price");
				Field date = c.getField("stockDate");
				
				s.setUserName((String) user.get(object));
				s.setQuoteName((String) quoteName.get(object));
				s.setPrice( (BigDecimal) price.get(object));
				s.setStockDate( (Date) date.get(object));
				
				
			} catch (NoSuchFieldException |IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			} catch (SecurityException e ) {
				e.printStackTrace();
			}
			System.out.println(object);
			stockList.add(s);
		}
		
		return stockList;
		//return stockRepository.findByUserName(userName);
		
	}
	
	private class Stock{
		
		private Integer id;
		private String userName;
		private String quoteName;
		private BigDecimal price;
		private Date stockDate;
		public Stock() {
			
		}
		public Stock(Integer id, String userName, String quoteName, BigDecimal price, Date stockDate) {
			this.id = id;
			this.userName = userName;
			this.quoteName = quoteName;
			this.price = price;
			this.stockDate = stockDate;
		}
		
		public Integer getId() {
			return id;
		}
		public void setId(Integer id) {
			this.id = id;
		}

		public String getUserName() {
			return userName;
		}

		public void setUserName(String userName) {
			this.userName = userName;
		}

		public String getQuoteName() {
			return quoteName;
		}

		public void setQuoteName(String quoteName) {
			this.quoteName = quoteName;
		}

		public BigDecimal getPrice() {
			return price;
		}

		public void setPrice(BigDecimal price) {
			this.price = price;
		}

		public Date getStockDate() {
			return stockDate;
		}

		public void setStockDate(Date stockDate) {
			this.stockDate = stockDate;
		}
	}
}
