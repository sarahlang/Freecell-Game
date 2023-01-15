import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;

import cs3500.freecell.model.Card;
import cs3500.freecell.model.CardValue;
import cs3500.freecell.model.PileType;
import cs3500.freecell.model.SuitSymbol;
import cs3500.freecell.model.hw02.SimpleFreecellModel;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;


/**
 * Tests for the methods in SimpleFreecellModel class.
 */
public abstract class AbstractFreecellModelTest {

  SimpleFreecellModel model1;
  List<Card> deck1;

  @Before
  public void init() {
    deck1 = new ArrayList<>();
    int counter = 0;
    for (CardValue c : CardValue.values()) {
      for (SuitSymbol s : SuitSymbol.values()) {
        deck1.add(new Card(c, s, counter));
        counter++;
      }
    }
  }

  /**
   * Tests the getDeck method, see if every card in my deck is extensionally equal to every card
   * made from the getDeck method.
   */
  @Test
  public void testGetDeck() {
    for (int i = 0; i < 52; i++) {
      assertEquals(this.model1.getDeck().get(i), this.deck1.get(i));
    }
  }

  /**
   * Tests startGame method with the invalid number of numCascadePiles.
   */
  @Test(expected = IllegalArgumentException.class)
  public void invalidNumCascade() {
    this.model1.startGame(deck1, -1, 4, true);
  }

  /**
   * Tests startGame method with the invalid number of numOpenPiles.
   */
  @Test(expected = IllegalArgumentException.class)
  public void invalidNumOpen() {
    this.model1.startGame(deck1, 5, 0, true);
    assertNotEquals(this.model1.getDeck().get(1), this.deck1.get(1));

  }

  /**
   * Tests startGame method with an invalid deck that has less than 52 cards.
   */
  @Test(expected = IllegalArgumentException.class)
  public void TooFewNumDeck() {
    deck1.remove(0);
    this.model1.startGame(deck1, 4, 4, true);
  }

  /**
   * Tests startGame method with an invalid deck that has more than 52 cards.
   */
  @Test(expected = IllegalArgumentException.class)
  public void TooManyNumDeck() {
    deck1.add(deck1.get(0));
    this.model1.startGame(deck1, 4, 4, true);
  }

  /**
   * Tests startGame method with an invalid deck where a deck is null.
   */
  @Test(expected = IllegalArgumentException.class)
  public void nullDeck() {
    this.model1.startGame(null, 4, 4, true);
  }

  /**
   * Tests startGame method with an invalid deck that has duplicate cards.
   */
  @Test(expected = IllegalArgumentException.class)
  public void duplicateCardDeck() {
    deck1.add(deck1.get(1));
    deck1.remove(2);
    this.model1.startGame(deck1, 4, 4, true);
  }

  /**
   * Tests startGame method to see if deck is shuffled when shuffled is false.
   */
  @Test
  public void testNotShuffled() {
    this.model1.startGame(deck1, 5, 4, false);
    for (int i = 0; i < 52; i++) {
      assertEquals(this.model1.getDeck().get(i), this.deck1.get(i));
    }
  }

  /**
   * Tests startGame method to see if deck is shuffled when shuffled is true, Calling "startGame"
   * method twice.
   */
  @Test
  public void testShuffle() {
    // make a model with a seeded random that creates the same shuffled deck each time
    this.model1.startGame(this.model1.getDeck(), 4, 2, true);
    Card shuffled1 = this.model1.getCascadeCardAt(1, 3);
    Card shuffled2 = this.model1.getCascadeCardAt(2, 10);
    Card shuffled3 = this.model1.getCascadeCardAt(3, 6);
    this.model1.startGame(this.model1.getDeck(), 4, 2, false);
    Card unshuffled1 = this.model1.getCascadeCardAt(1, 3);
    Card unshuffled2 = this.model1.getCascadeCardAt(2, 10);
    Card unshuffled3 = this.model1.getCascadeCardAt(3, 6);

    this.getNumCardsInCascadePile();
    // makes sure that the shuffled model is different from the unshuffled deck
    assertNotEquals(shuffled1, unshuffled1);
    assertNotEquals(shuffled2, unshuffled2);
    assertNotEquals(shuffled3, unshuffled3);
  }

  /**
   * Tests startGame method to see if the right number of open fields are initialized.
   */
  @Test
  public void numOpenPiles() {
    this.model1.startGame(deck1, 5, 4, false);
    assertEquals(4, this.model1.getNumOpenPiles());
  }

  /**
   * Tests startGame method to see if all open fields are empty.
   */
  @Test
  public void numCardsInFirstOpenPile() {
    this.model1.startGame(deck1, 5, 2, false);
    assertEquals(0, this.model1.getNumCardsInOpenPile(0));
  }

