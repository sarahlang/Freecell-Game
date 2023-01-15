package cs3500.freecell.model;

import cs3500.freecell.model.hw02.SimpleFreecellModel;
import cs3500.freecell.model.hw04.MultiCardMoveModel;

/**
 * A factory class that takes in a game type as a command and outputs a freecell model to be
 * executed.
 */
public class FreecellModelCreator {

  /**
   * Type for the two types of models in a game of Freecell. <br> Simgle Move: This game type lets
   * player move only one card at a time from any pile given the move is valid <br> Multi Move: This
   * game type lets player move multiple cards at a time when the move is valid.
   */
  public enum GameType {
    SINGLEMOVE, MULTIMOVE
  }

  /**
   * Determines which game type this Freecell game is going to have, either simple or multi move.
   *
   * @param type game type for the Freecell game
   * @return either a SimpleFreecellModel or an object of your multi-card-move model
   * @throws IllegalArgumentException if a game type is not one of the two enums or if game type is
   *                                  null
   */
  public static FreecellModel<Card> create(GameType type) throws IllegalArgumentException {
    if (type == null) {
      throw new IllegalArgumentException("Game type is null.");
    }
    switch (type) {
      case MULTIMOVE:
        return new MultiCardMoveModel();
      case SINGLEMOVE:
        return new SimpleFreecellModel();
      default:
        throw new IllegalArgumentException("this game type does not exist.");
    }
  }
}
