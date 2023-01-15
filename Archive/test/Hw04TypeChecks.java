////
//// DO NOT MODIFY THIS FILE
////
//// You don't need to submit it, but you should make sure it compiles.
//// Further explanation appears below.
////


import java.io.Reader;
import java.io.StringReader;
import java.util.List;

import cs3500.freecell.controller.FreecellController;
import cs3500.freecell.controller.SimpleFreecellController;
import cs3500.freecell.model.PileType;
import cs3500.freecell.model.FreecellModel;

import cs3500.freecell.view.FreecellTextView;
import cs3500.freecell.view.FreecellView;
import cs3500.freecell.model.FreecellModelCreator;

/**
 * This class is provided to check that your code implements the expected API. If your code compiles
 * with an unmodified version of this class, then it very likely will also compile with the tests
 * that we use to evaluate your code.
 */
public class Hw04TypeChecks {

  // This doesn't really need to be a dynamic method, since it doesn't use `this`
  static void checkSignatures() {
    Reader stringReader;
    StringBuffer out;
    FreecellModel<?> model = FreecellModelCreator.create(FreecellModelCreator.GameType.MULTIMOVE);

    checkNewModel(
        FreecellModelCreator.create(FreecellModelCreator.GameType
            .MULTIMOVE),
        FreecellModelCreator.create(FreecellModelCreator.GameType.MULTIMOVE)
            .getDeck());
    stringReader = new StringReader("C1 8 F1 q");
    out = new StringBuffer();
    checkNewController(
        FreecellModelCreator.create(FreecellModelCreator.GameType.SINGLEMOVE),
        FreecellModelCreator.create(FreecellModelCreator.GameType.SINGLEMOVE).getDeck(),
        new SimpleFreecellController(
            FreecellModelCreator.create(FreecellModelCreator.GameType.SINGLEMOVE), stringReader,
            out));
    checkNewController(
        FreecellModelCreator.create(FreecellModelCreator.GameType.MULTIMOVE),
        FreecellModelCreator.create(FreecellModelCreator.GameType.MULTIMOVE).getDeck(),
        new SimpleFreecellController(
            FreecellModelCreator.create(FreecellModelCreator.GameType.MULTIMOVE), stringReader,
            out));

  }

  // This doesn't really need to be a dynamic method, since it doesn't use `this`
  static <K> void checkNewController(FreecellModel<K> model, List<K> deck,
      FreecellController<K> controller) {
    String input = "4 3";

    try {
      controller.playGame(deck, 7, 4, false);
    } catch (IllegalStateException e) {
      e.printStackTrace();
    }
  }

  static <K> void checkNewModel(FreecellModel<K> model, List<K> deck) {
    FreecellView view = new FreecellTextView(model);
    List<K> initialDeck = model.getDeck();
    model.startGame(initialDeck, 7, 4, false);
    model.move(PileType.CASCADE, 0, 7, PileType.OPEN, 0);
    String result = view.toString();
    boolean done = model.isGameOver();
  }

  private Hw04TypeChecks() {
    throw new RuntimeException("Don't instantiate this: use it as a reference");
  }
}
