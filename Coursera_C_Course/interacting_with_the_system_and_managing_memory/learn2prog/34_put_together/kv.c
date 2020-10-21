#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "kv.h"


kvpair_t * parseKVs(char * curr){
  const char * separatorPtr = strchr(curr, '=');
  const char * eolPtr = strchr(curr, '\n');
  kvpair_t * kv = malloc(sizeof(*kv));

  kv->key = malloc((separatorPtr - curr + 1) * sizeof(char*));
  strncpy(kv->key, curr, separatorPtr - curr);
  kv->key[separatorPtr - curr] = '\0';
  
  kv->value = malloc((eolPtr - separatorPtr)* sizeof(char*));
  strncpy(kv->value, separatorPtr + 1, eolPtr - separatorPtr - 1);
  kv->value[eolPtr - separatorPtr - 1] = '\0';
  
  return kv;
}

//read key value pairs from file into kvarray_t
kvarray_t * readKVs(const char * fname) {
  FILE * f = fopen (fname, "r");
  if (f == NULL) {
    return NULL; //Could not open file->indicate failure
  }
  kvarray_t * kvArray = malloc(sizeof(*kvArray));
  kvArray->kvs = NULL;
  kvArray->numKvs = 0;
  char * line = NULL;
  size_t sz = 0;
  size_t i = 0;
  while (getline(&line,&sz, f) >= 0) {
    kvArray->kvs = realloc(kvArray->kvs, (i+1)*sizeof(*kvArray->kvs));
    kvArray->numKvs = i+1;
    kvArray->kvs[i] = parseKVs(line);
    free(line);
    line = NULL;
    ++i;
  }
  free(line);
  if (fclose(f) != 0) {
    perror("Failed to close the input file!");
    exit(EXIT_FAILURE);
  }
  return kvArray;
  //WRITE ME
}



void freeKVs(kvarray_t * pairs) {
  for(size_t i=0; i<pairs->numKvs; ++i){
    free(pairs->kvs[i]->key);
    free(pairs->kvs[i]->value);
    free(pairs->kvs[i]);
  }
  free(pairs->kvs);
  free(pairs);
  //WRITE ME
}

void printKVs(kvarray_t * pairs) {
  for(size_t i=0; i<pairs->numKvs; ++i){
    printf("key = '%s' value = '%s'\n", pairs->kvs[i]->key, pairs->kvs[i]->value);
  }
  //WRITE ME
}

char * lookupValue(kvarray_t * pairs, const char * key) {
  for(size_t i=0; i<pairs->numKvs; ++i){
    if(strcmp(pairs->kvs[i]->key, key) == 0){
      return pairs->kvs[i]->value;
    }
  }
  return NULL;
  //WRITE ME
}


