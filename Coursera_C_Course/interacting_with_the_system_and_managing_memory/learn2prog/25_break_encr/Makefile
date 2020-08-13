CC = gcc
CFLAGS  = -g -Wall

default: breaker

# To create the executable file count we need the object files
# countwords.o, counter.o, and scanner.o:
#
breaker:  breaker.o
	$(CC) $(CFLAGS) -o breaker breaker.o

breaker.o:  breaker.c
	$(CC) $(CFLAGS) -c breaker.c

# To start over from scratch, type 'make clean'.  This
# removes the executable file, as well as old .o object
# files and *~ backup files:
#
clean: 
	$(RM) breaker *.o *~