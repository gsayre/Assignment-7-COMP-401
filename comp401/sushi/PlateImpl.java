package comp401.sushi;

abstract public class PlateImpl implements Plate {

	private Sushi contents;
	private double price;
	private Plate.Color color;
	
	public PlateImpl(double price, Plate.Color color) {
		this.price = price;
		this.color = color;
		contents = null;
	}
	
	public PlateImpl(Sushi s, double price, Plate.Color color) throws PlatePriceException {
		this(price, color);
		setContents(s);
	}

	@Override
	public Sushi getContents() {
		return contents;
	}

	@Override
	public void setContents(Sushi s) throws PlatePriceException {
		if (s == null) {
			throw new IllegalArgumentException();
		}
		
		if (s.getCost() > getPrice()) {
			throw new PlatePriceException(this, s);
		}
		contents = s;
	}

	@Override
	public Sushi removeContents() {
		Sushi s = contents;
		contents = null;
		return s;
	}

	@Override
	public boolean hasContents() {
		return (contents != null);
	}

	@Override
	public double getPrice() {
		return price;
	}

	@Override
	public Color getColor() {
		return color;
	}

	@Override
	public double getProfit() {
		if (!hasContents()) {
			throw new UnsupportedOperationException("Profit undefined for empty plate");
		}
		return getPrice() - contents.getCost();
	}
}
