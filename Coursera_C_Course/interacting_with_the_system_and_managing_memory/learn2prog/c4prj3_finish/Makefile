CFLAGS=-std=gnu99 -Ofast
SRCS=$(wildcard *.c)
OBJS=$(patsubst %.c, %.o, $(SRCS))
PROGRAM=poker

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
main.o: cards.h
main.o: deck.h
main.o: eval.h
main.o: future.h
main.o: input.h
