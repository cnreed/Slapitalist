class PayCliff {

	private int tier;
	private static int sharePrice[] = new int[42];
	private static int majorityPayout;
	private static int minorityPayout;

	public PayCliff(int tier) {
		this.tier = tier;
		calculateSharePrice(this.tier);

	}

	/*
	 * Demonstrates creation of price/size chart for a company. for example:
	 * Lowest Tier = 0, Highest Tier = 2 (stock game options) Also includes a
	 * payout calculation for Majority and Minority
	 */

	// public static void main(String[] args) {
	// PayCliff test = new PayCliff(2);
	// for (int i = 2; i < sharePrice.length; i++) {
	// System.out.println(i + " " + sharePrice[i]);
	// }
	// System.out.println("Majority payout for level 20: "
	// + getMajorityPayout(20));
	// System.out.println("Minority payout for level 10: "
	// + getMinorityPayout(20));
	// }

	private void calculateSharePrice(int tier) {
		int tierValue = 0;
		System.out.println("Tier: " + tier);
		tierValue = 100 * tier;

		for (int i = 0; i < sharePrice.length; i++) {
			if (i < 6)
				sharePrice[i] = (i * 100) + tierValue;
			if (i > 5 && i < 11)
				sharePrice[i] = (600) + tierValue;
			if (i > 10 && i < 21)
				sharePrice[i] = (700) + tierValue;
			if (i > 20 && i < 31)
				sharePrice[i] = (800) + tierValue;
			if (i > 30 && i < 41)
				sharePrice[i] = (900) + tierValue;
			if (i == 41)
				sharePrice[i] = 1000 + tierValue;
		}
	}

	private static int getMajorityPayout(int companySize) {
		return sharePrice[companySize] * 10;
	}

	private static int getMinorityPayout(int companySize) {
		return sharePrice[companySize] * 5;
	}

	public static int getSharePrice(int companySize) {
		return sharePrice[companySize];
	}
}
