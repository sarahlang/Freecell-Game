package cs3500.freecell.model.hw04;

import cs3500.freecell.model.CardValue;
import cs3500.freecell.model.FreecellModel;
import cs3500.freecell.model.Card;
import cs3500.freecell.model.PileType;
import cs3500.freecell.model.SuitSymbol;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * This is an abstract class I aadded that represents the model for a simple Freecell game. Simple
 * and Multi move freecell models extend this class. The only difference between the two is when
 * moving from cascade to cascade pile, multi move allows multiple cards to be moved at the same
 * time.
 */
public abstract class AbstractFreecellModel implements FreecellModel<Card> {

  /**
   * represents fields of SimpleFreecellModel.
   */
  protected Card[] open;
  protected ArrayList<ArrayList<Card>> cascade;
  // when one cascade pile is empty? is everything going to switch with arrayList?
  protected ArrayList<ArrayList<Card>> foundation;
  protected boolean started;

  /**
   * represents constructor for SimpleFreecellModel.
   */
  public AbstractFreecellModel() {
    this.open = null;
    this.cascade = new ArrayList<ArrayList<Card>>();
    this.foundation = new ArrayList<ArrayList<Card>>(Arrays.asList(
        new ArrayList<Card>(), new ArrayList<Card>(), new ArrayList<Card>(),
        new ArrayList<Card>()));
    this.started = false;
  }

  @Override
  public List<Card> getDeck() {
    List<Card> deck = new ArrayList<>();
    int counter = 0;
    for (CardValue c : CardValue.values()) {
      for (SuitSymbol s : SuitSymbol.values()) {
        deck.add(new Card(c, s, counter));
        counter++;
      }
    }
    return deck;
  }

  @Override
  public void startGame(List<Card> deck, int numCascadePiles, int numOpenPiles, boolean shuffle)
      throws IllegalArgumentException {
    this.cascade = new ArrayList<>();
    if (deck == null || !this.validDeck(deck)) {
      throw new IllegalArgumentException("deck is invalid");
    }
    if (!(numCascadePiles >= 4 && numOpenPiles >= 1)) {
      throw new IllegalArgumentException("number of piles is too few");
    } else {
      ArrayList<Card> cards = new ArrayList<>(deck);
      if (shuffle) { // do not need to test, presumably it works correctly
        Collections.shuffle(cards);
      }
      this.open = new Card[numOpenPiles];
      for (int i = 0; i < numCascadePiles; i++) {
        cascade.add(new ArrayList<Card>());
      }
      for (int i = 0; i < 52; i++) {
        this.cascade.get(i % numCascadePiles).add(cards.get(i));
      }
      this.foundation = new ArrayList<ArrayList<Card>>(Arrays.asList(
          new ArrayList<Card>(), new ArrayList<Card>(), new ArrayList<Card>(),
          new ArrayList<Card>()));
    }
    this.started = true;
  }


  /**
   * checks if a deck is valid deck with 52 cards with no duplicates.
   *
   * @param deck the deck of cards used to start the game
   * @return true if a deck is valid, false if not
   */
  protected boolean validDeck(List<Card> deck) {
    Set<Card> noDuplicateSet = new HashSet<Card>(deck);
    // check deck isn't null && has 52 cards && has no duplicates
    return deck.size() == 52 && this.getDeck().size() == noDuplicateSet.size();
  }


  /**
   * Checks the destination pile number in move method is valid.
   *
   * @param destPileNumber the pile number of the destination pile
   * @param upperBound     the upper bound of the pile number (size - 1)
   * @throws IllegalArgumentException if pile number is lower than 0 r greater than its upper bound
   */
  protected void checkInvalidDestPileNum(int destPileNumber, int upperBound) {
    if (destPileNumber >= upperBound || destPileNumber < 0) {
      throw new IllegalArgumentException("invalid destination pile number");
    }
  }

  /**
   * Checks if the game has started.
   *
   * @throws IllegalStateException if the game has not started
   */
  protected void checkStarted() {
    if (!this.started) {
      throw new IllegalStateException("the game has not started");
    }
  }

  /**
   * Checks if the destination pile is full when destination is open.
   *
   * @param destPileNumber the pile number of the destination pile
   * @throws IllegalArgumentException if the destination open pile has a card in it
   */
  protected void checkDestOpenPileFull(int destPileNumber) {
    if (this.getNumCardsInOpenPile(destPileNumber) != 0) {
      throw new IllegalArgumentException("destination open pile is full");
    }
  }


