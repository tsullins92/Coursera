CC = gcc
CFLAGS  = -g -Wall

default: test

# To create the executable file count we need the object files
# countwords.o, counter.o, and scanner.o:
#
test:  test.o power.o
	$(CC) $(CFLAGS) -o test test.o power.o

test.o:  test.c
	$(CC) $(CFLAGS) -c test.c

power.o:  power.c
	$(CC) $(CFLAGS) -c power.c

# To start over from scratch, type 'make clean'.  This
# removes the executable file, as well as old .o object
# files and *~ backup files:
#
clean: 
	$(RM) count *.o *~