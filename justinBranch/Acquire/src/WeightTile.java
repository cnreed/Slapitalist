public class WeightTile {

	Tile tile;
	int surroundCount;
	int row;
	int col;
	double weight;

	public WeightTile(Tile tile, int row, int col) {
		this.tile = tile;
		this.surroundCount = 0;
		this.weight = 0.0;
		this.row = row;
		this.col = col;
	}

	public void addWeight(double addend) {
		weight += addend;
	}

	public void removeWeight() {
		weight = 0;
	}

	public void updateSurroundCount() {
		surroundCount++;
	}

}
