import static junit.framework.TestCase.assertEquals;

import cs3500.freecell.model.Card;
import cs3500.freecell.model.FreecellModel;
import cs3500.freecell.model.PileType;
import cs3500.freecell.model.hw02.SimpleFreecellModel;
import cs3500.freecell.view.FreecellTextView;
import java.io.IOException;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for FreecellTextView class.
 */
public class FreecellTextViewTest {

  private FreecellModel<Card> model;
  private FreecellTextView view;
  private Appendable appendable;

  @Before
  public void setup() {
    this.appendable = new StringBuilder();
    this.model = new SimpleFreecellModel();
    this.view = new FreecellTextView(this.model, this.appendable);
  }

  /**
   * Tests the toString method where game hasn't started (should return empty string).
   */
  @Test
  public void gameNotStartedPile() {
    assertEquals("", this.view.toString());
  }

  /**
   * Tests the toString method where open and foundation piles are empty.
   */
  @Test
  public void testEmptyFAndOPile() {
    this.model.startGame(this.model.getDeck(), 5, 4, false);
    String str =
        "F1:\n" + "F2:\n" + "F3:\n" + "F4:\n"
            + "O1:\n" + "O2:\n" + "O3:\n" + "O4:\n"
            + "C1: K♣, Q♠, J♦, 10♥, 8♣, 7♠, 6♦, 5♥, 3♣, 2♠, A♦\n"
            + "C2: K♠, Q♦, J♥, 9♣, 8♠, 7♦, 6♥, 4♣, 3♠, 2♦, A♥\n"
            + "C3: K♦, Q♥, 10♣, 9♠, 8♦, 7♥, 5♣, 4♠, 3♦, 2♥\n"
            + "C4: K♥, J♣, 10♠, 9♦, 8♥, 6♣, 5♠, 4♦, 3♥, A♣\n"
            + "C5: Q♣, J♠, 10♦, 9♥, 7♣, 6♠, 5♦, 4♥, 2♣, A♠";
    assertEquals(str, this.view.toString());
  }

  /**
   * Tests the toString method where open and foundation piles are not empty.
   */
  @Test
  public void testNonMtFAndO() {
    this.model.startGame(this.model.getDeck(), 5, 4, false);
    String str =
        "F1: A♥\n"
            + "F2:\n"
            + "F3:\n"
            + "F4:\n"
            + "O1: A♦\n"
            + "O2:\n"
            + "O3:\n"
            + "O4:\n"
            + "C1: K♣, Q♠, J♦, 10♥, 8♣, 7♠, 6♦, 5♥, 3♣, 2♠\n"
            + "C2: K♠, Q♦, J♥, 9♣, 8♠, 7♦, 6♥, 4♣, 3♠, 2♦\n"
            + "C3: K♦, Q♥, 10♣, 9♠, 8♦, 7♥, 5♣, 4♠, 3♦, 2♥\n"
            + "C4: K♥, J♣, 10♠, 9♦, 8♥, 6♣, 5♠, 4♦, 3♥, A♣\n"
            + "C5: Q♣, J♠, 10♦, 9♥, 7♣, 6♠, 5♦, 4♥, 2♣, A♠";
    this.model.move(PileType.CASCADE, 0, 10, PileType.OPEN, 0);
    this.model.move(PileType.CASCADE, 1, 10, PileType.FOUNDATION, 0);
    assertEquals(str, this.view.toString());
  }

  /**
   * Tests the toString method where one cascade pile is empty and two foundation piles are full.
   */
  @Test
  public void testMtCascade() {
    this.model.startGame(this.model.getDeck(), 4, 4, false);

    String str =
        "F1: A♣, 2♣, 3♣, 4♣, 5♣, 6♣, 7♣, 8♣, 9♣, 10♣, J♣, Q♣, K♣\n"
            + "F2:\n"
            + "F3:\n"
            + "F4:\n"
            + "O1:\n"
            + "O2:\n"
            + "O3:\n"
            + "O4:\n"
            + "C1:\n"
            + "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠\n"
            + "C3: K♦, Q♦, J♦, 10♦, 9♦, 8♦, 7♦, 6♦, 5♦, 4♦, 3♦, 2♦, A♦\n"
            + "C4: K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥, 6♥, 5♥, 4♥, 3♥, 2♥, A♥";
    this.model.move(PileType.CASCADE, 0, 12, PileType.FOUNDATION, 0);
    this.model.move(PileType.CASCADE, 0, 11, PileType.FOUNDATION, 0);
    this.model.move(PileType.CASCADE, 0, 10, PileType.FOUNDATION, 0);
    this.model.move(PileType.CASCADE, 0, 9, PileType.FOUNDATION, 0);
    this.model.move(PileType.CASCADE, 0, 8, PileType.FOUNDATION, 0);
    this.model.move(PileType.CASCADE, 0, 7, PileType.FOUNDATION, 0);
    this.model.move(PileType.CASCADE, 0, 6, PileType.FOUNDATION, 0);
    this.model.move(PileType.CASCADE, 0, 5, PileType.FOUNDATION, 0);
    this.model.move(PileType.CASCADE, 0, 4, PileType.FOUNDATION, 0);
    this.model.move(PileType.CASCADE, 0, 3, PileType.FOUNDATION, 0);
    this.model.move(PileType.CASCADE, 0, 2, PileType.FOUNDATION, 0);
    this.model.move(PileType.CASCADE, 0, 1, PileType.FOUNDATION, 0);
    this.model.move(PileType.CASCADE, 0, 0, PileType.FOUNDATION, 0);

    assertEquals(str, this.view.toString());
  }

