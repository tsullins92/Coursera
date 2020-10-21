#include "input.h"
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>

deck_t * hand_from_string(const char * str, future_cards_t * fc){
  deck_t * stringHand = malloc(sizeof(*stringHand));
  stringHand -> cards = malloc(sizeof(*stringHand -> cards));
  stringHand -> n_cards = 0;
  char cardStr[4];
  cardStr[3] = '\0';
  int strSz = 0;
  for (int i = 0; i < strlen(str); i++){
    if(isspace(str[i])){continue;}
    else{
      ++strSz;
      cardStr[strSz-1] = str[i];
      if(isspace(str[i+1])||str[i+1]=='\n'){
	char value = cardStr[0];
	char suit[strSz-1];
	for(int ii=1; ii<strSz;++ii){suit[ii-1]=cardStr[ii];}
	suit[strSz-1]='\0';
	if(cardStr[0]=='?'){
	  int suitInt = atoi(suit);
	  card_t * emptyCard = add_empty_card(stringHand);
	  add_future_card(fc, suitInt, emptyCard);
	} else{
	  card_t newCard = card_from_letters(value, suit[0]);
	  add_card_to(stringHand, newCard);
	}
	strSz = 0;
      }
    }
  }
  if(stringHand -> n_cards < 5){
    fprintf(stderr,"Poker hand must have at least 5 cards.\n");
    exit(EXIT_FAILURE);
  }
  return stringHand;
}


deck_t ** read_input(FILE * f, size_t * n_hands, future_cards_t * fc){
  deck_t ** inputDecks = malloc(sizeof(*inputDecks));
  char * line = NULL;
  size_t sz = 0;
  int lineIndex = 0;

  while (getline(&line,&sz, f) >= 0) {
    inputDecks = realloc(inputDecks, (lineIndex + 1) * sizeof(*inputDecks));
    inputDecks[lineIndex] = hand_from_string(line, fc);
    free(line);
    line = NULL;
    ++lineIndex;
    ++(*n_hands);
  }
  free(line);
  return inputDecks;
}
