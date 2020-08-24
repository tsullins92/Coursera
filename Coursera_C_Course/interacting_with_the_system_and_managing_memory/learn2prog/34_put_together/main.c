#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "kv.h"
#include "counts.h"
#include "outname.h"

//read values from filename, and create counts based on kvPairs
counts_t * countFile(const char * filename, kvarray_t * kvPairs) {
  counts_t * counts = createCounts();
  char * line = NULL;
  size_t sz = 0;
  FILE * f = fopen(filename, "r");
  if (f == NULL) {
    perror("Could not open value file");
    exit(EXIT_FAILURE);
  }
  while (getline(&line,&sz, f) >= 0) {
    line[strlen(line)-1] = '\0';
    char * value = lookupValue(kvPairs, line);
    addCount(counts, value);
    free(line);
    line = NULL;
  }
  free(line);
  if (fclose(f) != 0) {
    perror("Failed to close the input value file!");
    exit(EXIT_FAILURE);
  }
  return counts;
}

int main(int argc, char ** argv) {
  //WRITE ME (plus add appropriate error checking!)
  //ensure at least two files/args are present
  if(argc < 3){
    fprintf(stderr,"Usage: main kv_input value_input\n");
    return EXIT_FAILURE;
  }
  //read the key/value pairs from the file named by argv[1] (call the result kv)
  kvarray_t * kv = readKVs(argv[1]);
  //count from 2 to argc (call the number you count i)
  for(int i=2; i<argc; ++i){
    //count the values that appear in the file named by argv[i], using kv as the key/value pair
    //   (call this result c)
    counts_t * c = countFile(argv[i], kv);
    //compute the output file name from argv[i] (call this outName)
    char * outName = malloc((strlen(argv[i]) + 8) * sizeof(*outName));
    strcpy(outName, argv[i]);
    strcat(outName, ".counts");
    //open the file named by outName (call that f)
    FILE * f = fopen(outName, "w");
    if (f == NULL) {
      perror("Could not open file");
      return EXIT_FAILURE;
    }
    //print the counts from c into the FILE f
    printCounts(c, f);
    //close f
    if (fclose(f) != 0) {
      perror("Failed to close the output file!");
      return EXIT_FAILURE;
    }
    //free the memory for outName and c
    free(outName);
    freeCounts(c);
  }

  //free the memory for kv
  freeKVs(kv);
  return EXIT_SUCCESS;
}
