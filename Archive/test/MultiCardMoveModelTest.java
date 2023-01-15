import static org.junit.Assert.assertEquals;

import cs3500.freecell.model.Card;
import cs3500.freecell.model.CardValue;
import cs3500.freecell.model.hw04.MultiCardMoveModel;
import cs3500.freecell.model.PileType;
import cs3500.freecell.model.SuitSymbol;
import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for the MultiCardMoveModel class. This class tests mainly moving multiple cards from one
 * cascade pile to the other when conditions are or aren't met. Other aspects are tested in the
 * AbstractFreecellModelTest class.
 */
public class MultiCardMoveModelTest extends AbstractFreecellModelTest {

  @Before
  public void init() {
    model1 = new MultiCardMoveModel();
    deck1 = new ArrayList<>();
    int counter = 0;
    for (CardValue c : CardValue.values()) {
      for (SuitSymbol s : SuitSymbol.values()) {
        deck1.add(new Card(c, s, counter));
        counter++;
      }
    }
  }

  // when there is one empty open pile, zero empty cascade pile, only two cards can be moved.
  @Test
  public void moveTwoCards() {
    this.model1.startGame(this.model1.getDeck(), 4, 3, false);
    this.model1.move(PileType.CASCADE, 0, 12, PileType.OPEN, 0);
    // the following test moves one card from cascade to cascade
    this.model1.move(PileType.CASCADE, 2, 12, PileType.CASCADE, 0);
    this.model1.move(PileType.CASCADE, 2, 11, PileType.OPEN, 1);
    this.model1.move(PileType.CASCADE, 0, 11, PileType.CASCADE, 2);

    assertEquals(new Card(CardValue.Ace, SuitSymbol.Diamond, 0),
        this.model1.getCascadeCardAt(2, 12));
    assertEquals(new Card(CardValue.Two, SuitSymbol.Club, 0), this.model1.getCascadeCardAt(2, 11));
  }

  // fail to move 3 cards from one cascade pile to another with 1 open pile & 0 empty cascade pile.
  @Test(expected = IllegalArgumentException.class)
  public void moveThreeCardsWithOneOpenAndZeroCascade() {
    this.model1.startGame(this.model1.getDeck(), 4, 4, false);
    this.model1.move(PileType.CASCADE, 0, 12, PileType.OPEN, 0);
    this.model1.move(PileType.CASCADE, 2, 12, PileType.CASCADE, 0);
    this.model1.move(PileType.CASCADE, 2, 11, PileType.OPEN, 1);
    this.model1.move(PileType.CASCADE, 0, 11, PileType.CASCADE, 2);
    this.model1.move(PileType.CASCADE, 0, 10, PileType.OPEN, 2);
    this.model1.move(PileType.CASCADE, 2, 10, PileType.CASCADE, 0);
  }

  // move a single card to an empty cascade pile.
  @Test
  public void moveOneValidCardsToMtCascade() {
    this.model1.startGame(this.model1.getDeck(), 4, 4, false);
    this.model1.move(PileType.CASCADE, 0, 12, PileType.FOUNDATION, 0);
    this.model1.move(PileType.CASCADE, 0, 11, PileType.FOUNDATION, 0);
    this.model1.move(PileType.CASCADE, 0, 10, PileType.FOUNDATION, 0);
    this.model1.move(PileType.CASCADE, 0, 9, PileType.FOUNDATION, 0);
    this.model1.move(PileType.CASCADE, 0, 8, PileType.FOUNDATION, 0);
    this.model1.move(PileType.CASCADE, 0, 7, PileType.FOUNDATION, 0);
    this.model1.move(PileType.CASCADE, 0, 6, PileType.FOUNDATION, 0);
    this.model1.move(PileType.CASCADE, 0, 5, PileType.FOUNDATION, 0);
    this.model1.move(PileType.CASCADE, 0, 4, PileType.FOUNDATION, 0);
    this.model1.move(PileType.CASCADE, 0, 3, PileType.FOUNDATION, 0);
    this.model1.move(PileType.CASCADE, 0, 2, PileType.FOUNDATION, 0);
    this.model1.move(PileType.CASCADE, 0, 1, PileType.FOUNDATION, 0);
    this.model1.move(PileType.CASCADE, 0, 0, PileType.FOUNDATION, 0);
    this.model1.move(PileType.CASCADE, 1, 12, PileType.CASCADE, 0);
    assertEquals(new Card(CardValue.Ace, SuitSymbol.Spade, 12),
        this.model1.getCascadeCardAt(0, 0));
  }

