import cs3500.freecell.model.Card;
import cs3500.freecell.model.CardValue;
import cs3500.freecell.model.PileType;
import cs3500.freecell.model.SuitSymbol;
import cs3500.freecell.model.hw02.SimpleFreecellModel;
import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for SimpleFreecellModel class. Only contains ones that test behaviors that are different
 * from MultiMove, which is only move the last card from a cascade pile to another cascade pile.
 */
public class SimpleFreecellModelTest extends AbstractFreecellModelTest {

  @Before
  public void init() {
    model1 = new SimpleFreecellModel();
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
   * Moves from Cascade to Cascade where the card index is not the last in pile. This only applies
   * to SimpleFreecellModel since Multi can move when it's not the last card in source pile.
   */
  @Test(expected = IllegalArgumentException.class)
  public void cascadeIndexNotLastInPileToCascade() {
    this.model1.startGame(deck1, 4, 4, false);
    this.model1.move(PileType.CASCADE, 0, 11, PileType.CASCADE, 3);
  }

}
