package Abstraction;

import java.util.HashSet;
import java.util.Set;

public class Pawn {
	private Position pos;
	private Set<Position> possibleDestination;
	private Case playerNb;
	private Set<Position> finishLine;

	public Pawn(Position pos, Case player) {
		this.pos = pos;
		this.playerNb = player;
		this.possibleDestination = new HashSet<Position>();
		this.finishLine();
	}

	public Position getPos() {
		return pos;
	}

	public Case getPlayerNb() {
		return playerNb;
	}

	public Set<Position> getFinishLine() {
		return finishLine;
	}

	public void setPos(Position pos) {
		this.pos = pos;
	}

	public Set<Position> getPossibleDestination() {
		return this.possibleDestination;
	}

	public void topMove(Board board, Position pos, Boolean specialMove) {
		if (board.getBoard()[pos.getX() + 1][pos.getY()] == Case.POTENTIALWALL) {
			if (board.getBoard()[pos.getX() + 2][pos.getY()] == Case.EMPTY) {
				this.possibleDestination.add(new Position(pos.getX() + 2, pos.getY()));
			} else {
				if (!specialMove) {
					specialMove(board, new Position(pos.getX() + 2, pos.getY()), Movement.TOP);
				}
			}
		}
	}

	public void rightMove(Board board, Position pos, Boolean specialMove) {
		if (board.getBoard()[pos.getX()][pos.getY() + 1] == Case.POTENTIALWALL) {
			if (board.getBoard()[pos.getX()][pos.getY() + 2] == Case.EMPTY) {
				this.possibleDestination.add(new Position(pos.getX(), pos.getY() + 2));
			} else {
				if (!specialMove) {
					specialMove(board, new Position(pos.getX(), pos.getY() + 2), Movement.RIGHT);
				}
			}
		}
	}

	public void botMove(Board board, Position pos, Boolean specialMove) {
		if (board.getBoard()[pos.getX() - 1][pos.getY()] == Case.POTENTIALWALL) {
			if (board.getBoard()[pos.getX() - 2][pos.getY()] == Case.EMPTY) {
				this.possibleDestination.add(new Position(pos.getX() - 2, pos.getY()));
			} else {
				if (!specialMove) {
					specialMove(board, new Position(pos.getX(), pos.getY() - 2), Movement.BOT);
				}
			}
		}
	}

	public void leftMove(Board board, Position pos, Boolean specialMove) {
		if (board.getBoard()[pos.getX()][pos.getY() - 1] == Case.POTENTIALWALL) {
			if (board.getBoard()[pos.getX()][pos.getY() - 2] == Case.EMPTY) {
				this.possibleDestination.add(new Position(pos.getX(), pos.getY() - 2));
			} else {
				if (!specialMove) {
					specialMove(board, new Position(pos.getX(), pos.getY() - 2), Movement.LEFT);
				}
			}
		}
	}

	public void specialMove(Board board, Position pos, Movement m) {
		switch (m) {
		case TOP:
			if (board.getBoard()[pos.getX() + 1][pos.getY()] == Case.POTENTIALWALL) {
				topMove(board, pos, true);
			} else {
				leftMove(board, pos, true);
				rightMove(board, pos, true);
			}
			break;
		case RIGHT:
			if (board.getBoard()[pos.getX()][pos.getY() + 1] == Case.POTENTIALWALL) {
				rightMove(board, pos, true);
			} else {
				topMove(board, pos, true);
				botMove(board, pos, true);
			}
			break;
		case BOT:
			if (board.getBoard()[pos.getX() - 1][pos.getY()] == Case.POTENTIALWALL) {
				botMove(board, pos, true);
			} else {
				leftMove(board, pos, true);
				rightMove(board, pos, true);
			}
			break;
		case LEFT:
			if (board.getBoard()[pos.getX()][pos.getY() - 1] == Case.POTENTIALWALL) {
				leftMove(board, pos, true);
			} else {
				topMove(board, pos, true);
				botMove(board, pos, true);
			}
			break;
		default:
			break;
		}

	}

	public void possibleMove(Board board) {
		topMove(board, this.getPos(), false);
		rightMove(board, this.getPos(), false);
		botMove(board, this.getPos(), false);
		leftMove(board, this.getPos(), false);
	}

	public void finishLine() {
		this.finishLine = new HashSet<Position>();
		switch (this.playerNb) {
		case PLAYER1:
			for (int j = 1; j < Board.TAILLE; j += 2) {
				this.finishLine.add(new Position(1, j));
			}
			break;
		case PLAYER2:
			for (int j = 1; j < Board.TAILLE; j += 2) {
				this.finishLine.add(new Position(Board.TAILLE - 2, j));
			}
			break;
		case PLAYER3:
			for (int i = 1; i < Board.TAILLE; i += 2) {
				this.finishLine.add(new Position(i, Board.TAILLE - 2));
			}
			break;
		case PLAYER4:
			for (int i = 1; i < Board.TAILLE; i += 2) {
				this.finishLine.add(new Position(i, 1));
			}
			break;
		default:
			break;
		}
	}

	public Boolean isWinner() {
		if (this.finishLine.contains(this.pos)) {
			return true;
		}
		return false;
	}
}
