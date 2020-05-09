/************************************************
 * Program: stackMain
 *
 * Author: Kristian Knudsen (s183779)
 * Course: 02102 Indledende Programmering F19
 * Purpose: Implements a simple stack with use of a struct and dynamic array
 *
 * The real magic happens in stack.c
 ************************************************/

//gcc -o stackMain stack.c stackMain.c

#include <stdlib.h>
#include <stdio.h>
#include "stack.h"

int main() {
  stack_t * myStack = newStack();

  //Push numbers on stack
  push(myStack, 123);
  push(myStack, 99);
  push(myStack, 4444);

  //Empty stack with pop(). First in, last out; the numbers
  //will print in reverse order under correct operation
  while (!empty(myStack)) {
    int value;
    value = pop(myStack);
    printf("popped: %d\n", value);
  }
  return 0;
}
