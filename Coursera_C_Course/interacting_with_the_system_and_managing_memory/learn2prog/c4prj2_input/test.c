#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "eval.h"
#include "deck.h"
#include "cards.h"
#include "future.h"
#include "input.h"



int main(int argc, char ** argv) {

  if(argc != 2){
    fprintf(stderr,"Only takes one argument - a file of card hands to read.\n");
    return EXIT_FAILURE;
  }
  FILE * f = fopen(argv[1], "r");
  if (f == NULL) {
    perror("Could not open value file");
    exit(EXIT_FAILURE);
  }

  size_t n_hands = 0;
  future_cards_t * fc = malloc(sizeof(*fc));
  fc -> decks = malloc(sizeof(*fc->decks));
  fc -> n_decks = 0;
  deck_t ** decks = read_input(f, &n_hands, fc);

  if (fclose(f) != 0) {
    perror("Failed to close the input value file!");
    exit(EXIT_FAILURE);
  }
 
  deck_t * remainingDeck = build_remaining_deck(decks, n_hands);
  future_cards_from_deck(remainingDeck, fc);
  print_hand(remainingDeck);
  printf("\n");
  for(size_t i=0; i<n_hands; ++i){
    print_hand(decks[i]);
    printf("\n");
  }
  free_deck(remainingDeck);

  for(size_t i=0; i < n_hands; ++i){free_deck(decks[i]);}
  for(size_t i=0; i<fc->n_decks; ++i){
    free(fc->decks[i].cards);
  }
  free(fc->decks);
  free(fc);
  free(decks);
  return EXIT_SUCCESS;
}
// deck_t * testDeck = malloc(sizeof(*testDeck));
// testDeck -> cards = malloc(sizeof(*testDeck->cards));
// testDeck->n_cards = 0;
// add_card_to(testDeck, card_from_num(0));
// add_card_to(testDeck, card_from_num(51));
// add_card_to(testDeck, card_from_num(32));
// add_card_to(testDeck, card_from_num(45));
// add_card_to(testDeck, card_from_num(5));
// add_card_to(testDeck, card_from_num(46));
// add_empty_card(testDeck);

// deck_t * testDeck1 = malloc(sizeof(*testDeck1));
// testDeck1->cards = malloc(sizeof(*testDeck1->cards));
// testDeck1->n_cards = 0;
// add_card_to(testDeck1, card_from_num(3));
// add_card_to(testDeck1, card_from_num(41));
// add_card_to(testDeck1, card_from_num(39));
// add_card_to(testDeck1, card_from_num(27));
// add_empty_card(testDeck);
// add_empty_card(testDeck);
// add_empty_card(testDeck);

// future_cards_t * fc = malloc(sizeof(*fc));
// fc -> n_decks = 0;
// add_future_card(fc, 2, testDeck -> cards[6]);

// deck_t * excludeTestDeck = malloc(sizeof(*excludeTestDeck));
// excludeTestDeck = make_deck_exclude(testDeck);

// add_future_card(fc, 0, testDeck1 -> cards[4]);
// add_future_card(fc, 1, testDeck1 -> cards[5]);
// add_future_card(fc, 2, testDeck1 -> cards[6]);

// deck_t * testDecks[2] = {
//   testDeck,
//       testDeck1
// };
// deck_t * remainingDeck = build_remaining_deck(testDecks, 2);

// unsigned * match_counts = get_match_counts(testDeck1);

// free_deck(testDeck);
// free_deck(testDeck1);
// free_deck(excludeTestDeck);
// free_deck(remainingDeck);
// free(match_counts);
// for(size_t i=0; i<fc->n_decks; ++i){
//   for(size_t ii=0; ii<fc->decks[i].n_cards; ++ii){
//     free(fc->decks[i].cards[ii]);
//   }
//   free(fc->decks[i].cards);
//   free(fc->decks);
// }
// free(fc);