  @Test
  public void moveThreeValidCardsToEmptyCascade() {
    this.model1.startGame(this.model1.getDeck(), 4, 4, false);
    // form a valid build in a cascade pile
    this.model1.move(PileType.CASCADE, 0, 12, PileType.OPEN, 0);
    this.model1.move(PileType.CASCADE, 2, 12, PileType.CASCADE, 0);
    this.model1.move(PileType.CASCADE, 2, 11, PileType.OPEN, 1);
    this.model1.move(PileType.CASCADE, 0, 11, PileType.CASCADE, 2);

    // make one cascade pile empty
    this.model1.move(PileType.CASCADE, 1, 12, PileType.FOUNDATION, 1);
    this.model1.move(PileType.CASCADE, 1, 11, PileType.FOUNDATION, 1);
    this.model1.move(PileType.CASCADE, 1, 10, PileType.FOUNDATION, 1);
    this.model1.move(PileType.CASCADE, 1, 9, PileType.FOUNDATION, 1);
    this.model1.move(PileType.CASCADE, 1, 8, PileType.FOUNDATION, 1);
    this.model1.move(PileType.CASCADE, 1, 7, PileType.FOUNDATION, 1);
    this.model1.move(PileType.CASCADE, 1, 6, PileType.FOUNDATION, 1);
    this.model1.move(PileType.CASCADE, 1, 5, PileType.FOUNDATION, 1);
    this.model1.move(PileType.CASCADE, 1, 4, PileType.FOUNDATION, 1);
    this.model1.move(PileType.CASCADE, 1, 3, PileType.FOUNDATION, 1);
    this.model1.move(PileType.CASCADE, 1, 2, PileType.FOUNDATION, 1);
    this.model1.move(PileType.CASCADE, 1, 1, PileType.FOUNDATION, 1);
    this.model1.move(PileType.CASCADE, 1, 0, PileType.FOUNDATION, 1);

    // move the valid build to the empty cascade pile
    this.model1.move(PileType.CASCADE, 2, 10, PileType.CASCADE, 1);

    // check that the originally empty cascade pile now has a valid build of cards
    assertEquals(new Card(CardValue.Three, SuitSymbol.Diamond, 0),
        this.model1.getCascadeCardAt(1, 0));
    assertEquals(new Card(CardValue.Two, SuitSymbol.Club, 0), this.model1.getCascadeCardAt(1, 1));
    assertEquals(new Card(CardValue.Ace, SuitSymbol.Diamond, 0),
        this.model1.getCascadeCardAt(1, 2));
  }

