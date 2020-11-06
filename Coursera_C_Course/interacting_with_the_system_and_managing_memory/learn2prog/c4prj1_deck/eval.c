#include "eval.h"
#include <stdio.h>
#include <stdlib.h>
#include <assert.h>

// Comparison function to pass to qsort. sorted in descending order so returns:
// something < 0  if card1 > card2
// 0              if card1 == card2
// something > 0  if card1 < card2
int card_ptr_comp(const void * vp1, const void * vp2) {
  const card_t * const * cp1 = vp1;
  const card_t * const * cp2 = vp2;
  if((*cp1)->value>(*cp2)->value){return -1;}
  else if((*cp1)->value<(*cp2)->value){return 1;}
  else {
    if((*cp1)->suit<(*cp2)->suit){return 1;}
    if((*cp1)->suit>(*cp2)->suit){return -1;}
  }
  return 0;
}

// Checks if there is a flush and then returns the suit of the flush.
// If there is no flush, returns NUM_SUITS
suit_t flush_suit(deck_t * hand) {
  int n_spades=0;
  int n_hearts=0;
  int n_diamonds=0;
  int n_clubs=0;
  for(int i=0;i<hand->n_cards;++i){
    if(hand->cards[i]->suit == SPADES){++n_spades;}
    if(hand->cards[i]->suit == HEARTS){++n_hearts;}
    if(hand->cards[i]->suit == DIAMONDS){++n_diamonds;}
    if(hand->cards[i]->suit == CLUBS){++n_clubs;}
  }
  if(n_spades>=5){return SPADES;}
  if(n_hearts>=5){return HEARTS;}
  if(n_diamonds>=5){return DIAMONDS;}
  if(n_clubs>=5){return CLUBS;}
  return NUM_SUITS;
}

// Return value of largest integer in array
unsigned get_largest_element(unsigned * arr, size_t n) {
  unsigned largest = 0;
  for (size_t i=0; i<n; ++i){
    if(arr[i]>largest){largest = arr[i];}
  }
  return largest;
}

// Return the index of n_of_akind in match_counts
size_t get_match_index(unsigned * match_counts, size_t n,unsigned n_of_akind){
  for (size_t i=0; i<n; ++i){
    if(match_counts[i]==n_of_akind){return i;}
  }
  exit(EXIT_FAILURE);
}

// Returns index of a second pair. Returns -1 if no index is found
ssize_t  find_secondary_pair(deck_t * hand,
			     unsigned * match_counts,
			     size_t match_idx) {
  for (size_t i=0; i<hand->n_cards; ++i){
    if(match_counts[i]>1 && hand->cards[i]->value != hand->cards[match_idx]->value){return i;}
  }
  return -1;
}

// Check for an n length straigh at index. return 0 for no and 1 for yes
int is_n_length_straight_at(deck_t * hand, size_t index, suit_t fs, int n){
  //printf("is_n_length_straight_at");
  if(fs!=NUM_SUITS &&  hand->cards[index]->suit!=fs){return 0;}
  unsigned straight_size = 1;
  unsigned last_num = hand->cards[index]->value;
  for(int i = index+1; i < hand->n_cards; ++i){
    if(straight_size >= n){break;}
    if(last_num - hand->cards[i]->value > 1){break;}
    else if(last_num - hand->cards[i]->value == 0){}
    else{
      if(fs != NUM_SUITS){
	if(hand->cards[i]->suit != fs){}
	else {
	  ++straight_size;
	  last_num = hand->cards[i]->value;
	}
      }
      else {
	++straight_size;
	last_num = hand->cards[i]->value;
      }
    }
  }
  if(straight_size >= n){return 1;}
  return 0;
}

