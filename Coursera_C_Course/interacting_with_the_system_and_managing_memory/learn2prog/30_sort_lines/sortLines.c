#include <stdio.h>
#include <stdlib.h>
#include <string.h>


//This function is used to figure out the ordering
//of the strings in qsort.  You do not need
//to modify it.
int stringOrder(const void * vp1, const void * vp2) {
  const char * const * p1 = vp1;
  const char * const * p2 = vp2;
  return strcmp(*p1, *p2);
}
//This function will sort and print data (whose length is count).
void sortData(char ** data, size_t count) {
  qsort(data, count, sizeof(char *), stringOrder);
}

void processInput(FILE * f){
  char ** lines = NULL;
  char * line = NULL;
  size_t sz = 0;
  size_t i = 0;
  while (getline(&line,&sz, f) >= 0) {
    lines = realloc(lines, (i+1)*sizeof(*lines));
    lines[i] = line;
    line = NULL;
    ++i;
  }
  free(line);
  sortData(lines, i);
  for(int index=0;index<i;++index){
    printf("%s",lines[index]);
    free(lines[index]);
  }
  free(lines);
}

void processInputFiles(int argc, char ** argv){
  for(int i=1;i<argc;++i){
    FILE * f = fopen(argv[i], "r");
    if (f == NULL) {
      perror("Could not open file");
      exit(EXIT_FAILURE);
    }
    processInput(f);
    if (fclose(f) != 0) {
      perror("Failed to close the input file!");
      exit(EXIT_FAILURE);
    }
  }
}

int main(int argc, char ** argv) {
  if (argc == 1) {
    processInput(stdin);
  } else{
    processInputFiles(argc,argv);
  }
  return EXIT_SUCCESS;
}
