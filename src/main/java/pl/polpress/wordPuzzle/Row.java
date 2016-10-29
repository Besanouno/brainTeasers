package pl.polpress.wordPuzzle;

public class Row {
	public Row(char[] row) {
		for (int i = 0; i < row.length; i++) {
			c[i] =  row[i] ;
		}
		c[20] = (row.length > 20) ? row[20] : ' ';
		c[21] = (row.length > 21) ? row[21] : ' ';
		c[22] = (row.length > 22) ? row[22] : ' ';
	}

	private Character[] c = new Character[23];

	public Character getC1() {
		return c[0];
	}

	public Character getC2() {
		return c[1];
	}

	public Character getC3() {
		return c[2];
	}

	public Character getC4() {
		return c[3];
	}

	public Character getC5() {
		return c[4];
	}

	public Character getC6() {
		return c[5];
	}

	public Character getC7() {
		return c[6];
	}

	public Character getC8() {
		return c[7];
	}

	public Character getC9() {
		return c[8];
	}

	public Character getC10() {
		return c[9];
	}

	public Character getC11() {
		return c[10];
	}

	public Character getC12() {
		return c[11];
	}

	public Character getC13() {
		return c[12];
	}

	public Character getC14() {
		return c[13];
	}

	public Character getC15() {
		return c[14];
	}

	public Character getC16() {
		return c[15];
	}

	public Character getC17() {
		return c[16];
	}

	public Character getC18() {
		return c[17];
	}

	public Character getC19() {
		return c[18];
	}

	public Character getC20() {
		return c[19];
	}

	public Character getC21() {
		return c[20];
	}

	public Character getC22() {
		return c[21];
	}

	public Character getC23() {
		return c[22];
	}
}
