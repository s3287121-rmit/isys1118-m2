package isys1118.group1.server.helpers;

public final class Cost {
	
	public final int dollars;
	public final int cents;
	
	public Cost(int dollars, int cents) {
		this.dollars = dollars;
		this.cents = cents;
	}
	
	public String getPriceStr() {
		return String.valueOf(dollars) + "." + String.valueOf(cents);
	}

}
