import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import cs3500.freecell.model.Card;
import cs3500.freecell.model.CardValue;
import cs3500.freecell.model.SuitSymbol;
import org.junit.Test;

/**
 * Tests the Card class.
 */
public class CardTest {

  Card aceClub = new Card(CardValue.Ace, SuitSymbol.Club, 0);
  Card aceClub_2 = new Card(CardValue.Ace, SuitSymbol.Club, 2);

  @Test
  public void checkToString() {
    assertEquals("A♣", this.aceClub.toString());
  }

  @Test
  public void checkEqualsTo() {
    assertTrue(this.aceClub.equals(this.aceClub_2));
  }

  @Test
  public void checkGetCardValue() {
    assertEquals(CardValue.Ace, this.aceClub.getCardValue());
  }

  @Test
  public void checkGetSuitSymbol() {
    assertEquals(SuitSymbol.Club, this.aceClub.getSuitSymbol());
  }

  @Test
  public void checkHashCode() {
    assertEquals(0, this.aceClub.hashCode());
    assertEquals(2, this.aceClub_2.hashCode());
  }
}
