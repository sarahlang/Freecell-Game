package cs3500.freecell.model.hw02;

import cs3500.freecell.model.hw04.AbstractFreecellModel;
import cs3500.freecell.model.Card;

/**
 * A model that represents a simple game of freecell, where players can only move one card at a
 * time. The only two methods that SimpleFreecellModel differs from MultiCardMoveModel is moving
 * from cascade source pile to cascade destination pile and removing from cascade source methods.
 */
public class SimpleFreecellModel extends AbstractFreecellModel {

  @Override
  protected void addCascadeToCascade(int destPileNumber, int pileNumber, int cardIndex) {
    this.checkMoveLastCard(cardIndex, this.getNumCardsInCascadePile(pileNumber));
    this.addToCascade(destPileNumber, this.getCascadeCardAt(pileNumber, cardIndex));
  }

  @Override
  protected void removeFromCascade(int pileNumber, Card cardInSrc, int cardIndex) {
    this.cascade.get(pileNumber).remove(cardInSrc);
  }
}