#include <stdio.h>
#include <assert.h>
#include <stdlib.h>
#include "cards.h"


void assert_card_valid(card_t c) {
	assert(c.value>=2 && c.value<=14 && c.suit>=SPADES && c.suit<=CLUBS);
}

const char * ranking_to_string(hand_ranking_t r) {
  if(r == STRAIGHT_FLUSH){return "STRAIGHT_FLUSH";}
  if(r == FOUR_OF_A_KIND){return "FOUR_OF_A_KIND";}
  if(r == FULL_HOUSE){return "FULL_HOUSE";}
  if(r == FLUSH){return "FLUSH";}
  if(r == STRAIGHT){return "STRAIGHT";}
  if(r == THREE_OF_A_KIND){return "THREE_OF_A_KIND";}
  if(r == TWO_PAIR){return "TWO_PAIR";}
  if(r == PAIR){return "PAIR";}
  if(r == NOTHING){return "NOTHING";}
  return "";
}

char value_letter(card_t c) {
	if(c.value >= 2 && c.value <= 9) return '0' + c.value; 
	if(c.value == 10) return '0';
	if(c.value == VALUE_JACK) return 'J';
	if(c.value == VALUE_QUEEN) return 'Q';
	if(c.value == VALUE_KING) return 'K';
	if(c.value == VALUE_ACE) return 'A';
	return 'x';
}


char suit_letter(card_t c) {
	if(c.suit == SPADES) return 's';
	if(c.suit == HEARTS) return 'h';
	if(c.suit == DIAMONDS) return 'd';
	if(c.suit == CLUBS) return 'c';	
  return 'x';
  
}

void print_card(card_t c) {
	printf("%c%c",value_letter(c),suit_letter(c));
}

card_t card_from_letters(char value_let, char suit_let) {
  card_t temp;
	if(value_let == '2') temp.value = 2;
	else if(value_let == '3') temp.value = 3;
	else if(value_let == '4') temp.value = 4;
	else if(value_let == '5') temp.value = 5;
	else if(value_let == '6') temp.value = 6;
	else if(value_let == '7') temp.value = 7;
	else if(value_let == '8') temp.value = 8;
	else if(value_let == '9') temp.value = 9;
	else if(value_let == '0') temp.value = 10;
	else if(value_let == 'J') temp.value = VALUE_JACK;
	else if(value_let == 'Q') temp.value = VALUE_QUEEN;
	else if(value_let == 'K') temp.value = VALUE_KING;
	else if(value_let == 'A') temp.value = VALUE_ACE;  
	else {
		printf("value_let: %c is not a valid card_t.value\n",value_let);
		exit(EXIT_FAILURE);
	}
	if(suit_let == 's') temp.suit = SPADES;
	else if(suit_let == 'h') temp.suit = HEARTS;
	else if(suit_let == 'd') temp.suit = DIAMONDS;
	else if(suit_let == 'c') temp.suit = CLUBS;		
	else{
		printf("suit_let: %c is not a valid card_t.suit\n",suit_let);
		exit(EXIT_FAILURE);
	}
  return temp;
}

card_t card_from_num(unsigned c) {
	assert(c>=0 && c<=51);
  card_t temp;
  temp.suit = c/13;
  if((c%13 >= 0 && c %13 <= 12)){temp.value = c%13 + 2;}
  return temp;
}
