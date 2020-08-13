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
