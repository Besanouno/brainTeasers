package pl.polpress.wordPuzzle;

public class Row {
	public Row(char[] row) {
		for (int i = 0; i < row.length; i++) {
			c[i] = row[i];
		}
		c[20] = (row.length > 20) ? row[20] : ' ';
		c[21] = (row.length > 21) ? row[21] : ' ';
		c[22] = (row.length > 22) ? row[22] : ' ';
	}

	private char[] c = new char[23];

	public char getC1() {
		return c[0];
	}

	public char getC2() {
		return c[1];
	}

	public char getC3() {
		return c[2];
	}

	public char getC4() {
		return c[3];
	}

	public char getC5() {
		return c[4];
	}

	public char getC6() {
		return c[5];
	}

	public char getC7() {
		return c[6];
	}

	public char getC8() {
		return c[7];
	}

	public char getC9() {
		return c[8];
	}

	public char getC10() {
		return c[9];
	}

	public char getC11() {
		return c[10];
	}

	public char getC12() {
		return c[11];
	}

	public char getC13() {
		return c[12];
	}

	public char getC14() {
		return c[13];
	}

	public char getC15() {
		return c[14];
	}

	public char getC16() {
		return c[15];
	}

	public char getC17() {
		return c[16];
	}

	public char getC18() {
		return c[17];
	}

	public char getC19() {
		return c[18];
	}

	public char getC20() {
		return c[19];
	}

	public char getC21() {
		return c[20];
	}

	public char getC22() {
		return c[21];
	}

	public char getC23() {
		return c[22];
	}
}
