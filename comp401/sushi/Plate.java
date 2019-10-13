package comp401.sushi;


public interface Plate {
	 public enum Color {RED, GREEN, BLUE, GOLD}

     Sushi getContents();
     void setContents(Sushi s) throws PlatePriceException;
     Sushi removeContents();
     boolean hasContents();
     double getPrice();
     Plate.Color getColor();
     double getProfit();
}

