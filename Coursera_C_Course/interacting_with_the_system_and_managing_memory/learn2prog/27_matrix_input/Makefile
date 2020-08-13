CC = gcc
CFLAGS  = -g -Wall

default: rotateMatrix

# To create the executable file count we need the object files
# countwords.o, counter.o, and scanner.o:
#
rotateMatrix:  rotateMatrix.o
	$(CC) $(CFLAGS) -o rotateMatrix rotateMatrix.o

rotateMatrix.o:  rotateMatrix.c
	$(CC) $(CFLAGS) -c rotateMatrix.c

# To start over from scratch, type 'make clean'.  This
# removes the executable file, as well as old .o object
# files and *~ backup files:
#
clean:
	$(RM) rotateMatrix *.o *~