  // invalid suit symbol, valid card value.
  @Test(expected = IllegalArgumentException.class)
  public void moveCardsWithInvalidSuitSymbolBuildToEmptyCascade() {
    this.model1.startGame(this.model1.getDeck(), 4, 6, false);

    // make c1 empty
    this.model1.move(PileType.CASCADE, 1, 12, PileType.FOUNDATION, 1);
    this.model1.move(PileType.CASCADE, 1, 11, PileType.FOUNDATION, 1);
    this.model1.move(PileType.CASCADE, 1, 10, PileType.FOUNDATION, 1);
    this.model1.move(PileType.CASCADE, 1, 9, PileType.FOUNDATION, 1);
    this.model1.move(PileType.CASCADE, 1, 8, PileType.FOUNDATION, 1);
    this.model1.move(PileType.CASCADE, 1, 7, PileType.FOUNDATION, 1);
    this.model1.move(PileType.CASCADE, 1, 6, PileType.FOUNDATION, 1);
    this.model1.move(PileType.CASCADE, 1, 5, PileType.FOUNDATION, 1);
    this.model1.move(PileType.CASCADE, 1, 4, PileType.FOUNDATION, 1);
    this.model1.move(PileType.CASCADE, 1, 3, PileType.FOUNDATION, 1);
    this.model1.move(PileType.CASCADE, 1, 2, PileType.FOUNDATION, 1);
    this.model1.move(PileType.CASCADE, 1, 1, PileType.FOUNDATION, 1);
    this.model1.move(PileType.CASCADE, 1, 0, PileType.FOUNDATION, 1);

    // move black cards in descending order to empty cascade pile
    this.model1.move(PileType.CASCADE, 0, 4, PileType.CASCADE, 0);
  }

  // valid suit symbol, invalid card value.
  @Test(expected = IllegalArgumentException.class)
  public void moveCardsWithInvalidCardValueBuildToEmptyCascade() {
    this.model1.startGame(this.model1.getDeck(), 17, 6, false);
    // make c0 empty
    this.model1.move(PileType.CASCADE, 0, 3, PileType.OPEN, 0);
    this.model1.move(PileType.CASCADE, 0, 2, PileType.OPEN, 1);
    this.model1.move(PileType.CASCADE, 0, 1, PileType.OPEN, 2);
    this.model1.move(PileType.CASCADE, 0, 0, PileType.OPEN, 3);

    // move red A to black 2
    this.model1.move(PileType.CASCADE, 16, 2, PileType.CASCADE, 10);
    // move red 7, black 2, and red A to empty cascade
    this.model1.move(PileType.CASCADE, 10, 1, PileType.CASCADE, 0);

  }

  // invalid suit symbol, valid card value.
  @Test(expected = IllegalArgumentException.class)
  public void moveCardsWithInvalidSuitSymbolBuildToNonEmptyCascade() {
    this.model1.startGame(this.model1.getDeck(), 20, 6, false);
    this.model1.move(PileType.CASCADE, 8, 2, PileType.CASCADE, 6);
    this.model1.move(PileType.CASCADE, 6, 2, PileType.CASCADE, 3);
  }

  // valid suit symbol, invalid card value.
  @Test(expected = IllegalArgumentException.class)
  public void moveCardsWithInvalidCardValueBuildToNonEmptyCascade() {
    this.model1.startGame(this.model1.getDeck(), 13, 6, false);
    this.model1.move(PileType.CASCADE, 9, 2, PileType.CASCADE, 11);
  }


  // can only move max of 3 cards with 2 empty open and 0 empty cascade.
  @Test(expected = IllegalArgumentException.class)
  public void moveFourCards() {
    this.model1.startGame(this.model1.getDeck(), 4, 5, false);
    // form a valid build in a cascade pile
    this.model1.move(PileType.CASCADE, 0, 12, PileType.FOUNDATION, 0);
    this.model1.move(PileType.CASCADE, 2, 12, PileType.CASCADE, 0);
    this.model1.move(PileType.CASCADE, 2, 11, PileType.OPEN, 1);
    this.model1.move(PileType.CASCADE, 0, 11, PileType.CASCADE, 2);
    this.model1.move(PileType.CASCADE, 0, 10, PileType.OPEN, 0);
    this.model1.move(PileType.CASCADE, 2, 10, PileType.CASCADE, 0);
    this.model1.move(PileType.CASCADE, 2, 9, PileType.OPEN, 2);
    this.model1.move(PileType.CASCADE, 0, 9, PileType.CASCADE, 2);
  }