  /**
   * Achieves the actual adding and removing (to and from a pile) process of the move method,
   * provided when the move is not invalid. This method is changed to combine the previous
   * "addToPile" method.
   *
   * @param destType       type of the destination pile
   * @param destPileNumber pile number of the destination pile
   * @param cardInSrc      the card to move in source
   * @param srcPile        type of the source pile
   * @param pileNumber     pile number of the source pile
   */
  protected void addAndRemove(PileType destType, int destPileNumber, Card cardInSrc,
      PileType srcPile, int pileNumber, int cardIndex) {
    int lastInSrcIndex = cardInSrc.getCardValue().getCardIndex();
    boolean checkIndex;
    switch (destType) {
      case FOUNDATION:
        if (getNumCardsInFoundationPile(destPileNumber) == 0) {
          if (lastInSrcIndex == 0) {
            this.foundation.get(destPileNumber).add(cardInSrc);
          } else {
            throw new IllegalArgumentException("cannot move anything other "
                + "than Ace to beginning of foundation pile");
          }
        } else {
          Card lastInDest =
              this.foundation.get(destPileNumber)
                  .get(this.foundation.get(destPileNumber).size() - 1);
          boolean compareColors = lastInDest.getSuitSymbol().getColor()
              .equals(cardInSrc.getSuitSymbol().getColor());
          checkIndex = this.foundation.get(destPileNumber).size() == lastInSrcIndex;
          if (checkIndex && compareColors) {
            this.foundation.get(destPileNumber).add(cardInSrc);
          } else {
            throw new IllegalArgumentException("invalid move");
          }
        }
        break;

      case CASCADE:
        if (srcPile == PileType.CASCADE) {
          this.addCascadeToCascade(destPileNumber, pileNumber, cardIndex);
        } else {
          this.addToCascade(destPileNumber, cardInSrc);
        }
        break;

      case OPEN:
        this.open[destPileNumber] = cardInSrc;
        break;

      default:
        throw new IllegalArgumentException("shouldn't get here");
    }
    if (srcPile == PileType.OPEN) {
      this.open[pileNumber] = null;
    }
    if (srcPile == PileType.CASCADE) {
      this.removeFromCascade(pileNumber, cardInSrc, cardIndex);
    }
  }


  /**
   * Checks and adds the given card in source (open) to the given destination pile. this is a newly
   * added helper method.
   *
   * @param destPileNumber pile number of the destination pile
   * @param cardInSrc      the card to move in source
   */
  protected void addToCascade(int destPileNumber, Card cardInSrc) {
    int lastInSrcIndex = cardInSrc.getCardValue().getCardIndex();
    boolean checkIndex;
    Card lastInDest =
        this.cascade.get(destPileNumber).get(this.cascade.get(destPileNumber).size() - 1);
    boolean compareColors = lastInDest.getSuitSymbol().getColor()
        .equals(cardInSrc.getSuitSymbol().getColor());
    checkIndex = (lastInDest.getCardValue().getCardIndex() - 1 == lastInSrcIndex);
    compareColors = !compareColors;
    if (checkIndex && compareColors) {
      this.cascade.get(destPileNumber).add(cardInSrc);
    } else {
      throw new IllegalArgumentException("invalid move");
    }
  }

  /**
   * Checks and adds the given card in the given source cascade pile to the given destination
   * cascade pile. this is a newly added helper method.
   *
   * @param destPileNumber pile number of the destination pile
   * @param pileNumber     pile number of the source pile
   * @param cardIndex      card index of the card to move
   */
  protected abstract void addCascadeToCascade(int destPileNumber, int pileNumber,
      int cardIndex);

  /**
   * Remove the given card in source from the source pile.
   *
   * @param pileNumber pile number of the source pile
   * @param cardInSrc  the card to move in source
   * @param cardIndex  the index of the card to move
   */
  protected abstract void removeFromCascade(int pileNumber, Card cardInSrc, int cardIndex);

  /**
   * Checks the exceptions of source pile for move method. (this method was changed from last hw as
   * checking if the card to be moved is the last in the pile for cascade piles now only applies to
   * single moves, therefore I moved that check into single move's helper method. As for checking
   * last card to move from an open pile, it stays the same in the move method.
   *
   * @param pileNumber      pile number of source
   * @param numSrcPiles     number of source piles total
   * @param numCardsSrcPile the number of the last card (size - 1) in the source pile
   */
  protected void exceptionsForMove(int pileNumber, int numSrcPiles, int numCardsSrcPile) {
    // test invalid pile number
    if (pileNumber >= numSrcPiles || pileNumber < 0) {
      throw new IllegalArgumentException("invalid pile number");
    }

    // check if source does not contain cards
    if (numCardsSrcPile == 0) {
      throw new IllegalArgumentException("source cannot be empty");
    }
  }


  /**
   * check if card is the last in the pile (the only movable card). This check does not apply to
   * multi move from cascade to cascade.
   *
   * @param cardIndex     the index of the card to move
   * @param numCardsInSrc pile number of the source pile
   */
  protected void checkMoveLastCard(int cardIndex, int numCardsInSrc) {
    if (cardIndex != numCardsInSrc - 1) {
      throw new IllegalArgumentException("source card is not the last in the pile");
    }
  }

