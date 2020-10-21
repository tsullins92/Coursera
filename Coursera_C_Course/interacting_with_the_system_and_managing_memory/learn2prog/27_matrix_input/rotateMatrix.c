#include <stdio.h>
#include <string.h>
#include <stdlib.h>

void rotate(char matrix[10][12]){
  char rotMatrix[10][12];
  for(int x=0;x<10;++x){
    int i=0;
    for(int y=9;y>-1;--y){
      if(matrix[y][x]=='\n'){
    	fprintf(stderr,"matrix[%d][%d] contains newline.\n",y,x);
    	exit(EXIT_FAILURE);
          }
          rotMatrix[x][i]=matrix[y][x];
          ++i;
        }
  }
  for(int x=0;x<10;++x){
    for(int y=0;y<10;++y){
      matrix[x][y] = rotMatrix[x][y];
    }
  }
  return;
}

void populateMatrix(FILE * f, char matrix[10][12]) {
  int lineIndex = 0;
  while (fgets(matrix[lineIndex], 12, f) != NULL) {
    if(strchr(matrix[lineIndex],'\n')==NULL){
      fprintf(stderr,"Line is too long.\n");
      exit(EXIT_FAILURE);
    } if(lineIndex>9){
      fprintf(stderr,"Too many lines.\n");
      exit(EXIT_FAILURE);
    } if(strlen(matrix[lineIndex]) < 11){
      fprintf(stderr,"Line too short.\n");
      exit(EXIT_FAILURE);
    }
    ++lineIndex;
  }
  if(lineIndex < 10){
    fprintf(stderr,"Not enough lines.\n");
    exit(EXIT_FAILURE);
  }
}

void printMatrix(char matrix[10][12]){
  for(int x=0;x<10;++x){
    for(int y=0;y<11;++y){
      printf("%c",matrix[x][y]);
    }
  }
}

int main (int argc, char ** argv) {
  if(argc !=2){
    fprintf(stderr,"Must pass exactly one argument, which is the name of a .txt file to decipher");
    return EXIT_FAILURE;
  }
  FILE * f = fopen(argv[1],"r");
  if(f == NULL){
    perror("Error from fopen: ");
    return EXIT_FAILURE;
  }
  char matrix[10][12] = {0};
  populateMatrix(f,matrix);
  rotate(matrix);
  printMatrix(matrix);
  if (fclose(f) != 0) {
    perror("Failed to close the input file!");
    return EXIT_FAILURE;
  }
  return EXIT_SUCCESS;
}
