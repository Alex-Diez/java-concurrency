package org.sudoku.game.elements;

public class Position {

	public static final Position STUB = new Position(-1, -1);

	public final int row;
	public final int column;

	public Position(final int row, final int column) {
		this.row = row;
		this.column = column;
	}

	@Override
	public int hashCode() {
		int result = 31;
		result *= (17 + Integer.hashCode(row));
		result *= (17 + Integer.hashCode(column));
		return result;
	}

	@Override
	public boolean equals(Object object) {
		if(object == this) {
			return true;
		}
		if(object != null
				&& object.getClass().equals(getClass())) {
			Position position = (Position)object;
			return position.row == row
					&& position.column == column;
		}
		return false;
	}

	@Override
	public String toString() {
		return "Position{" +
				"row=" + row +
				", column=" + column +
				'}';
	}
}
