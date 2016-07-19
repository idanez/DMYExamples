package my.snippets;
import java.text.DecimalFormat;


public class RegraDeTres {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		RegraDeTres regraDeTres = new RegraDeTres();
		
		Double result = regraDeTres.setX(100d).setEqualsX(100d).setEqualsY(30d).calculate();		
		System.out.println("Testando 100 = 100% e y = 30% : Resultado: y = "+result);
		
		result = regraDeTres.setX(50d).setEqualsX(100d).setY(40d).calculate();		
		System.out.println("Testando 50 = 100% e 40 = x% : Resultado x = "+result+"%");
		
			
		result = regraDeTres.setX(5153.10d).setEqualsX(100d).setY(5083.34d).calculate();	
		System.out.println("Testando "+regraDeTres.getX()+" = "+regraDeTres.getEqualsX()+"% e "+
		regraDeTres.getY()+" = "+regraDeTres.getEqualsY()+"% -> Rendimento = "+getFormatted(100 - result)+"%");
		
		/* 25/09/2013 = 6432.52 (5153.10)
		 * 
		*/
		 
		
		
	}

	private Double x = null;
	private Double equalsX  = null;
	private Double y = null;
	private Double equalsY = null;
	private static DecimalFormat df = new DecimalFormat("0.00");
	
	
	/**
	 * Exemplo de uso: <br>
	 * Double result = new RegraDeTres().setX(100d).setEqualsX(100d).setEqualsY(30d).calculate();
	 */
	public RegraDeTres() {	
	}
		
		/**
	 * @return the x
	 */
	public Double getX() {
		//String formatted = df.format(x);
		//return Double.parseDouble(formatted);
		return getFormatted(x);
	}

	private static Double getFormatted(Double number) {
		String formatted = df.format(number);
		return Double.parseDouble(formatted);
	}
	
	/**
	 * @param x the x to set
	 */
	public RegraDeTres setX(Double x) {
		this.x = x;
		return this;
	}

	/**
	 * @return the equalsX
	 */
	public Double getEqualsX() {
		return getFormatted(equalsX);
	}

	/**
	 * @param equalsX the equalsX to set
	 */
	public RegraDeTres setEqualsX(Double equalsX) {
		this.equalsX = equalsX;
		return this;
	}

	/**
	 * @return the y
	 */
	public Double getY() {
		return getFormatted(y);
	}

	/**
	 * @param y the y to set
	 */
	public RegraDeTres setY(Double y) {
		this.y = y;
		return this;
	}

	/**
	 * @return the equalsY
	 */
	public Double getEqualsY() {
		return getFormatted(equalsY);
	}

	/**
	 * @param equalsY the equalsY to set
	 */
	public RegraDeTres setEqualsY(Double equalsY) {
		this.equalsY = equalsY;
		return this;
	}

	/**
	 * x = equalsX
	 * y = equalsY
	 * @return
	 */
	public Double calculate() {
		
		Double result = null;
						
		if(x == null) {
			result = calculateX();			
		} else if( equalsX == null) {
			result = calculateEqualsX();
		} else if(y == null){
			result = calculateY();
		} else {
			result = calculateEqualsY();
		}
			
		return result;
	}

	/**
	 * Exemplo: <br><br> 
	 * 
	 *  100 = 100%<br>
	 *  50 = equalsY<br><br>
	 *  
	 *  return equalsY=50
	 *  
	 * @return
	 */
	private Double calculateEqualsY() {
		equalsY = internalCalculation(y, equalsX, x);
		return equalsY;
	}
	
	/**
	 * Exemplo: <br><br> 
	 * 
	 *  10 = 100%<br>
	 *  y = 50%<br><br>
	 *  
	 *  return y=5
	 *  
	 * @return
	 */
	private Double calculateY() {
		y = internalCalculation(x,equalsY,equalsX);
		return y;
	}

	/**
	 * Exemplo: <br><br> 
	 * 
	 *  100 = equalsX<br>
	 *  50 = 50%<br><br>
	 *  
	 *  return equalsY=100
	 *  
	 * @return
	 */
	private Double calculateEqualsX() {
		internalCalculation(x,equalsY,y);
		return equalsX;
	}

	/**
	 * Exemplo: <br><br> 
	 * 
	 *  x = 100%<br>
	 *  50= 50%<br><br>
	 *  
	 *  return x=100
	 *  
	 * @return
	 */
	private Double calculateX() {
		x = internalCalculation(y,equalsX,equalsY);
		return x;
	}
	
	private boolean validateVariables(Double var1, Double var2, Double var3) {
		return var1 != null && var2 != null && var3 != null;
	}
	
	private Double internalCalculation(Double multiplier1, Double multiplier2, Double divisor) {
		Double result = -1d;
		boolean valid = validateVariables(multiplier1, multiplier2, divisor);
		if(valid)
			result = (multiplier1 * multiplier2) / divisor;
		return result;
	}

}
