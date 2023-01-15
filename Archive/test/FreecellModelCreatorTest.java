import static org.junit.Assert.assertTrue;

import cs3500.freecell.model.FreecellModelCreator;
import cs3500.freecell.model.FreecellModelCreator.GameType;
import cs3500.freecell.model.hw02.SimpleFreecellModel;
import cs3500.freecell.model.hw04.MultiCardMoveModel;
import org.junit.Test;

/**
 * Tests for the FreecellModelCreator class.
 */
public class FreecellModelCreatorTest {

  @Test(expected = IllegalArgumentException.class)
  public void creatorTestNull() {
    FreecellModelCreator.create(null);
  }

  @Test
  public void creator() {
    assertTrue(FreecellModelCreator.create(GameType.SINGLEMOVE) instanceof SimpleFreecellModel);
    assertTrue(FreecellModelCreator.create(GameType.MULTIMOVE) instanceof MultiCardMoveModel);
  }
}
