package pl.edu.agh.mwo.invoice;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashMap;

import pl.edu.agh.mwo.invoice.product.Product;

public class Invoice {
	private Collection<Product> products;
	private HashMap<Product, Integer> amout = new HashMap<Product, Integer>();
	String number = "1234";

	public void addProduct(Product product) {
		this.addProduct(product, 1);
	}

	public void addProduct(Product product, Integer quantity) {
		if (quantity <= 0) {
			throw new IllegalArgumentException();
		}
		this.amout.put(product, quantity);
	}

	public BigDecimal getNetPrice() {
		BigDecimal subtotal = BigDecimal.ZERO;
		for (Product item : amout.keySet()) {
			BigDecimal subPrice = item.getPrice().multiply(new BigDecimal(amout.get(item)));
			subtotal = subtotal.add(subPrice);
		}
		return subtotal;
	}

	public BigDecimal getTax() {
		BigDecimal tax = BigDecimal.ZERO;
		for (Product item : amout.keySet()) {
			BigDecimal subTax = item.getTaxPercent().multiply(item.getPrice())
					.multiply(new BigDecimal(amout.get(item)));
			tax = tax.add(subTax);
		}
		return tax;
	}

	public BigDecimal getTotal() {
		return this.getNetPrice().add(this.getTax());
	}

	public String getNumber() {
		return this.number;
	}
}
