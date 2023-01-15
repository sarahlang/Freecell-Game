package cs3500.freecell.model;


/**
 * Card values that are in a deck of cards, from one (Ace) to ten, and Jack, Queen, King
 * each value has a unique index from 0 to 12 that represents 13 cards in the same suit.
 */
public enum CardValue {
  // Define each named value, passing an argument into the constructor

  King(12, "K"),
  Queen(11, "Q"),
  Jack(10, "J"),
  Ten(9, "10"),
  Nine(8, "9"),
  Eight(7, "8"),
  Seven(6, "7"),
  Six(5, "6"),
  Five(4, "5"),
  Four(3, "4"),
  Three(2, "3"),
  Two(1, "2"),
  Ace(0, "A");


  private final int index;
  private final String value;

  CardValue(int index, String value) {
    this.index = index;
    this.value = value;
  }

  // getter for the card index
  public int getCardIndex() {
    return this.index;
  }

  @Override
  public String toString() {
    return this.value;
  }

}