  @Override
  public void move(PileType source, int pileNumber, int cardIndex, PileType destination,
      int destPileNumber) throws IllegalArgumentException, IllegalStateException {
    this.checkStarted();
    switch (source) {

      case FOUNDATION:
        throw new IllegalArgumentException("cannot move from foundation");

      case OPEN:
        this.exceptionsForMove(pileNumber, this.getNumOpenPiles(),
            this.getNumCardsInOpenPile(pileNumber));

        this.checkMoveLastCard(cardIndex, this.getNumCardsInOpenPile(pileNumber));

        switch (destination) {
          case OPEN:
            this.checkInvalidDestPileNum(destPileNumber, this.getNumOpenPiles());
            this.checkDestOpenPileFull(destPileNumber);
            break;

          case CASCADE:
            this.checkInvalidDestPileNum(destPileNumber, this.getNumCascadePiles());
            break;

          case FOUNDATION:
            this.checkInvalidDestPileNum(destPileNumber, 4);
            break;

          default:
            throw new IllegalArgumentException("should not get here");
        }
        this.addAndRemove(destination, destPileNumber, this.getOpenCardAt(pileNumber),
            PileType.OPEN, pileNumber, cardIndex);
        break;

      case CASCADE:
        this.exceptionsForMove(pileNumber, this.getNumCascadePiles(),
            this.getNumCardsInCascadePile(pileNumber));

        if (destination == PileType.OPEN) {
          this.checkInvalidDestPileNum(destPileNumber, this.getNumOpenPiles());
          this.checkDestOpenPileFull(destPileNumber);
          // check if card is the last in the pile (the only movable)
          this.checkMoveLastCard(cardIndex, this.getNumCardsInCascadePile(pileNumber));

        }
        if (destination == PileType.CASCADE) {
          this.checkInvalidDestPileNum(destPileNumber, this.getNumCascadePiles());
        }
        if (destination == PileType.FOUNDATION) {
          this.checkInvalidDestPileNum(destPileNumber, 4);
          this.checkMoveLastCard(cardIndex, this.getNumCardsInCascadePile(pileNumber));

        }

        this.addAndRemove(destination, destPileNumber,
            this.getCascadeCardAt(pileNumber, cardIndex), PileType.CASCADE, pileNumber, cardIndex);
        break;

      default:
        throw new IllegalArgumentException("should not get here");
    }

  }


  @Override
  public boolean isGameOver() {
    this.checkStarted();
    return foundation.get(0).size() == 13
        && foundation.get(1).size() == 13
        && foundation.get(2).size() == 13
        && foundation.get(3).size() == 13;
  }

  @Override
  public int getNumCardsInFoundationPile(int index)
      throws IllegalArgumentException, IllegalStateException {
    this.checkStarted();
    if (index < 0 || index >= this.foundation.size()) {
      throw new IllegalArgumentException("index is invalid");
    }
    return this.foundation.get(index).size();
  }

  @Override
  public int getNumCascadePiles() {
    if (!this.started) {
      return -1;
    } else {
      return this.cascade.size();
    }
  }

  @Override
  public int getNumCardsInCascadePile(int index)
      throws IllegalArgumentException, IllegalStateException {
    if (!this.started) {
      throw new IllegalStateException("game has not yet started");
    }
    if (index < 0 || index >= this.cascade.size()) {
      throw new IllegalArgumentException("index is invalid");
    }
    return this.cascade.get(index).size();
  }

  @Override
  public int getNumCardsInOpenPile(int index)
      throws IllegalArgumentException, IllegalStateException {
    if (!this.started) {
      throw new IllegalStateException("game has not yet started");
    }
    if (index < 0 || index >= this.open.length) {
      throw new IllegalArgumentException("index is invalid");
    }
    if (this.open[index] == null) {
      return 0;
    } else {
      return 1;
    }
  }


  @Override
  public int getNumOpenPiles() {
    if (!this.started) {
      return -1;
    } else {
      return this.open.length;
    }
  }


  @Override
  public Card getFoundationCardAt(int pileIndex, int cardIndex)
      throws IllegalArgumentException, IllegalStateException {
    if (!this.started) {
      throw new IllegalStateException("game has not yet started");
    }
    if (pileIndex < 0
        || pileIndex >= this.foundation.size()
        || cardIndex < 0
        || cardIndex >= this.foundation.get(pileIndex).size()) {
      throw new IllegalArgumentException("index is invalid");
    }
    return this.foundation.get(pileIndex).get(cardIndex);
  }


  @Override
  public Card getCascadeCardAt(int pileIndex, int cardIndex)
      throws IllegalArgumentException, IllegalStateException {
    if (!this.started) {
      throw new IllegalStateException("game has not yet started");
    }
    if (pileIndex < 0
        || pileIndex >= this.cascade.size()
        || cardIndex < 0
        || cardIndex >= this.cascade.get(pileIndex).size()) {
      throw new IllegalArgumentException("index is invalid");
    }
    return this.cascade.get(pileIndex).get(cardIndex);
  }

  @Override
  public Card getOpenCardAt(int pileIndex)
      throws IllegalArgumentException, IllegalStateException {
    if (!this.started) {
      throw new IllegalStateException("game has not yet started");
    }
    if (pileIndex < 0 || pileIndex >= this.open.length) {
      throw new IllegalArgumentException("index is invalid");
    }
    if (this.open[pileIndex] == null) {
      return null;
    } else {
      return this.open[pileIndex];
    }
  }
}
