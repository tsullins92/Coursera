#include <stdio.h>
#include <stdlib.h>

unsigned power(unsigned x, unsigned y);

void run_check(unsigned x, unsigned y, unsigned expected_ans){
  if(power(x,y)!=expected_ans){
    printf("power(%d,%d) returns %d\n",x,y,power(x,y));
    exit(EXIT_FAILURE);
  }
}

int main(void) {
  run_check(1,1,1);
  run_check(10,0,1);
  run_check(0,1,0);
  run_check(0,0,1);
  run_check(2,6,64);
  run_check(-5,3,-125);
  run_check(-2500,1,-2500);
  printf("\n");
  return EXIT_SUCCESS;
}
