#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "counts.h"

counts_t * createCounts(void) {
  counts_t * counts = malloc(sizeof(*counts));
  counts -> countArray = malloc(sizeof(*counts->countArray));
  counts -> numCounts = 0;
  counts -> numUnknowns = 0;
  return counts;
  //WRITE ME
}

//searches counts for name. Returns -1 if it finds nothing, or the index of the match
int findName(counts_t * c, const char * name){
  for(size_t i=0; i < c->numCounts; ++i){
    if(strcmp(c -> countArray[i] -> value, name) == 0){
      return i;
    }
  }
  return -1;
}

//create a new one_count_t for counts_t -> countArray
//takes the parent counts structure, a name to add, the index within
//counts_t -> countArray to add it, and the new size of counts_t -> countArray
void addCountArray(counts_t * c, const char * name, int countIndex, size_t countsSz){
  const char * eolPtr = strchr(name, '\0');
  int nameSz = eolPtr - name + 1;
  c -> countArray = realloc(c -> countArray, countsSz * sizeof(*c->countArray));
  c -> countArray[countIndex] = malloc(sizeof(*c -> countArray[countIndex]));
  c -> countArray[countIndex] -> value = malloc(nameSz*sizeof(*name));
  c -> countArray[countIndex] -> count = 1;
  strncpy(c -> countArray[countIndex] -> value, name, nameSz);
}

//adds a name to c
void addCount(counts_t * c, const char * name) {
  int index = 0;
  
  if(name == NULL){//if null value, increment numUnknowns
    ++c -> numUnknowns;
  } else if(c -> numCounts == 0){//if first value, initialize countArray pointer and first countarray value
    addCountArray(c, name, 0, 1);
    ++c -> numCounts;
  }
  else{
    index = findName(c, name);
    if(index != -1){//if value exists, increment it
      ++c -> countArray[index] -> count;
    } else { //if it doesn't exist, add a countArray for it.
      addCountArray(c, name, c->numCounts, c -> numCounts+1);
      ++c -> numCounts;
    }
  }
  //WRITE ME
}
void printCounts(counts_t * c, FILE * outFile) {
  for(size_t i=0; i < c->numCounts; ++i){
    fprintf(outFile, "%s: %ld\n", c->countArray[i]->value, c->countArray[i]->count);
  }
  if(c->numUnknowns>0){
    fprintf(outFile, "%s: %ld\n", "<unknown>", c->numUnknowns);
  }
  //WRITE ME
}

void freeCounts(counts_t * c) {
  for(size_t i=0; i < c->numCounts; ++i){
    free(c -> countArray[i] -> value);
    free(c -> countArray[i]);
  }
  free(c -> countArray);
  free(c);
  //WRITE ME
}
