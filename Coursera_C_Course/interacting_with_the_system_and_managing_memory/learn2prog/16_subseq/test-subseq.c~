#include <stdio.h>
#include <stdlib.h>

size_t maxSeq(int * array, size_t n);

int main(void) {
  int testArray1[] = { -1, -2, 2, 3, 42, 6, 27, 4};
  int testArray2[] = { 50, 38, 2, 20, 34, 68, -2, -1};
  int testArray3[] = { 0, 1, 3, 3, 1, 2, 27, 4};
  int testArray4[] = { 50, 51, 52, 53, 1, 2, 3, 4, 5, -4, 101, 102, 103};
  int testArray5[] = { 0,1,2,0,10,30,40,0,41,42,1,2,3,4,5,6,7,-80,-70,-60,-50,-40,-30,-20,-10,0};
  int testArray6[] = { 1, 0, 2, 0, 3, 0, 4, 1};
  int testArray7[] = { 51, 52, 53, 41, 42, 31, 32, 33, 34 };
  int testArray8[] = { 51, 52, 53, 54, 55, 31, 32, 33, 34 };
  int testArray9[] = {-1, 4, 0, 2, 5};
  int testArray10[] = {-1, 0, 2, -5, 6};
  int testArray11[] = {-3,-42,-99,-1000,-999,-88,-77};
  int testArray12[] = {};

  if(maxSeq(testArray2,8)!=4){
    printf("Failed on array2\n");
    return EXIT_FAILURE;}
  if(maxSeq(testArray3,8)!=3){
    printf("Failed on array3\n");
    return EXIT_FAILURE;}
  if(maxSeq(testArray4,13)!=5){
    printf("Failed on array4\n");
    return EXIT_FAILURE;}
  if(maxSeq(testArray5,26)!=9){
    printf("Failed on array5\n");
    return EXIT_FAILURE;}
  if(maxSeq(testArray6,8)!=2){
    printf("Failed on array6\n");
    return EXIT_FAILURE;}
  if(maxSeq(testArray7,9)!=4){
    printf("Failed on array7\n");
    return EXIT_FAILURE;}
  if(maxSeq(testArray8,9)!=5){
    printf("Failed on array8\n");
    return EXIT_FAILURE;}
  if(maxSeq(testArray9,5)!=3){
    printf("Failed on array9\n");
    return EXIT_FAILURE;}
  if(maxSeq(testArray10,5)!=3){
    printf("Failed on array10\n");
    return EXIT_FAILURE;}
  if(maxSeq(testArray11,7)!=4){
    printf("Failed on array11\n");
    return EXIT_FAILURE;}
  if(maxSeq(testArray12,0)!=0){
    printf("Failed on array12\n");
    return EXIT_FAILURE;}



  return EXIT_SUCCESS;
}



