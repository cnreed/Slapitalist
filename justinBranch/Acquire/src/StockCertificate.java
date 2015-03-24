import org.apache.log4j.Logger;

public class StockCertificate {

	public static Logger log = Logger.getLogger(StockCertificate.class);
	public Company companyOwner;
	public int quantity;
	public Player playerOwner;

	public StockCertificate(Company companyOwner) {
		this.companyOwner = companyOwner;
	}

	public StockCertificate(Company companyOwner, int quantity,
			Player playerOwner) {
		this.companyOwner = companyOwner;
		this.quantity = quantity;
		this.playerOwner = playerOwner;
		companyOwner.soldStock(quantity);
		System.out.println("New certificate for "
				+ companyOwner.getCompanyName() + " x" + quantity);
		log.debug("The owner of this stock is : " + playerOwner.getName());
		playerOwner.addCertificate(this);
	}

	/**
	 * @return the playerOwner
	 */
	public Player getPlayerOwner() {
		return playerOwner;
	}

	/**
	 * @return the quantity
	 */
	public int getQuantity() {
		return quantity;
	}

	/**
	 * @param quantity
	 *            the quantity to set
	 */
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

}
