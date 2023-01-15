package cs3500.freecell.controller;

import cs3500.freecell.model.Card;
import cs3500.freecell.model.FreecellModel;
import cs3500.freecell.model.PileType;
import cs3500.freecell.view.FreecellTextView;
import cs3500.freecell.view.FreecellView;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

/**
 * A Simple controller for the freecell game. An implementation will work with the IFreeCellModel
 * interface to provide a game of freecell.
 */
public class SimpleFreecellController implements FreecellController<Card> {

  private final FreecellModel<Card> model;
  private final Readable rd;
  private final Appendable ap;
  private final FreecellView view;
  // initialized the following to meaningless values, and used void methods to change them
  private PileType pileType;
  private int pileNumber;
  private int cardIndex;
  private PileType destType;
  private int destPileNumber;


  /**
   * Constructs a controller for a simple freecell game.
   *
   * @param model model for a simple freecell game.
   * @param rd    an existing java interface that abstracts input.
   * @param ap    an existing java interface that abstracts output.
   * @throws IllegalArgumentException if any inputs are null.
   */
  public SimpleFreecellController(FreecellModel<Card> model, Readable rd, Appendable ap)
      throws IllegalArgumentException {
    if (model == null || rd == null || ap == null) {
      throw new IllegalArgumentException("argument cannot be null");
    } else {
      this.model = model;
      this.rd = rd;
      this.ap = ap;
      this.view = new FreecellTextView(this.model, this.ap);
      this.pileType = null;
      this.pileNumber = Integer.MAX_VALUE;
      this.cardIndex = Integer.MAX_VALUE;
      this.destType = null;
      this.destPileNumber = Integer.MAX_VALUE;
    }
  }

  @Override
  public void playGame(List<Card> deck, int numCascades, int numOpens, boolean shuffle)
      throws IllegalStateException, IllegalArgumentException {
    if (deck == null) {
      throw new IllegalArgumentException("Deck cannot be null.");
    }
    try {
      // start the game
      try {
        this.model.startGame(deck, numCascades, numOpens, shuffle);
      } catch (IllegalArgumentException | IllegalStateException e) {
        this.view.renderMessage("Could not start game.");
        return;
      }
      this.view.renderBoard();
      this.ap.append("\n\n");
      Scanner scan = new Scanner(this.rd);

      this.hasNext(scan);
      while (scan.hasNext()) {
        if (this.findSourcePile(scan)) {
          return;
        } else {
          this.hasNext(scan);
          if (this.findCardIndex(scan)) {
            return;
          } else {
            this.hasNext(scan);
            if (this.findDestPile(scan)) {
              return;
            } else {
              try {
                this.model
                    .move(this.pileType, this.pileNumber - 1, this.cardIndex - 1, this.destType,
                        this.destPileNumber - 1);
                this.view.renderBoard();
                this.ap.append("\n\n");
              } catch (IllegalArgumentException e) {
                this.view.renderMessage("Invalid move. Try again.\n");
              }
              if (this.model.isGameOver()) {                // check if game is over
                this.view.renderMessage("Game over.");
                return;
              }
            }
          }
        }
      }
      if (!this.model.isGameOver()) {
        throw new IllegalStateException();
      }
    } catch (IOException e) {
      throw new IllegalStateException("Writing to the Appendable object used by it fails");
    }
  }

  /**
   * Looks for the source pile type and pile number and set the fields to pile type and number, if
   * there are none, fields remains null and -1 respectively.
   *
   * @param scan a Scanner that scans every input readable
   * @return true if game source cannot be found or if quit game, else false
   * @throws IOException           if transmission of the board to the provided data destination
   *                               fails
   * @throws IllegalStateException if there is no next element
   */
  private boolean findSourcePile(Scanner scan) throws IOException, IllegalStateException {
    this.hasNext(scan);
    while (scan.hasNext()) {
      String first = scan.next();
      if (this.checkQuitGame(first)) {
        return true;
      }
      // see if the first input has a valid pileType
      switch (first.substring(0, 1).toLowerCase()) {
        case "o":
          this.pileType = PileType.OPEN;
          break;
        case "c" :
          this.pileType = PileType.CASCADE;
          break;
        case "f" :
          this.pileType = PileType.FOUNDATION;
          break;
        default:
          this.view.renderMessage("Re-input source pile type.\n");
          this.hasNext(scan);
          continue;
      }
      try {
        this.pileNumber = Integer.parseInt(first.substring(1));
        break;
      } catch (IllegalArgumentException e) {
        this.view.renderMessage("Re-input source pile type & number.\n");
        this.hasNext(scan);
      }
    }
    return pileType == null || pileNumber == Integer.MAX_VALUE;
  }

  /**
   * Looks for the card index and set the field to the card index set the fields to card index, if
   * there is none, field remains -1.
   *
   * @param scan a Scanner that scans every input readable
   * @return true if card index cannot be found or if quit game, else false
   * @throws IOException           if transmission of the board to the provided data destination
   *                               fails
   * @throws IllegalStateException if there is no next element
   */
  private boolean findCardIndex(Scanner scan) throws IOException, IllegalStateException {
    this.hasNext(scan);
    while (scan.hasNext()) {
      String first = scan.next();
      if (this.checkQuitGame(first)) {
        return true;
      }
      try {
        this.cardIndex = Integer.parseInt(first);
        break;
      } catch (IllegalArgumentException e) {
        this.view.renderMessage("Re-input card index.\n");
        this.hasNext(scan);
      }
    }
    return cardIndex == Integer.MAX_VALUE;
  }

  /**
   * Looks for the destination pile type and pile number. if there are none, fields remain null and
   * -1 respectively.
   *
   * @param scan a Scanner that scans every input readable
   * @return true if game destination cannot be found or if quit game, else false
   * @throws IOException           if transmission of the board to the provided data destination
   *                               fails
   * @throws IllegalStateException if there is no next element
   */
  private boolean findDestPile(Scanner scan) throws IOException, IllegalStateException {
    while (scan.hasNext()) {
      String first = scan.next();
      if (this.checkQuitGame(first)) {
        return true;
      }
      switch (first.substring(0, 1).toLowerCase()) {
        case "o":
          this.destType = PileType.OPEN;
          break;
        case "c" :
          this.destType = PileType.CASCADE;
          break;
        case "f" :
          this.destType = PileType.FOUNDATION;
          break;
        default:
          this.view.renderMessage("Re-input destination pile type.\n");
          this.hasNext(scan);
          continue;
      }
      try {
        this.destPileNumber = Integer.parseInt(first.substring(1));
        break;
      } catch (IllegalArgumentException e) {
        this.view.renderMessage("Re-input destination pile type & number.\n");
        this.hasNext(scan);
      }
    }
    return destType == null || destPileNumber == Integer.MAX_VALUE;
  }

  /**
   * Check if there is a next element in readable.
   *
   * @param scan a Scanner that scans every input readable
   */
  private void hasNext(Scanner scan) throws IllegalStateException {
    if (!scan.hasNext()) {
      throw new IllegalStateException("reading from the provided Readable fails");
    }
  }

  /**
   * Check if the given string is "q" or "Q" to quit the game.
   *
   * @param first the input string to check
   * @return true if there is a q, else false
   * @throws IOException if transmission of the board to the provided data destination fails
   */
  private boolean checkQuitGame(String first) throws IOException {
    if (first.equalsIgnoreCase("q")) {
      this.view.renderMessage("Game quit prematurely.");
      return true;
    }
    return false;
  }
}
