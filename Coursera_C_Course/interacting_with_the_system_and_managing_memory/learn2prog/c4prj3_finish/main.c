#include <stdio.h>
#include <stdlib.h>
#include <assert.h>
#include <ctype.h>
#include <stdbool.h>
#include "eval.h"
#include "deck.h"
#include "cards.h"
#include "future.h"
#include "input.h"



int main(int argc, char ** argv) {
  int num_trials = 0;
  //Check command line arguments
  if(argc == 1 || argc > 3){
    fprintf(stderr,"Takes two arguments -- the input file of hands to read and an optional argument for the number of simulations to perform.\n");
    return EXIT_FAILURE;
  }else if(argc == 2){
    num_trials = 10000;
  }else if(argc == 3){
    num_trials = atoi(argv[2]);
  }

  FILE * f = fopen(argv[1], "r"); //Open file
  if (f == NULL) {
    perror("Could not open input file");
    exit(EXIT_FAILURE);
  }

  size_t n_hands = 0;
  future_cards_t * fc = malloc(sizeof(*fc));
  fc -> decks = malloc(sizeof(*fc->decks));
  fc -> n_decks = 0;
  deck_t ** decks = read_input(f, &n_hands, fc); //Read hands from file

  if (fclose(f) != 0) { //Close file
    perror("Failed to close the input file!");
    exit(EXIT_FAILURE);
  }
  deck_t * remaining_deck = build_remaining_deck(decks, n_hands);
  int deck_win_array[n_hands+1];
  for(size_t i=0; i < n_hands+1; ++i){
    deck_win_array[i]=0;
  }
  

  //Evaluate each hand and compare values. Do this num_trials times
  for(size_t i=0; i<num_trials; ++i){
    if(i%4){
      shuffle(remaining_deck); //Shuffle remaining deck
    }
    future_cards_from_deck(remaining_deck, fc);//Assign unknown cards from the shuffled deck
    size_t win_index = 0;
    bool is_tie = false;
    for(size_t ii=1; ii<n_hands; ++ii){//find out which hand wins or if there is a tie
      int compare_result = compare_hands(decks[ii], decks[win_index]);
      if(compare_result > 0){
	win_index = ii;
	is_tie = false;
      } else if(compare_result == 0){
	is_tie = true;
      }
    }
    if(!is_tie){
      ++deck_win_array[win_index];
    } else{
      ++deck_win_array[n_hands];
    }
  }

  //Loop through hand deck_win_array and print how many times each hand won
  for(size_t i=0;i<n_hands;++i){
    printf("Hand %zu won %u / %u times (%.2f%%)\n", i, deck_win_array[i], num_trials, ((double)deck_win_array[i]/num_trials)*100);
  }
  printf("And there were %u ties\n", deck_win_array[n_hands]);
  //Free all decks and future cards
  for(size_t i=0; i< n_hands; ++i){free_deck(decks[i]);}
  for(size_t i=0; i<fc->n_decks; ++i){

      for(size_t ii=0; ii<fc->decks[i].n_cards; ++ii){
	//printf("freeing cards\n");
	//free(fc->decks[i].cards[ii]);
      
      //free_deck(&fc->decks[i]);
    }
    free(fc->decks[i].cards);
  }
  free(fc->decks);
  free(fc);
  free(decks);
  free_deck(remaining_deck);
  
  //YOUR CODE GOES HERE
  return EXIT_SUCCESS;
}
