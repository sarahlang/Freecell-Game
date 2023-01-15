
package cs3500.freecell;

import cs3500.freecell.controller.SimpleFreecellController;
import cs3500.freecell.model.Card;
import cs3500.freecell.model.FreecellModel;
import cs3500.freecell.model.FreecellModelCreator;
import cs3500.freecell.model.FreecellModelCreator.GameType;
import cs3500.freecell.model.hw04.MultiCardMoveModel;
import java.io.InputStreamReader;

/**
 * A class for testing with the console.
 */
public class Main {

  /**
   * program execution start point that executes a Freecell model and outputs in the console.
   *
   * @param args a string array of argument
   */
  public static void main(String[] args) {
    try {
      FreecellModel<Card> model = FreecellModelCreator.create(GameType.MULTIMOVE);
      new SimpleFreecellController(model, new InputStreamReader(System.in),
          System.out).playGame(new MultiCardMoveModel().getDeck(), 4, 5, false);
    } catch (IllegalStateException e) {
      e.printStackTrace();
    }
  }

}

