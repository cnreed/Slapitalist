public class WeightTile {

	Tile tile;
	int surroundCount;
	int row;
	int col;
	float weight;

	public WeightTile(Tile tile, int row, int col) {
		this.tile = tile;
		this.surroundCount = 0;
		this.weight = 0.0f;
		this.row = row;
		this.col = col;
	}

	public double getWeight() {
		return weight;
	}

	public void addWeight(float addend) {
		weight += addend;
	}

	public void removeWeight() {
		weight = 0;
	}

	public void updateSurroundCount() {
		surroundCount++;
	}

	public int getSurroundCount() {
		return surroundCount;
	}

	public void removeSurroundCount() {
		surroundCount = 0;
	}

	public Tile getTile() {
		return tile;
	}

}