  /**
   * Tests startGame method to see if all open fields are empty.
   */
  @Test
  public void numCardsSecondInOpenPile() {
    this.model1.startGame(deck1, 5, 2, false);
    assertEquals(0, this.model1.getNumCardsInOpenPile(1));
  }

  /**
   * Tests startGame method to see if foundation field is initialized.
   */
  @Test
  public void numCardsInFoundationPile() {
    this.model1.startGame(deck1, 5, 1, false);
    assertEquals(0, this.model1.getNumCardsInOpenPile(0));
  }

  /**
   * Tests startGame method to see if the right number of foundation fields are initialized.
   */
  @Test
  public void numFoundationPiles() {
    this.model1.startGame(deck1, 5, 4, false);
    assertEquals(0, this.model1.getNumCardsInFoundationPile(1));
  }


  /**
   * Tests startGame method to see if cascade field is initialized.
   */
  @Test
  public void numCascadePiles() {
    this.model1.startGame(deck1, 5, 4, false);
    assertEquals(5, this.model1.getNumCascadePiles());
  }

  /**
   * Tests startGame method to see if cascade piles have the correct number of cards.
   */
  @Test
  public void numCardsInFirstCascadePile() {
    this.model1.startGame(deck1, 5, 4, false);
    assertEquals(11, this.model1.getNumCardsInCascadePile(0));
  }

  /**
   * Tests startGame method to see if cascade piles have the correct number of cards.
   */
  @Test
  public void numCardsInSecondCascadePile() {
    this.model1.startGame(deck1, 5, 4, false);
    assertEquals(11, this.model1.getNumCardsInCascadePile(1));
  }

  /**
   * Tests startGame method to see if cascade piles have the correct number of cards.
   */
  @Test
  public void numCardsInThirdCascadePile() {
    this.model1.startGame(deck1, 5, 4, false);
    assertEquals(10, this.model1.getNumCardsInCascadePile(2));
  }

  /**
   * Tests startGame method to see if cascade piles have the correct number of cards.
   */
  @Test
  public void numCardsInFourthCascadePile() {
    this.model1.startGame(deck1, 5, 4, false);
    assertEquals(10, this.model1.getNumCardsInCascadePile(3));
  }

  /**
   * Tests startGame method to see if cascade piles have the correct number of cards.
   */
  @Test
  public void numCardsInFifthCascadePile() {
    this.model1.startGame(deck1, 5, 4, false);
    assertEquals(10, this.model1.getNumCardsInCascadePile(4));
  }

  /**
   * Tests the isGameOver method when the game has not started.
   */
  @Test(expected = IllegalStateException.class)
  public void gameNotStartedGameOver() {
    this.model1.isGameOver();
  }

  /**
   * Tests the isGameOver method when game is over.
   */
  @Test
  public void gameOver() {
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

    this.model1.move(PileType.CASCADE, 2, 12, PileType.FOUNDATION, 2);
    this.model1.move(PileType.CASCADE, 2, 11, PileType.FOUNDATION, 2);
    this.model1.move(PileType.CASCADE, 2, 10, PileType.FOUNDATION, 2);
    this.model1.move(PileType.CASCADE, 2, 9, PileType.FOUNDATION, 2);
    this.model1.move(PileType.CASCADE, 2, 8, PileType.FOUNDATION, 2);
    this.model1.move(PileType.CASCADE, 2, 7, PileType.FOUNDATION, 2);
    this.model1.move(PileType.CASCADE, 2, 6, PileType.FOUNDATION, 2);
    this.model1.move(PileType.CASCADE, 2, 5, PileType.FOUNDATION, 2);
    this.model1.move(PileType.CASCADE, 2, 4, PileType.FOUNDATION, 2);
    this.model1.move(PileType.CASCADE, 2, 3, PileType.FOUNDATION, 2);
    this.model1.move(PileType.CASCADE, 2, 2, PileType.FOUNDATION, 2);
    this.model1.move(PileType.CASCADE, 2, 1, PileType.FOUNDATION, 2);
    this.model1.move(PileType.CASCADE, 2, 0, PileType.FOUNDATION, 2);

    this.model1.move(PileType.CASCADE, 3, 12, PileType.FOUNDATION, 3);
    this.model1.move(PileType.CASCADE, 3, 11, PileType.FOUNDATION, 3);
    this.model1.move(PileType.CASCADE, 3, 10, PileType.FOUNDATION, 3);
    this.model1.move(PileType.CASCADE, 3, 9, PileType.FOUNDATION, 3);
    this.model1.move(PileType.CASCADE, 3, 8, PileType.FOUNDATION, 3);
    this.model1.move(PileType.CASCADE, 3, 7, PileType.FOUNDATION, 3);
    this.model1.move(PileType.CASCADE, 3, 6, PileType.FOUNDATION, 3);
    this.model1.move(PileType.CASCADE, 3, 5, PileType.FOUNDATION, 3);
    this.model1.move(PileType.CASCADE, 3, 4, PileType.FOUNDATION, 3);
    this.model1.move(PileType.CASCADE, 3, 3, PileType.FOUNDATION, 3);
    this.model1.move(PileType.CASCADE, 3, 2, PileType.FOUNDATION, 3);
    this.model1.move(PileType.CASCADE, 3, 1, PileType.FOUNDATION, 3);
    this.model1.move(PileType.CASCADE, 3, 0, PileType.FOUNDATION, 3);

    assertEquals(true, this.model1.isGameOver());
  }

