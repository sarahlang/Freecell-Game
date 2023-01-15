package cs3500.freecell.model.hw04;

import cs3500.freecell.model.Card;
import cs3500.freecell.model.hw02.SimpleFreecellModel;
import java.util.ArrayList;

/**
 * A model for a freecell game where multiple cards can be moved from a cascade pile to another
 * cascade pile given the right conditions. Otherwise, every function is the same as the simple
 * freecell game.
 */
public class MultiCardMoveModel extends SimpleFreecellModel {


  /**
   * Calculates the maximum number of cards (that form a valid build) that can be moved from a
   * cascade pile according to the number of empty open piles and empty cascade piles. The formula
   * is (N + 1) * 2^K, where N represents empty open piles and K represents empty cascade piles.
   *
   * @return the maximum number of cards that can be moved
   */
  private int maxNumCardsToMove() {
    int mtOpenCounter = 0;
    for (int i = 0; i < this.getNumOpenPiles(); i++) {
      if (this.getNumCardsInOpenPile(i) == 0) {
        mtOpenCounter++;
      }
    }
    int mtCascadeCounter = 0;
    for (ArrayList<Card> cards : this.cascade) {
      if (cards.isEmpty()) {
        mtCascadeCounter += 1;
      }
    }
    return (int) ((mtOpenCounter + 1) * Math.pow(2, mtCascadeCounter));
  }

  @Override
  protected void addCascadeToCascade(int destPileNumber, int pileNumber, int cardIndex) {
    // 1. check that the number of cards to move are not more than the maximum, else throw exception
    if ((this.getNumCardsInCascadePile(pileNumber) - cardIndex) > this.maxNumCardsToMove()) {
      throw new IllegalArgumentException("Cannot move this many cards.");
    }

    boolean checkIndex;
    for (int i = cardIndex; i < this.getNumCardsInCascadePile(pileNumber); i++) {
      Card curCard;
      Card nextCard;
      // 3. if this is the last card in source, check if the cardInSrc forms a valid build with
      // destination pile
      if (i == this.getNumCardsInCascadePile(pileNumber) - 1) {
        // check if there are cards in destination pile. if so, add cards to move to destination
        // pile
        if (this.getNumCardsInCascadePile(destPileNumber) == 0) {
          break;
        } else {
          // if there is no card in destination pile, move the cards to move there
          curCard = this.getCascadeCardAt(pileNumber, cardIndex);
          nextCard = this.cascade.get(destPileNumber)
              .get(this.cascade.get(destPileNumber).size() - 1);
          checkIndex = nextCard.getCardValue().getCardIndex() - 1 == curCard.getCardValue()
              .getCardIndex();
        }
      } else {
        // 2. check that the cards to move form a valid build (alternating colors, descending
        // values)
        curCard = this.getCascadeCardAt(pileNumber, i);
        nextCard = this.getCascadeCardAt(pileNumber, i + 1);
        checkIndex = curCard.getCardValue().getCardIndex() - 1 == nextCard.getCardValue()
            .getCardIndex();
      }
      boolean compareColors = curCard.getSuitSymbol().getColor()
          .equals(nextCard.getSuitSymbol().getColor());
      compareColors = !compareColors;
      if (!checkIndex || !compareColors) {
        throw new IllegalArgumentException(
            "Cards to move do not form a valid build or invalid move.");
      }
    }
    this.cascade.get(destPileNumber).addAll(this.cardsToMove(cardIndex, pileNumber));
  }

  /**
   * Gather the list of cards going down from the card index of the card to move.
   *
   * @param cardIndex  the index of the card to move
   * @param pileNumber the pile number of the source pile
   * @return a list of cards starting from the cardIndex to move to destination
   */
  private ArrayList<Card> cardsToMove(int cardIndex, int pileNumber) {
    ArrayList<Card> cardsToAdd = new ArrayList<Card>();
    for (int i = cardIndex; i < this.getNumCardsInCascadePile(pileNumber); i++) {
      cardsToAdd.add(this.getCascadeCardAt(pileNumber, i));
    }
    return cardsToAdd;
  }

  @Override
  protected void removeFromCascade(int pileNumber, Card cardInSrc, int cardIndex) {
    this.cascade.get(pileNumber).removeAll(this.cardsToMove(cardIndex, pileNumber));
  }

}
