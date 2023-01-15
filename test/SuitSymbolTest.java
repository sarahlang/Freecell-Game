import static org.junit.Assert.assertEquals;

import cs3500.freecell.model.SuitSymbol;
import org.junit.Test;

/**
 * Tests the SuitSymbol class.
 */
public class SuitSymbolTest {

  @Test
  public void testGetColor() {
    assertEquals("black", SuitSymbol.Club.getColor());
  }

  @Test
  public void testToString() {
    assertEquals("♦", SuitSymbol.Diamond.toString());
  }
}
