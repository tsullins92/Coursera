#include <stdio.h>
#include <stdlib.h>

size_t maxSeq(int * array, size_t n){
  int prevNum;
  int highestSeqLength = 0;
  int currentSeqLength = 0;
  if(n==0){return 0;}
  if(n==1){return 1;}
  for(int i=0;i<n;++i){
    if(i==0){
      prevNum = array[i];
      ++currentSeqLength;
      ++highestSeqLength;
    } else{
      if(array[i]>prevNum){
	prevNum = array[i];
	++currentSeqLength;
        if(currentSeqLength >= highestSeqLength){++highestSeqLength;}
      }else{
	prevNum = array[i];
	currentSeqLength = 0;
      }
    }
  }
  return highestSeqLength;
}