  //can only move max of 6 cards with 2 empty open and 1 empty cascade.
  @Test(expected = IllegalArgumentException.class)
  public void moveSevenCards() {
    this.model1.startGame(this.model1.getDeck(), 4, 8, false);
    // form a valid build of 4 cards in a cascade pile
    this.model1.move(PileType.CASCADE, 0, 12, PileType.FOUNDATION, 0);
    this.model1.move(PileType.CASCADE, 2, 12, PileType.CASCADE, 0);
    this.model1.move(PileType.CASCADE, 2, 11, PileType.OPEN, 1);
    this.model1.move(PileType.CASCADE, 0, 11, PileType.CASCADE, 2);
    this.model1.move(PileType.CASCADE, 0, 10, PileType.OPEN, 0);
    this.model1.move(PileType.CASCADE, 2, 10, PileType.CASCADE, 0);
    this.model1.move(PileType.CASCADE, 2, 9, PileType.OPEN, 2);

    // make one cascade pile empty
    this.model1.move(PileType.CASCADE, 1, 12, PileType.FOUNDATION, 1);
    this.model1.move(PileType.CASCADE, 1, 11, PileType.FOUNDATION, 1);
    this.model1.move(PileType.CASCADE, 1, 10, PileType.FOUNDATION, 1);
    this.model1.move(PileType.CASCADE, 1, 9, PileType.FOUNDATION, 1);
    this.model1.move(PileType.CASCADE, 1, 8, PileType.FOUNDATION, 1);
    this.model1.move(PileType.CASCADE, 1, 7, PileType.FOUNDATION, 1);
    this.model1.move(PileType.CASCADE, 1, 6, PileType.FOUNDATION, 1);
    this.model1.move(PileType.CASCADE, 1, 5, PileType.FOUNDATION, 1);
    this.model1.move(PileType.CASCADE, 1, 4, PileType.FOUNDATION, 1);
    this.model1.move(PileType.CASCADE, 1, 3, PileType.FOUNDATION, 1);
    this.model1.move(PileType.CASCADE, 1, 2, PileType.FOUNDATION, 1);
    this.model1.move(PileType.CASCADE, 1, 1, PileType.FOUNDATION, 1);
    this.model1.move(PileType.CASCADE, 1, 0, PileType.FOUNDATION, 1);

    this.model1.move(PileType.CASCADE, 0, 9, PileType.CASCADE, 2);
    this.model1.move(PileType.CASCADE, 0, 8, PileType.OPEN, 3);
    this.model1.move(PileType.CASCADE, 2, 8, PileType.CASCADE, 0);
    this.model1.move(PileType.CASCADE, 2, 7, PileType.OPEN, 4);
    this.model1.move(PileType.CASCADE, 0, 7, PileType.CASCADE, 2);
    this.model1.move(PileType.CASCADE, 0, 6, PileType.OPEN, 5);
    this.model1.move(PileType.CASCADE, 2, 6, PileType.CASCADE, 0);
  }

  @Test
  public void moveNineCardsFromCascadeToEmptyCascade() {
    this.model1.startGame(this.model1.getDeck(), 52, 1, false);
    this.model1.move(PileType.CASCADE, 51, 0, PileType.CASCADE, 44);
    this.model1.move(PileType.CASCADE, 44, 0, PileType.CASCADE, 43);
    this.model1.move(PileType.CASCADE, 43, 0, PileType.CASCADE, 37);
    this.model1.move(PileType.CASCADE, 37, 0, PileType.CASCADE, 35);
    this.model1.move(PileType.CASCADE, 35, 0, PileType.CASCADE, 29);
    this.model1.move(PileType.CASCADE, 29, 0, PileType.CASCADE, 26);
    this.model1.move(PileType.CASCADE, 26, 0, PileType.CASCADE, 20);
    this.model1.move(PileType.CASCADE, 20, 0, PileType.CASCADE, 19);
    this.model1.move(PileType.CASCADE, 19, 0, PileType.CASCADE, 20);

    assertEquals(new Card(CardValue.Nine, SuitSymbol.Heart, 10),
        this.model1.getCascadeCardAt(20, 0));
  }

}