  /**
   * Tests the isGameOver method when game is not over.
   */
  @Test
  public void gameNotOver() {
    this.model1.startGame(deck1, 4, 4, true);
    assertEquals(false, this.model1.isGameOver());
  }


  /**
   * Tests the getNumCardsInFoundationPile method with invalid index.
   */
  @Test(expected = IllegalArgumentException.class)
  public void smallIndex() {
    this.model1.startGame(deck1, 4, 4, true);
    this.model1.getNumCardsInFoundationPile(-1);
  }

  /**
   * Tests the getNumCardsInFoundationPile method with invalid index.
   */
  @Test(expected = IllegalArgumentException.class)
  public void bigIndex() {
    this.model1.startGame(deck1, 4, 4, true);
    this.model1.getNumCardsInFoundationPile(4);
  }

  /**
   * Tests the getNumCardsInFoundationPile method when game has not started.
   */
  @Test(expected = IllegalStateException.class)
  public void foundationGameNotStarted() {
    this.model1.getNumCardsInFoundationPile(2);
  }

  /**
   * Tests the getNumCardsInFoundationPile method.
   */
  @Test
  public void NumCardsInFoundationPile() {
    this.model1.startGame(deck1, 4, 4, false);
    assertEquals(0, this.model1.getNumCardsInFoundationPile(1));
  }

  /**
   * Tests the getNumCascadePiles method where game has started.
   */
  @Test
  public void gameStartedNumCascadePiles() {
    this.model1.startGame(deck1, 4, 4, false);
    assertEquals(4, this.model1.getNumCascadePiles());
  }

  /**
   * Tests the getNumCascadePiles method where game has not started.
   */
  @Test
  public void gameNotStartedNumCascadePiles() {
    assertEquals(-1, this.model1.getNumCascadePiles());
  }


  /**
   * Tests the getNumCardsInCascadePile method.
   */
  @Test
  public void getNumCardsInCascadePile() {
    this.model1.startGame(deck1, 4, 4, false);
    assertEquals(13, this.model1.getNumCardsInCascadePile(0));
  }

  /**
   * Tests the getNumCardsInCascadePile method when game has not started.
   */
  @Test(expected = IllegalStateException.class)
  public void cascadeGameNotStarted() {
    this.model1.getNumCardsInCascadePile(2);
  }

  /**
   * Tests the getNumCardsInCascadePile method with invalid index.
   */
  @Test(expected = IllegalArgumentException.class)
  public void cascadeSmallIndex() {
    this.model1.startGame(deck1, 4, 4, true);
    this.model1.getNumCardsInCascadePile(4);
  }

  /**
   * Tests the getNumCardsInCascadePile method with invalid index.
   */
  @Test(expected = IllegalArgumentException.class)
  public void cascadeBigIndex() {
    this.model1.startGame(deck1, 4, 4, true);
    this.model1.getNumCardsInCascadePile(-1);
  }

  /**
   * Tests the getNumCardsInOpenPile method.
   */
  @Test
  public void getNumCardsOpenPile() {
    this.model1.startGame(deck1, 4, 4, false);
    this.model1.move(PileType.CASCADE, 2, 12, PileType.OPEN, 0);
    assertEquals(1, this.model1.getNumCardsInOpenPile(0));
  }

  /**
   * Tests the getNumCardsInOpenPile method when game has not started.
   */
  @Test(expected = IllegalStateException.class)
  public void openGameNotStarted() {
    this.model1.getNumCardsInOpenPile(2);
  }

  /**
   * Tests the getNumCardsInOpenPile method with invalid index.
   */
  @Test(expected = IllegalArgumentException.class)
  public void openSmallIndex() {
    this.model1.startGame(deck1, 4, 4, true);
    this.model1.getNumCardsInOpenPile(4);
  }

  /**
   * Tests the getNumCardsInOpenPile method with invalid index.
   */
  @Test(expected = IllegalArgumentException.class)
  public void openBigIndex() {
    this.model1.startGame(deck1, 4, 4, true);
    this.model1.getNumCardsInOpenPile(-1);
  }

