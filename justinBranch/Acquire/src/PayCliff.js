/* Pay Cliff */

var tier, 				// level of price structure
	sharePrice,			// price per share of company
	sharePriceLength = 42,  // standard length of prices
	majorityPayout,		// majority payout for this company
	minorityPayout;		// minority payout for this company

	function PayCliff(tier){
		this.tier = tier;
		calculateSharePrice(tier);
	}

	function test(tier){
		var testPayCliff = new PayCliff(tier);
		for(var i = 2; i < sharePriceLength; i++){
			console.log(i+" "+ sharePrice[i]);
		}

		Console.log("Majority payout for level 20: "
			+ getMajorityPayout(20));
		Console.log("Minority payout for level 10: "
			+ getMinorityPayout(20));


	}

	function calculateSharePRice(tier){
		var tierValue = 0;
		tierValue = 100 * tier;

		for (var i = 0; i < sharePrice.length; i++) {
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
			if (i === 41)
				sharePrice[i] = 1000 + tierValue;
		}
	}

	function getMajorityPayout(companySize) {
		return sharePrice[companySize] * 10;
	}

	function getMinorityPayout(companySize) {
		return sharePrice[companySize] * 5;