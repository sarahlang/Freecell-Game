import static org.junit.Assert.assertEquals;

import cs3500.freecell.model.CardValue;
import org.junit.Test;

/**
 * Tests the CardValue class.
 */
public class CardValueTest {

  @Test
  public void testGetCardIndex() {
    assertEquals(0, CardValue.Ace.getCardIndex());
  }

  @Test
  public void testToString() {
    assertEquals("K", CardValue.King.toString());
  }
}
