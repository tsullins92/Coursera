#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "eval.h"
#include "deck.h"
#include "cards.h"




int main(int argc, char ** argv) {

  deck_t * testDeck = malloc(sizeof(*testDeck));
  testDeck -> cards = malloc(sizeof(*testDeck->cards));
  testDeck->n_cards = 0;
  add_card_to(testDeck, card_from_num(0));
  add_card_to(testDeck, card_from_num(51));
  add_card_to(testDeck, card_from_num(32));
  add_card_to(testDeck, card_from_num(45));
  add_card_to(testDeck, card_from_num(5));
  add_card_to(testDeck, card_from_num(46));

  deck_t * testDeck1 = malloc(sizeof(*testDeck1));
  testDeck1->cards = malloc(sizeof(*testDeck1->cards));
  testDeck1->n_cards = 0;
  add_card_to(testDeck1, card_from_num(3));
  add_card_to(testDeck1, card_from_num(41));
  add_card_to(testDeck1, card_from_num(39));
  add_card_to(testDeck1, card_from_num(27));
  add_card_to(testDeck1, card_from_num(35));
  add_card_to(testDeck1, card_from_num(13));
  add_card_to(testDeck1, card_from_num(14));

  deck_t * excludeTestDeck = malloc(sizeof(*excludeTestDeck));
  excludeTestDeck = make_deck_exclude(testDeck);

  deck_t * testDecks[2] = {
    testDeck,
    testDeck1
  };
  deck_t * remainingDeck = build_remaining_deck(testDecks, 2);

  unsigned * match_counts = get_match_counts(testDeck1);

  free_deck(testDeck);
  free_deck(testDeck1);
  free_deck(excludeTestDeck);
  free_deck(remainingDeck);
  free(match_counts);

  return EXIT_SUCCESS;
}