// Check if there is ace low straight at index return 0 for no and 1 for yes
int is_ace_low_straight_at(deck_t * hand, size_t index, suit_t fs){
  if(fs!=NUM_SUITS &&  hand->cards[index]->suit!=fs){return 0;}
  while(hand->cards[index]->value == VALUE_ACE){
    if(fs != NUM_SUITS){
      if(hand->cards[index]->suit != fs){}
      else{
	for(size_t i=0;i<hand->n_cards;++i){
	  if(hand->cards[i]->value == 5 && hand->cards[i]->suit == fs){
	    if(is_n_length_straight_at(hand, i, fs, 4)==1){return 1;}
	  }
	}
      }
    } else{
      for(size_t i=0;i<hand->n_cards;++i){
	if(hand->cards[i]->value == 5){
	  if(is_n_length_straight_at(hand, i, fs, 4)==1){return 1;}
	}
      }
    }
    ++index;
  }
  return 0;
}
// Check if there is a straight starting at index.
// return -1 if ace low straight, 1 if straight, 0 if no
int is_straight_at(deck_t * hand, size_t index, suit_t fs) {
  if(is_ace_low_straight_at(hand, index, fs)==1){return -1;}
  if(is_n_length_straight_at(hand, index, fs, 5)){return 1;}
  return 0;
}






// Build a hand starting with a match. Then fill in the remainder of
// cards from highest rank to lowest.
hand_eval_t build_hand_from_match(deck_t * hand,
				  unsigned n,
				  hand_ranking_t what,
				  size_t idx) {
  hand_eval_t ans;
  ans.ranking = what;
  for(int i = 0; i < n; ++i){
    ans.cards[i] = hand->cards[i+idx];
  }
  int hand_size = n;
  int temp_index = 0;
  while(hand_size < 5){
    if(temp_index < idx || temp_index >= idx+n){
      ans.cards[hand_size] = hand->cards[temp_index];
      ++hand_size;
    }
    ++temp_index;
  }
  return ans;
}

// return a positive number if hand 1 is better,
// 0 if the hands tie,
// and a negative number if hand 2 is better.
// Uses value to determine winner of ties.
int compare_hands(deck_t * hand1, deck_t * hand2) {
  qsort(hand1->cards, hand1->n_cards, sizeof(const card_t *), card_ptr_comp);
  qsort(hand2->cards, hand2->n_cards, sizeof(const card_t *), card_ptr_comp);
  hand_eval_t hand1_eval = evaluate_hand(hand1);
  hand_eval_t hand2_eval = evaluate_hand(hand2);
  if(hand1_eval.ranking != hand2_eval.ranking){
    return hand2_eval.ranking - hand1_eval.ranking;
  } else{
    for(int i = 0; i<5; ++i){
      if(hand1_eval.cards[i]->value > hand2_eval.cards[i]->value){return 1;}
      else if(hand1_eval.cards[i]->value < hand2_eval.cards[i]->value){return -1;}
    }
  }
  return 0;
}



// Given a hand (deck_t) of cards, this function
// allocates an array of unsigned ints with as
// many elements as there are cards in the hand.
// It then fills in this array with
// the "match counts" of the corresponding cards.
unsigned * get_match_counts(deck_t * hand){
  unsigned * match_counts = malloc((hand -> n_cards) * sizeof(*match_counts));
  for(size_t i = 0; i < hand->n_cards; ++i){
    match_counts[i] = 1;
    for(size_t ii = 0; ii < hand->n_cards; ++ii){
      if(i == ii){continue;}
      else if(hand->cards[i]->value == hand->cards[ii]->value){++match_counts[i];}
    }
  }
  return match_counts;
}


// We provide the below functions.  You do NOT need to modify them
// In fact, you should not modify them!


//This function copies a straight starting at index "ind" from deck "from".
//This copies "count" cards (typically 5).
//into the card array "to"
//if "fs" is NUM_SUITS, then suits are ignored.
//if "fs" is any other value, a straight flush (of that suit) is copied.
void copy_straight(card_t ** to, deck_t *from, size_t ind, suit_t fs, size_t count) {
  assert(fs == NUM_SUITS || from->cards[ind]->suit == fs);
  unsigned nextv = from->cards[ind]->value;
  size_t to_ind = 0;
  while (count > 0) {
    assert(ind < from->n_cards);
    assert(nextv >= 2);
    assert(to_ind <5);
    if (from->cards[ind]->value == nextv &&
	(fs == NUM_SUITS || from->cards[ind]->suit == fs)){
      to[to_ind] = from->cards[ind];
      to_ind++;
      count--;
      nextv--;
    }
    ind++;
  }
}


