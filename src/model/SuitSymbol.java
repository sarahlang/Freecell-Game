package cs3500.freecell.model;

/**
 * represents the suit symbols of a deck of cards, where club and spades have color black and
 * diamond and heart have color red.
 */
public enum SuitSymbol {

  Club("♣", "black"),
  Spade("♠", "black"),
  Diamond("♦", "red"),
  Heart( "♥", "red");

  private final String value;
  private final String color;

  SuitSymbol(String value, String color) {
    this.value = value;
    this.color = color;
  }

  @Override
  public String toString() {
    return this.value;
  }

  // gets the color field of the suit
  public String getColor() {
    return this.color;
  }

}
