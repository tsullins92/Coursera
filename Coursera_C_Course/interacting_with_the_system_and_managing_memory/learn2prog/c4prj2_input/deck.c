#include <stdio.h>
#include <stdlib.h>
#include <assert.h>
#include "deck.h"

void print_hand(deck_t * hand){
  for(int i=0;i< hand->n_cards; ++i){
    print_card(*hand->cards[i]);
    printf(" ");
  }
}

int deck_contains(deck_t * d, card_t c) {
  for(int i=0;i< d->n_cards; ++i){
    card_t dCard = *d->cards[i];
    if(dCard.value == c.value && dCard.suit == c.suit){
      return 1;
    }
  }
  return 0;
}

void shuffle(deck_t * d){
  for(int i=0;i< d->n_cards; ++i){
    int cardIndex = random()%d->n_cards;
    card_t * tCardPtr = d->cards[i];
    d->cards[i]=d->cards[cardIndex];
    d->cards[cardIndex]=tCardPtr;
  }
}

void assert_full_deck(deck_t * d) {
  for(int i=0;i<52;++i){
    card_t tempCard = card_from_num(i);
    assert(deck_contains(d,tempCard)==1);
  }
}
// Add the particular card to the given deck (which will
// involve reallocing the array of cards in that deck).
void add_card_to(deck_t * deck, card_t c){
  size_t deckSz = deck -> n_cards;
  deck -> cards = realloc(deck -> cards, (deckSz+1) * sizeof(*deck -> cards));
  deck -> cards[deckSz] = malloc(sizeof(*deck -> cards[deckSz]));
  deck -> cards[deckSz] -> value = c.value;
  deck -> cards[deckSz] -> suit = c.suit;
  ++deck -> n_cards;
}
// Add a card whose value and suit are both 0, and return a pointer
// to it in the deck.
// This will add an invalid card to use as a placeholder
// for an unknown card.
card_t * add_empty_card(deck_t * deck){
  size_t deckSz = deck -> n_cards;
  deck -> cards = realloc(deck -> cards, (deckSz+1) * sizeof(*deck -> cards));
  deck -> cards[deckSz] = malloc(sizeof(*deck -> cards[deckSz]));
  deck -> cards[deckSz] -> value = 0;
  deck -> cards[deckSz] -> suit = SPADES;
  ++deck -> n_cards;
  return deck -> cards[deckSz];
}
// Create a deck that is full EXCEPT for all the cards
// that appear in excluded_cards.
deck_t * make_deck_exclude(deck_t * excluded_cards){
  deck_t * newDeck = malloc(sizeof(*newDeck));
  newDeck -> cards = malloc(sizeof(*newDeck -> cards));
  newDeck -> n_cards = 0;
  for(int i=0;i<52;++i){
    card_t tempCard = card_from_num(i);
    if(deck_contains(excluded_cards, tempCard)){continue;}
    else{
      size_t numCards = newDeck -> n_cards;
      newDeck -> cards = realloc(newDeck -> cards, (numCards + 1) * sizeof(*newDeck -> cards));
      newDeck -> cards[numCards] = malloc(sizeof(*newDeck -> cards[numCards]));
      newDeck -> cards[numCards] -> value = tempCard.value;
      newDeck -> cards[numCards] -> suit = tempCard.suit;
      ++newDeck -> n_cards;
    }
  }
  return newDeck;
}
// This function takes an array of hands (remember
// that we use deck_t to represent a hand).  It then builds
// the deck of cards that remain after those cards have
// been removed from a full deck.
deck_t * build_remaining_deck(deck_t ** hands, size_t n_hands){
  deck_t * excludeDeck = malloc(sizeof(*excludeDeck));
  excludeDeck -> cards = malloc(sizeof(*excludeDeck->cards));
  excludeDeck -> n_cards = 0;
  for(size_t i=0; i<n_hands; ++i){
    for(size_t ii=0;ii < hands[i]->n_cards; ++ii){
      add_card_to(excludeDeck, *hands[i]->cards[ii]);
    }
  }
  deck_t * newDeck = make_deck_exclude(excludeDeck);
  return newDeck;
}
// Free the memory allocated to a deck of cards.
void free_deck(deck_t * deck){
  for(size_t i=0; i<deck->n_cards; ++i){
    free(deck -> cards[i]);
  }
  free(deck -> cards);
  free(deck);
}
