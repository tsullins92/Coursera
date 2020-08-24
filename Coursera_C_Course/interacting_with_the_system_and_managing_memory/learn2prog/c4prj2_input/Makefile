CFLAGS=-Wall -Werror -std=gnu99 -pedantic -ggdb3
SRCS=$(wildcard *.c)
OBJS=$(patsubst %.c, %.o, $(SRCS))
PROGRAM=test_cards

$(PROGRAM): $(OBJS)
	gcc $(CFLAGS) -o $@ $(OBJS)

%.o: %.c
	gcc -c $(CFLAGS) $<

clean:
	rm -f $(OBJS) $(PROGRAM) *~

input.o: input.h
future.o: future.h
eval.o: eval.h
deck.o: deck.h
cards.o: cards.h
test.o: cards.h
test.o: deck.h
test.o: eval.h
test.o: future.h
test.o: input.h
