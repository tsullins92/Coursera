#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "outname.h"

char * computeOutputFileName(const char * inputName) {
  size_t inputSz = 0;
  while(inputName[inputSz]!='\0'){
    ++inputSz;
  }
  char * outFileName = malloc((inputSz + 8) * sizeof(*outFileName));
  strcpy(outFileName, inputName);
  strcat(outFileName, ".counts");
  return outFileName;
  //WRITE ME
}
