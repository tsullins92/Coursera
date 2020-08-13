#include <stdio.h>
#include <stdlib.h>
#include <ctype.h>

void getCounts(FILE * f, int * letterCounts) {    
  int c;    
  while ((c = fgetc(f)) != EOF) {        
    if (isalpha(c)) {            
      c = tolower(c);   
      c -= 'a';                   
      c %= 26;
      ++letterCounts[c];
    }        
  }
}

int getKey(int * letterCounts){
  int highestValueIndex = 0;
  for(int i = 0; i < 26; ++i){
    if(letterCounts[i]>letterCounts[highestValueIndex]){highestValueIndex = i;}
  }
  highestValueIndex += 'a';
  highestValueIndex -= 'e';
  highestValueIndex += 26; //make answer between 0,26
  highestValueIndex %= 26;
  return highestValueIndex;
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
  int letterCounts[26] = {0};
  getCounts(f,letterCounts);  
  if (fclose(f) != 0) {    
    perror("Failed to close the input file!");    
    return EXIT_FAILURE;  
  }  
  int key = getKey(letterCounts);
  printf("%d\n",key);

  return EXIT_SUCCESS;
}