  @Test
  public void testInitRenderBoard() throws IOException {
    this.model.startGame(this.model.getDeck(), 4, 4, false);
    this.view.renderBoard();
    String str = "F1:\n"
        + "F2:\n"
        + "F3:\n"
        + "F4:\n"
        + "O1:\n"
        + "O2:\n"
        + "O3:\n"
        + "O4:\n"
        + "C1: K♣, Q♣, J♣, 10♣, 9♣, 8♣, 7♣, 6♣, 5♣, 4♣, 3♣, 2♣, A♣\n"
        + "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠\n"
        + "C3: K♦, Q♦, J♦, 10♦, 9♦, 8♦, 7♦, 6♦, 5♦, 4♦, 3♦, 2♦, A♦\n"
        + "C4: K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥, 6♥, 5♥, 4♥, 3♥, 2♥, A♥\n"
        + "\n";
    assertEquals(str, this.appendable.toString());
  }

  @Test
  public void testOneMoveRenderBoard() throws IOException {
    this.model.startGame(this.model.getDeck(), 4, 4, false);
    this.model.move(PileType.CASCADE, 0, 12, PileType.FOUNDATION, 0);
    this.view.renderBoard();
    String str = "F1: A♣\n"
        + "F2:\n"
        + "F3:\n"
        + "F4:\n"
        + "O1:\n"
        + "O2:\n"
        + "O3:\n"
        + "O4:\n"
        + "C1: K♣, Q♣, J♣, 10♣, 9♣, 8♣, 7♣, 6♣, 5♣, 4♣, 3♣, 2♣\n"
        + "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠\n"
        + "C3: K♦, Q♦, J♦, 10♦, 9♦, 8♦, 7♦, 6♦, 5♦, 4♦, 3♦, 2♦, A♦\n"
        + "C4: K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥, 6♥, 5♥, 4♥, 3♥, 2♥, A♥\n"
        + "\n";
    assertEquals(str, this.appendable.toString());
  }

  @Test
  public void testTwoMovesRenderBoard() throws IOException {
    this.model.startGame(this.model.getDeck(), 4, 4, false);
    this.model.move(PileType.CASCADE, 0, 12, PileType.FOUNDATION, 0);
    this.model.move(PileType.CASCADE, 2, 12, PileType.CASCADE, 0);
    this.view.renderBoard();
    String str = "F1: A♣\n"
        + "F2:\n"
        + "F3:\n"
        + "F4:\n"
        + "O1:\n"
        + "O2:\n"
        + "O3:\n"
        + "O4:\n"
        + "C1: K♣, Q♣, J♣, 10♣, 9♣, 8♣, 7♣, 6♣, 5♣, 4♣, 3♣, 2♣, A♦\n"
        + "C2: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠\n"
        + "C3: K♦, Q♦, J♦, 10♦, 9♦, 8♦, 7♦, 6♦, 5♦, 4♦, 3♦, 2♦\n"
        + "C4: K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥, 6♥, 5♥, 4♥, 3♥, 2♥, A♥\n"
        + "\n";
    assertEquals(str, this.appendable.toString());
  }

  @Test
  public void testMTRenderMessage() throws IOException {
    this.view.renderMessage("");
    String str = "";
    assertEquals(str, this.appendable.toString());
  }

  @Test
  public void testHiRenderMessage() throws IOException {
    this.view.renderMessage("hi");
    String str = "hi";
    assertEquals(str, this.appendable.toString());
  }

  @Test
  public void testOODRenderMessage() throws IOException {
    this.view.renderMessage("ood homeworks take so much time");
    String str = "ood homeworks take so much time";
    assertEquals(str, this.appendable.toString());
  }
}
