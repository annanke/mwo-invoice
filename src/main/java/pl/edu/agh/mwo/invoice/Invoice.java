package pl.edu.agh.mwo.invoice;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Random;

import pl.edu.agh.mwo.invoice.product.Product;

public class Invoice {
	//private Collection<Product> products;
	private HashMap<Product, Integer> amout = new HashMap<Product, Integer>();
	private final int number=iloscFaktur+1;
	//static int[] numbers=new int[] {0,0,0,0,0,0,0,0,0,0};
	static int iloscFaktur=0;
	
	public Invoice() {
		
		iloscFaktur++;
	}
	
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

	public int getNumber() {
		return this.number;
	}
}