  /**
   * Tests the getNumOpenPiles method where game has started.
   */
  @Test
  public void gameStartedNumOpenPiles() {
    this.model1.startGame(deck1, 4, 4, false);
    assertEquals(4, this.model1.getNumOpenPiles());
  }

  /**
   * Tests the getNumOpenPiles method where game has not started.
   */
  @Test
  public void gameNotStartedNumOpenPiles() {
    assertEquals(-1, this.model1.getNumOpenPiles());
  }


  /**
   * Tests the getFoundationCardAt method when game has not started.
   */
  @Test(expected = IllegalStateException.class)
  public void getCardOpenGameNotStarted() {
    this.model1.getFoundationCardAt(2, 2);
  }

  /**
   * Tests the getFoundationCardAt method with invalid index.
   */
  @Test(expected = IllegalArgumentException.class)
  public void openSmallPileIndex() {
    this.model1.startGame(deck1, 4, 4, true);
    this.model1.getFoundationCardAt(5, 2);
  }

  /**
   * Tests the getFoundationCardAt method with invalid index.
   */
  @Test(expected = IllegalArgumentException.class)
  public void openBigPileIndex() {
    this.model1.startGame(deck1, 4, 4, true);
    this.model1.getFoundationCardAt(-1, 2);
  }

  /**
   * Tests the getFoundationCardAt method with invalid index.
   */
  @Test(expected = IllegalArgumentException.class)
  public void openSmallCardIndex() {
    this.model1.startGame(deck1, 4, 4, true);
    this.model1.getFoundationCardAt(2, 7);
  }

  /**
   * Tests the getFoundationCardAt method with invalid index.
   */
  @Test(expected = IllegalArgumentException.class)
  public void openBigCardIndex() {
    this.model1.startGame(deck1, 4, 4, true);
    this.model1.getFoundationCardAt(2, -1);
  }

  /**
   * Tests the getFoundationCardAt method.
   */
  @Test
  public void getFoundationCardAt() {
    this.model1.startGame(this.model1.getDeck(), 4, 4, false);
    this.model1.move(PileType.CASCADE, 0, 12, PileType.OPEN, 0);
    assertEquals(new Card(CardValue.Ace, SuitSymbol.Club, 0), this.model1.getOpenCardAt(0));

  }

  /**
   * Tests the getOpenCardAt method when the game has not started.
   */
  @Test(expected = IllegalStateException.class)
  public void gameNotStartedGetOpenCardAt() {
    this.model1.getOpenCardAt(2);
  }

  /**
   * Tests the getOpenCardAt method with a too small pile index.
   */
  @Test(expected = IllegalArgumentException.class)
  public void smallPileIndexGetOpenCardAt() {
    this.model1.startGame(this.model1.getDeck(), 4, 4, false);
    // index too small
    this.model1.getOpenCardAt(-1);
  }

  /**
   * Tests the getOpenCardAt method with a too large pile index.
   */
  @Test(expected = IllegalArgumentException.class)
  public void largePileIndexGetOpenCardAt() {
    this.model1.startGame(this.model1.getDeck(), 4, 4, false);
    // index too small
    this.model1.getOpenCardAt(5);
  }

  /**
   * Tests the getOpenCardAt method when a card is null in the deck.
   */
  @Test
  public void testNullGetOpenCardAt() {
    this.model1.startGame(this.model1.getDeck(), 4, 4, false);
    // moves 3 cards from cascade to 3 separate open piles, leaving the last open pile empty
    this.model1.move(PileType.CASCADE, 0, 12, PileType.OPEN, 0);
    this.model1.move(PileType.CASCADE, 0, 11, PileType.OPEN, 1);
    this.model1.move(PileType.CASCADE, 0, 10, PileType.OPEN, 2);
    assertNull(this.model1.getOpenCardAt(3));
  }

  /**
   * Tests the getOpenCardAt method when there is no exceptions.
   */
  @Test
  public void correctGetOpenCardAt() {
    this.model1.startGame(this.model1.getDeck(), 4, 4, false);
    this.model1.move(PileType.CASCADE, 0, 12, PileType.OPEN, 0);
    assertEquals(new Card(CardValue.Ace, SuitSymbol.Club, 1), this.model1.getOpenCardAt(0));
  }

  /**
   * Tests the getCascadeCardAt method when the game has not started.
   */
  @Test(expected = IllegalStateException.class)
  public void gameNotStartedGetCascadeCardAt() {
    this.model1.getCascadeCardAt(2, 1);
  }

