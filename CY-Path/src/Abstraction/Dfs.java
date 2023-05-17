package Abstraction;

public class Dfs {
	private Boolean[][] matrix;
	private Integer width;
	private Pawn pawn;

	public Dfs(Board board) {
		this.width = (Board.TAILLE - 1) / 2;
		int nbVertex = (int) Math.pow((Board.TAILLE - 1) / 2, 2);
		this.matrix = new Boolean[nbVertex][nbVertex];
		int begin;
		int end;

		for (int i = 0; i < nbVertex; i++) {
			for (int j = 0; j < i + 1; j++) {
				this.matrix[i][j] = false;
				this.matrix[j][i] = false;
			}
		}

		for (int i = 2; i < Board.TAILLE - 1; i += 2) {
			for (int j = 1; j < Board.TAILLE - 1; j += 2) {
				if (board.getBoard()[i][j] == Case.POTENTIALWALL) {
					begin = (i / 2 - 1) * (Board.TAILLE - 1) / 2 + (j - 1) / 2;
					end = begin + (Board.TAILLE - 1) / 2;
					this.matrix[begin][end] = true;
					this.matrix[end][begin] = true;
				}
			}
		}

		for (int i = 1; i < Board.TAILLE - 1; i += 2) {
			for (int j = 2; j < Board.TAILLE - 1; j += 2) {
				if (board.getBoard()[i][j] == Case.POTENTIALWALL) {
					begin = j / 2 - 1 + (i - 1) / 2 * this.width;
					end = begin + 1;
					this.matrix[begin][end] = true;
					this.matrix[end][begin] = true;
				}
			}
		}
	}

	public Boolean[][] getMatrix() {
		return matrix;
	}

	public void setMatrix(Boolean[][] matrix) {
		this.matrix = matrix;
	}

	public Integer getWidth() {
		return width;
	}

	public void setWidth(Integer width) {
		this.width = width;
	}

	public Boolean dfs(Integer vertex, Integer line) {
		Integer length = this.getMatrix().length;
		Boolean[] marking = new Boolean[length];
		for (int i = 0; i < length; i++) {
			marking[i] = false;
		}
		marking = this.dfs_rec(vertex, marking);
		for (int i = 0; i < this.getWidth(); i++) {
			if (marking[line * this.getWidth() + i]) {
				return true;
			}
		}
		return false;
	}

	public Boolean[] dfs_rec(Integer vertex, Boolean[] marking) {
		if (!marking[vertex]) {
			marking[vertex] = true;
			for (int i = 0; i < this.getMatrix().length; i++) {
				if (this.getMatrix()[vertex][i]) {
					this.dfs_rec(i, marking);
				}
			}
		}
		return marking;
	}
}