//This looks for a straight (or straight flush if "fs" is not NUM_SUITS)
// in "hand".  It calls the student's is_straight_at for each possible
// index to do the work of detecting the straight.
// If one is found, copy_straight is used to copy the cards into
// "ans".
int find_straight(deck_t * hand, suit_t fs, hand_eval_t * ans) {
  if (hand->n_cards < 5){
    return 0;
  }
  for(size_t i = 0; i <= hand->n_cards -5; i++) {
    int x = is_straight_at(hand, i, fs);
    if (x != 0){
      if (x < 0) { //ace low straight
	assert(hand->cards[i]->value == VALUE_ACE &&
	       (fs == NUM_SUITS || hand->cards[i]->suit == fs));
	ans->cards[4] = hand->cards[i];
	size_t cpind = i+1;
	while(hand->cards[cpind]->value != 5 ||
	      !(fs==NUM_SUITS || hand->cards[cpind]->suit ==fs)){
	  cpind++;
	  assert(cpind < hand->n_cards);
	}
	copy_straight(ans->cards, hand, cpind, fs,4) ;
      }
      else {
	copy_straight(ans->cards, hand, i, fs,5);
      }
      return 1;
    }
  }
  return 0;
}

//This function puts all the hand evaluation logic together.
//This function is longer than we generally like to make functions,
//and is thus not so great for readability :(
hand_eval_t evaluate_hand(deck_t * hand) {
  suit_t fs = flush_suit(hand);
  hand_eval_t ans;
  if (fs != NUM_SUITS) {
    if(find_straight(hand, fs, &ans)) {
      ans.ranking = STRAIGHT_FLUSH;
      return ans;
    }
  }

  unsigned * match_counts = get_match_counts(hand);
  unsigned n_of_a_kind = get_largest_element(match_counts, hand->n_cards);
  assert(n_of_a_kind <= 4);
  size_t match_idx = get_match_index(match_counts, hand->n_cards, n_of_a_kind);
  ssize_t other_pair_idx = find_secondary_pair(hand, match_counts, match_idx);
  free(match_counts);
  if (n_of_a_kind == 4) { //4 of a kind
    return build_hand_from_match(hand, 4, FOUR_OF_A_KIND, match_idx);
  }
  else if (n_of_a_kind == 3 && other_pair_idx >= 0) {     //full house
    ans = build_hand_from_match(hand, 3, FULL_HOUSE, match_idx);
    ans.cards[3] = hand->cards[other_pair_idx];
    ans.cards[4] = hand->cards[other_pair_idx+1];
    return ans;
  }
  else if(fs != NUM_SUITS) { //flush
    ans.ranking = FLUSH;
    size_t copy_idx = 0;
    for(size_t i = 0; i < hand->n_cards;i++) {
      if (hand->cards[i]->suit == fs){
	ans.cards[copy_idx] = hand->cards[i];
	copy_idx++;
	if (copy_idx >=5){
	  break;
	}
      }
    }
    return ans;
  }
  else if(find_straight(hand,NUM_SUITS, &ans)) {     //straight
    ans.ranking = STRAIGHT;
    return ans;
  }
  else if (n_of_a_kind == 3) { //3 of a kind
    return build_hand_from_match(hand, 3, THREE_OF_A_KIND, match_idx);
  }
  else if (other_pair_idx >=0) {     //two pair
    assert(n_of_a_kind ==2);
    ans = build_hand_from_match(hand, 2, TWO_PAIR, match_idx);
    ans.cards[2] = hand->cards[other_pair_idx];
    ans.cards[3] = hand->cards[other_pair_idx + 1];
    if (match_idx > 0) {
      ans.cards[4] = hand->cards[0];
    }
    else if (other_pair_idx > 2) {  //if match_idx is 0, first pair is in 01
      //if other_pair_idx > 2, then, e.g. A A K Q Q
      ans.cards[4] = hand->cards[2];
    }
    else {       //e.g., A A K K Q
      ans.cards[4] = hand->cards[4];
    }
    return ans;
  }
  else if (n_of_a_kind == 2) {
    return build_hand_from_match(hand, 2, PAIR, match_idx);
  }
  return build_hand_from_match(hand, 0, NOTHING, 0);
}