  /**
   * Tests the getCascadeCardAt method with a too small pile index.
   */
  @Test(expected = IllegalArgumentException.class)
  public void smallPileIndexGetCascadeCardAt() {
    this.model1.startGame(this.model1.getDeck(), 4, 4, false);
    // index too small
    this.model1.getCascadeCardAt(-1, 1);
  }

  /**
   * Tests the getCascadeCardAt method with a too large pile index.
   */
  @Test(expected = IllegalArgumentException.class)
  public void largePileIndexGetCascadeCardAt() {
    this.model1.startGame(this.model1.getDeck(), 4, 4, false);
    // index too small
    this.model1.getCascadeCardAt(5, 1);
  }

  /**
   * Tests the getCascadeCardAt method with a too large card index.
   */
  @Test(expected = IllegalArgumentException.class)
  public void largeCardIndexGetCascadeCardAt() {
    this.model1.startGame(this.model1.getDeck(), 4, 4, false);
    this.model1.move(PileType.CASCADE, 0, 12, PileType.OPEN, 0);
    // index too large
    this.model1.getCascadeCardAt(0, 12);
  }

  /**
   * Tests the getCascadeCardAt method that works correctly.
   */
  @Test
  public void correctGetCascadeCardAt() {
    this.model1.startGame(this.model1.getDeck(), 4, 4, false);
    assertEquals(new Card(CardValue.Ace, SuitSymbol.Club, 1), this.model1.getCascadeCardAt(0, 12));
  }

  /**
   * Tests the getCascadeCardAt method with a too small card index.
   */
  @Test(expected = IllegalArgumentException.class)
  public void smallCardIndexGetCascadeCardAt() {
    this.model1.startGame(this.model1.getDeck(), 4, 4, false);
    // index too small
    this.model1.getCascadeCardAt(0, -1);
  }

  // TEST MOVE

  /**
   * Moves when game has not started.
   */
  @Test(expected = IllegalStateException.class)
  public void moveGameNotStarted() {
    this.model1.move(PileType.CASCADE, 0, 11, PileType.OPEN, 0);
  }

  /**
   * Moves from foundation.
   */
  @Test(expected = IllegalArgumentException.class)
  public void moveFromFoundation() {
    this.model1.startGame(deck1, 4, 4, false);
    this.model1.move(PileType.FOUNDATION, 0, 0, PileType.CASCADE, 0);
  }


  /**
   * Moves from Cascade where source does not contain any cards.
   */
  @Test(expected = IllegalArgumentException.class)
  public void invalidSrcIndexCascade() {
    this.model1.startGame(deck1, 4, 4, false);

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

    this.model1.move(PileType.CASCADE, 0, 0, PileType.FOUNDATION, 1);

  }

  /**
   * Moves from Cascade where the pile index is invalid.
   */
  @Test(expected = IllegalArgumentException.class)
  public void cascadePileIndexUpperBoundInvalid() {
    this.model1.startGame(deck1, 4, 4, false);
    this.model1.move(PileType.CASCADE, 5, 0, PileType.OPEN, 0);
  }

  /**
   * Moves from Cascade where the pile index is invalid.
   */
  @Test(expected = IllegalArgumentException.class)
  public void cascadePileIndexLowerBoundInvalid() {
    this.model1.startGame(deck1, 15, 4, false);
    this.model1.move(PileType.CASCADE, -1, 0, PileType.OPEN, 0);

  }

  /**
   * Moves from Cascade where the card index is not the last in pile.
   */
  @Test(expected = IllegalArgumentException.class)
  public void cascadeIndexNotLastInPile() {
    this.model1.startGame(deck1, 4, 4, false);
    this.model1.move(PileType.CASCADE, 0, 11, PileType.OPEN, 0);
  }

  /**
   * Moves from Cascade to Open where destination pile number is out of bound.
   */
  @Test(expected = IllegalArgumentException.class)
  public void cascadeToInvalidUpperPileNumberOpen() {
    this.model1.startGame(deck1, 4, 4, false);
    this.model1.move(PileType.CASCADE, 0, 0, PileType.OPEN, 5);
  }

  /**
   * Moves from Cascade to Open where destination pile number is out of bound.
   */
  @Test(expected = IllegalArgumentException.class)
  public void cascadeToInvalidLowerPileNumberOpen() {
    this.model1.startGame(deck1, 4, 4, false);
    this.model1.move(PileType.CASCADE, 0, 0, PileType.OPEN, -1);
  }


  /**
   * Cascade -> Open: destination not empty.
   */
  @Test(expected = IllegalArgumentException.class)
  public void destinationNotMtCascadeToOpen() {
    this.model1.startGame(deck1, 4, 4, false);
    this.model1.move(PileType.CASCADE, 0, 12, PileType.OPEN, 0);
    this.model1.move(PileType.CASCADE, 0, 11, PileType.OPEN, 0);
  }

