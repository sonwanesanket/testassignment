package models.exchange_rate;

import java.util.Hashtable;
import java.util.Map;

import com.google.gson.JsonObject;

public class GetExchangeRateResponse {
	private String base;
	private Hashtable<String, Double> rates;
	private String date;

	public Hashtable<String, Double> getRates() {
		return rates;
	}

	public void setRates(Hashtable<String, Double> rates) {
		this.rates = rates;
	}

	public String getBase() {
		return base;
	}

	public void setBase(String base) {
		this.base = base;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

}
