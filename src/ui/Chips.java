
/** 
 * @author Trey Briccetti
 * @version 1.0
 */

public class Chips {

	/**
	 * @param _amt The amount of money to be divided into an array
	 * @return Array representing how many chips of each type need to be drawn to display an amount of money
	 */
	public static int[] getChips(int _amt) {
		
		int[] chips = new int[6];
		chips[5] = _amt / 1000;
		_amt %= 1000;
		chips[4] = _amt / 250;
		_amt %= 250;
		chips[3] = _amt / 100;
		_amt %= 100;
		chips[2] = _amt / 25;
		_amt %= 25;
		chips[1] = _amt / 5;
		_amt %= 5;
		chips[0] = _amt / 1;
	
		return chips;
	
	}
	
	
}