  /**
   * Cascade -> Open: works correctly.
   */
  @Test
  public void correctCascadeToOpen() {
    this.model1.startGame(this.model1.getDeck(), 4, 4, false);
    this.model1.move(PileType.CASCADE, 0, 12, PileType.OPEN, 0);
    assertEquals(new Card(CardValue.Ace, SuitSymbol.Club, 1), this.model1.getOpenCardAt(0));
  }

  /**
   * Moves from Cascade to Cascade where destination pile number is out of bound.
   */
  @Test(expected = IllegalArgumentException.class)
  public void cascadeToInvalidPileNumberUpperCascade() {
    this.model1.startGame(deck1, 4, 4, false);
    this.model1.move(PileType.CASCADE, 0, 0, PileType.CASCADE, 5);
  }

  /**
   * Moves from Cascade to Cascade where destination pile number is out of bound.
   */
  @Test(expected = IllegalArgumentException.class)
  public void cascadeToInvalidPileNumberLowerCascade() {
    this.model1.startGame(deck1, 4, 4, false);
    this.model1.move(PileType.CASCADE, 0, 0, PileType.CASCADE, -1);
  }

  /**
   * Cascade -> Cascade : invalid because of card value.
   */
  @Test(expected = IllegalArgumentException.class)
  public void incorrectCardValueCascadeToCascade() {
    this.model1.startGame(deck1, 4, 4, false);
    this.model1.move(PileType.CASCADE, 0, 12, PileType.CASCADE, 2);
  }

  /**
   * Cascade -> Cascade : invalid because of Suit Symbol.
   */
  @Test(expected = IllegalArgumentException.class)
  public void incorrectSuitSymbolCascadeToCascade() {
    this.model1.startGame(deck1, 4, 4, false);
    this.model1.move(PileType.CASCADE, 0, 12, PileType.CASCADE, 1);
  }

  /**
   * Cascade -> Cascade: correct.
   */
  @Test
  public void correctCascadeToCascade() {
    this.model1.startGame(this.model1.getDeck(), 4, 2, false);
    // move Ace of club from cascade pile 0 to an open pile
    this.model1.move(PileType.CASCADE, 0, 12, PileType.OPEN, 0);
    // move Ace of diamond to cascade pile 0
    this.model1.move(PileType.CASCADE, 2, 12, PileType.CASCADE, 0);
    assertEquals(new Card(CardValue.Ace, SuitSymbol.Diamond, 1),
        this.model1.getCascadeCardAt(0, 12));
  }


  /**
   * Cascade -> Foundation: first card is not A.
   */
  @Test(expected = IllegalArgumentException.class)
  public void notAceCascadeToFoundation() {
    this.model1.startGame(this.model1.getDeck(), 4, 2, false);
    // moves Ace of Club and 2 of Club from cascade pile to open pile 0 and 1
    this.model1.move(PileType.CASCADE, 0, 12, PileType.OPEN, 0);
    this.model1.move(PileType.CASCADE, 0, 11, PileType.FOUNDATION, 1);
  }

  /**
   * Moves from Cascade to Foundation where the card index is not the last in pile.
   */
  @Test(expected = IllegalArgumentException.class)
  public void cascadeToFoundationNotLastCard() {
    this.model1.startGame(deck1, 4, 4, false);
    this.model1.move(PileType.CASCADE, 0, 10, PileType.FOUNDATION, 0);
  }

  /**
   * Cascade -> Foundation where destination pile number is out of bound.
   */
  @Test(expected = IllegalArgumentException.class)
  public void cascadeToInvalidUpperBoundPileNumberFoundation() {
    this.model1.startGame(deck1, 4, 4, false);
    this.model1.move(PileType.CASCADE, 0, 11, PileType.OPEN, 0);
    this.model1.move(PileType.OPEN, 0, 0, PileType.CASCADE, 5);
  }

  /**
   * Cascade -> Foundation where destination pile number is out of bound.
   */
  @Test(expected = IllegalArgumentException.class)
  public void cascadeToInvalidLowerBoundPileNumberFoundation() {
    this.model1.startGame(deck1, 4, 4, false);
    this.model1.move(PileType.CASCADE, 0, 11, PileType.OPEN, 0);
    this.model1.move(PileType.OPEN, 0, 0, PileType.CASCADE, -1);
  }

  /**
   * Cascade -> Foundation: tests when the source card is compatible to move to destination.
   */
  @Test
  public void correctCascadeToFoundation() {
    this.model1.startGame(this.model1.getDeck(), 4, 2, false);
    // moves valid cascade to foundation
    this.model1.move(PileType.CASCADE, 0, 12, PileType.FOUNDATION, 0);
    this.model1.move(PileType.CASCADE, 0, 11, PileType.FOUNDATION, 0);
    assertEquals(new Card(CardValue.Two, SuitSymbol.Club, 1),
        this.model1.getFoundationCardAt(0, 1));
  }

