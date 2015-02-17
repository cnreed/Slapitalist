class playerNode implements Comparable<playerNode> {
	String playerName;
	int shareCount;

	public playerNode(String playerName, int shareCount) {
		this.playerName = playerName;
		this.shareCount = shareCount;
	}

	@Override
	public int compareTo(playerNode other) {
		// compareTo should return < 0 if this is supposed to be
		// less than other, > 0 if this is supposed to be greater than
		// other and 0 if they are supposed to be equal
		Integer iShareCount = new Integer(other.shareCount);
		int last = iShareCount.compareTo(this.shareCount);
		return last == 0 ? this.playerName.compareTo(other.playerName) : last;
	}
}