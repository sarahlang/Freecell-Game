package cs3500.freecell.view;

import cs3500.freecell.model.FreecellModelState;
import java.io.IOException;

/**
 * A text view (string format) of the freecell view.
 */
public class FreecellTextView implements FreecellView {

  private final FreecellModelState<?> modelState;
  private Appendable ap;

  /**
   * Constructor for Freecell text view.
   *
   * @param modelState model of the Freecell game
   */
  public FreecellTextView(FreecellModelState<?> modelState, Appendable ap) {
    if (modelState == null) {
      throw new IllegalArgumentException("Could not initialize with null model.");
    }

    this.modelState = modelState;
    this.ap = ap;
  }

  /**
   * Constructor for homework two that only takes in a model.
   *
   * @param modelState model of the Freecell game
   */

  public FreecellTextView(FreecellModelState<?> modelState) {
    if (modelState == null) {
      throw new IllegalArgumentException("Could not initialize with null model.");
    }

    this.modelState = modelState;
  }


  @Override
  public String toString() {
    String result = "";
    // if game has not started;
    if (this.modelState.getNumCascadePiles() == -1) {
      return "";
    }
    // number of piles
    for (int i = 0; i < 4; i++) {
      if (this.modelState.getNumCardsInFoundationPile(i) == 0) {
        result += "F" + String.valueOf(i + 1) + ":" + "\n";
      } else {
        result += "F" + String.valueOf(i + 1) + ":";
      }
      // number of cards within the pile
      for (int j = 0; j < this.modelState.getNumCardsInFoundationPile(i); j++) {
        if (j != this.modelState.getNumCardsInFoundationPile(i) - 1) { // if it's not the last card
          result += " " + this.modelState.getFoundationCardAt(i, j) + ",";
        } else { // if it is the last card
          result += " " + this.modelState.getFoundationCardAt(i, j) + "\n";
        }
      }
    }
    for (int i = 0; i < this.modelState.getNumOpenPiles(); i++) {
      if (this.modelState.getNumCardsInOpenPile(i) == 0) {
        result += "O" + String.valueOf(i + 1) + ":" + "\n";
      } else {
        result += "O" + String.valueOf(i + 1) + ":";
      }
      for (int j = 0; j < this.modelState.getNumCardsInOpenPile(i); j++) {
        if (j != this.modelState.getNumCardsInOpenPile(i) - 1) { // if it's not the last card
          result += " " + this.modelState.getOpenCardAt(i) + ",";
        } else { // if it is the last card
          result += " " + this.modelState.getOpenCardAt(i) + "\n";
        }
      }
    }
    for (int i = 0; i < this.modelState.getNumCascadePiles(); i++) {
      if (this.modelState.getNumCardsInCascadePile(i) == 0) {
        result += "C" + String.valueOf(i + 1) + ":" + "\n";
      } else {
        result += "C" + String.valueOf(i + 1) + ":";
      }
      for (int j = 0; j < this.modelState.getNumCardsInCascadePile(i); j++) {
        if (i == this.modelState.getNumCascadePiles() - 1
            && j == this.modelState.getNumCardsInCascadePile(i) - 1) { // if it's not the last line
          result += " " + this.modelState.getCascadeCardAt(i, j);
        } else { // if it is the last line
          if (j != this.modelState.getNumCardsInCascadePile(i) - 1) { // if it's not the last card
            result += " " + this.modelState.getCascadeCardAt(i, j) + ",";
          } else { // if it is the last card
            result += " " + this.modelState.getCascadeCardAt(i, j) + "\n";
          }
        }
      }
    }

    return result;
  }

  @Override
  public void renderBoard() throws IOException {
    this.ap.append(this.toString());
  }

  @Override
  public void renderMessage(String message) throws IOException {
    this.ap.append(message);
  }

}