  /**
   * Moves from Open where source does not contain any cards.
   */
  @Test(expected = IllegalArgumentException.class)
  public void invalidSrcIndexOpen() {
    this.model1.startGame(deck1, 4, 4, false);
    this.model1.move(PileType.OPEN, 0, 0, PileType.OPEN, 0);
  }

  /**
   * Moves from Open where the card index is not the last in pile.
   */
  @Test(expected = IllegalArgumentException.class)
  public void openIndexNotLastInPile() {
    this.model1.startGame(deck1, 4, 4, false);
    this.model1.move(PileType.CASCADE, 0, 12, PileType.OPEN, 0);
    this.model1.move(PileType.OPEN, 0, 1, PileType.OPEN, 1);
  }

  /**
   * Moves from Open where source does not contain any cards.
   */
  @Test(expected = IllegalArgumentException.class)
  public void openSourceNull() {
    this.model1.startGame(deck1, 15, 4, false);
    this.model1.move(PileType.OPEN, 0, 0, PileType.FOUNDATION, 0);
  }

  /**
   * Moves Open to Open where destination is full.
   */
  @Test(expected = IllegalArgumentException.class)
  public void openToFullOpen() {
    this.model1.startGame(deck1, 4, 4, false);
    this.model1.move(PileType.CASCADE, 0, 11, PileType.OPEN, 0);
    this.model1.move(PileType.OPEN, 0, 0, PileType.OPEN, 0);
  }

  /**
   * Moves from Open to Open where destination pile number is out of bound.
   */
  @Test(expected = IllegalArgumentException.class)
  public void openToInvalidPileNumberOpen() {
    this.model1.startGame(deck1, 15, 4, false);
    this.model1.move(PileType.CASCADE, 0, 11, PileType.OPEN, 0);
    this.model1.move(PileType.OPEN, 0, 0, PileType.OPEN, 5);
  }

  /**
   * Correctly moves Open to Open.
   */
  @Test
  public void correctOpenToOpen() {
    this.model1.startGame(deck1, 4, 4, false);
    this.model1.move(PileType.CASCADE, 0, 12, PileType.OPEN, 0);
    this.model1.move(PileType.OPEN, 0, 0, PileType.OPEN, 1);
    assertEquals(new Card(CardValue.Ace, SuitSymbol.Club, 12), this.model1.getOpenCardAt(1));
  }

  /**
   * Moves from Open to Foundation where destination pile number is out of bound.
   */
  @Test(expected = IllegalArgumentException.class)
  public void openToInvalidUpperBoundPileNumberCascade() {
    this.model1.startGame(deck1, 4, 4, false);
    this.model1.move(PileType.CASCADE, 0, 12, PileType.OPEN, 0);
    this.model1.move(PileType.OPEN, 0, 0, PileType.CASCADE, 5);
  }

  /**
   * Moves from Open to Foundation where destination pile number is out of bound.
   */
  @Test(expected = IllegalArgumentException.class)
  public void openToInvalidLowerBoundPileNumberCascade() {
    this.model1.startGame(deck1, 4, 4, false);
    this.model1.move(PileType.CASCADE, 0, 12, PileType.OPEN, 0);
    this.model1.move(PileType.OPEN, 0, 0, PileType.CASCADE, -1);
  }

  /**
   * Open -> Cascade : tests when move from open to cascade is invalid because of card value.
   */
  @Test(expected = IllegalArgumentException.class)
  public void incorrectCardValueOpenToCascade() {
    this.model1.startGame(deck1, 4, 4, false);
    this.model1.move(PileType.CASCADE, 0, 12, PileType.OPEN, 0);
    this.model1.move(PileType.OPEN, 0, 0, PileType.CASCADE, 1);
  }

  /**
   * Open -> Cascade : tests when move from open to cascade is invalid because of Suit Symbol.
   */
  @Test(expected = IllegalArgumentException.class)
  public void incorrectSuitSymbolOpenToCascade() {
    this.model1.startGame(deck1, 4, 4, false);
    this.model1.move(PileType.CASCADE, 0, 12, PileType.OPEN, 0);
    this.model1.move(PileType.OPEN, 0, 0, PileType.CASCADE, 0);
  }


