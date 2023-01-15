package cs3500.freecell.model;


/**
 * represents a single card with a card value, a suit symbol, and a unique index from 0 to 51 that
 * represents a total of 52 cards.
 */
public class Card {

  private final CardValue value;
  private final SuitSymbol suitSymbol;
  private final int index;

  /**
   * Constructor for Card.
   * @param value the face value of the card
   * @param suitSymbol the suitSymbol of the card
   * @param index the index of the card
   */
  public Card(CardValue value, SuitSymbol suitSymbol, int index) {
    this.value = value;
    this.suitSymbol = suitSymbol;
    this.index = index;
  }


  /**
   * Getter for getting the card value.
   * @return the card value of the card
   */
  public CardValue getCardValue() {
    return this.value;
  }

  /**
   * Getter for getting the suit symbol.
   * @return the suit symbol of the card
   */
  public SuitSymbol getSuitSymbol() {
    return this.suitSymbol;
  }

  @Override
  public String toString() {
    return this.getCardValue().toString() + this.getSuitSymbol().toString();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Card)) {
      return false;
    }
    Card card = (Card) o;
    return this.suitSymbol.equals(card.getSuitSymbol())
        && this.value.equals(card.getCardValue());
  }

  @Override
  public int hashCode() {
    return Long.hashCode(this.index);
  }
}