  /**
   * Open -> Cascade : tests when the source card is compatible to move to destination.
   */
  @Test
  public void correctOpenToCascade() {
    this.model1.startGame(this.model1.getDeck(), 4, 2, false);
    // moves valid cascade to open
    this.model1.move(PileType.CASCADE, 0, 12, PileType.OPEN, 0);
    this.model1.move(PileType.CASCADE, 2, 12, PileType.OPEN, 1);
    // moves valid open back to cascade
    this.model1.move(PileType.OPEN, 0, 0, PileType.CASCADE, 2);
    assertEquals(new Card(CardValue.Ace, SuitSymbol.Club, 1), this.model1.getCascadeCardAt(2, 12));
  }


  /**
   * Open -> Foundation: CardValue SuitSymbol first card is not A.
   */
  @Test(expected = IllegalArgumentException.class)
  public void notAceOpenToFoundation() {
    this.model1.startGame(this.model1.getDeck(), 4, 2, false);
    // moves Ace of Club and 2 of Club from cascade pile to open pile 0 and 1
    this.model1.move(PileType.CASCADE, 0, 12, PileType.OPEN, 0);
    this.model1.move(PileType.CASCADE, 0, 11, PileType.OPEN, 1);
    this.model1.move(PileType.OPEN, 1, 0, PileType.FOUNDATION, 0);
  }

  /**
   * Moves from Open to Foundation where destination pile number is out of bound.
   */
  @Test(expected = IllegalArgumentException.class)
  public void openToInvalidUpperBoundPileNumberFoundation() {
    this.model1.startGame(deck1, 4, 4, false);
    this.model1.move(PileType.CASCADE, 0, 12, PileType.OPEN, 0);
    this.model1.move(PileType.OPEN, 0, 0, PileType.FOUNDATION, 5);
  }

  /**
   * Moves from Open to Foundation where destination pile number is out of bound.
   */
  @Test(expected = IllegalArgumentException.class)
  public void openToInvalidLowerBoundPileNumberFoundation() {
    this.model1.startGame(deck1, 4, 4, false);
    this.model1.move(PileType.CASCADE, 0, 12, PileType.OPEN, 0);
    this.model1.move(PileType.OPEN, 0, 0, PileType.FOUNDATION, -1);
  }


  /**
   * valid move from Open to Foundation.
   */
  @Test
  public void validOpenToFoundation() {
    this.model1.startGame(this.model1.getDeck(), 4, 2, false);
    // moves Ace of Club and 2 of Club from cascade pile to open pile 0 and 1
    this.model1.move(PileType.CASCADE, 0, 12, PileType.OPEN, 0);
    this.model1.move(PileType.CASCADE, 0, 11, PileType.OPEN, 1);
    // moves Ace of Club and 2 of Club from open piles to foundation pile 0
    this.model1.move(PileType.OPEN, 0, 0, PileType.FOUNDATION, 0);
    this.model1.move(PileType.OPEN, 1, 0, PileType.FOUNDATION, 0);
    assertEquals(2, this.model1.getNumCardsInFoundationPile(0));
    assertEquals(new Card(CardValue.Ace, SuitSymbol.Club, 0),
        this.model1.getFoundationCardAt(0, 0));
    assertEquals(new Card(CardValue.Two, SuitSymbol.Club, 0),
        this.model1.getFoundationCardAt(0, 1));
  }


  /**
   * tests calling startGame when a game has started.
   */
  @Test
  public void startGameWhileStarted() {
    this.model1.startGame(this.model1.getDeck(), 4, 2, false);
    this.model1.move(PileType.CASCADE, 0, 12, PileType.OPEN, 0);
    this.model1.move(PileType.CASCADE, 0, 11, PileType.OPEN, 1);
    this.model1.move(PileType.CASCADE, 1, 12, PileType.FOUNDATION, 0);
    this.model1.startGame(this.model1.getDeck(), 4, 2, false);
    assertEquals(new Card(CardValue.Ace, SuitSymbol.Club, 0),
        this.model1.getCascadeCardAt(0, 12));
    assertEquals(new Card(CardValue.Two, SuitSymbol.Club, 0),
        this.model1.getCascadeCardAt(0, 11));
    assertEquals(new Card(CardValue.Three, SuitSymbol.Club, 0),
        this.model1.getCascadeCardAt(0, 10));
    assertEquals(0, this.model1.getNumCardsInFoundationPile(0));
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidSimpleMoveWhenMovingAValidBuildSimple() {
    this.model1.startGame(this.model1.getDeck(), 4, 2, false);
    this.model1.move(PileType.CASCADE, 0, 12, PileType.OPEN, 0);
    this.model1.move(PileType.CASCADE, 2, 12, PileType.CASCADE, 0);
    this.model1.move(PileType.CASCADE, 2, 11, PileType.OPEN, 1);
    //moving a valid build of 2 cards to another
    this.model1.move(PileType.CASCADE, 0, 11, PileType.CASCADE, 2);
  }


}

