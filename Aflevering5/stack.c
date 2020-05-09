// stack.c

#include <stdlib.h>
#include <stdio.h>
#include "stack.h"

//Initialize new stack
stack_t * newStack(void) {
  stack_t *temp = (stack_t *)malloc(sizeof(stack_t));
  temp->array = (int *)malloc(sizeof(int[1]));
  temp->capacity = 1;
  temp->size = 0;
  return temp;
}

//Remove topmost item from stack, and return it
int pop(stack_t * stack_p) {
  if (empty(stack_p)) {
    fprintf(stderr, "%s\n", "Trying to pop empty stack!");
    exit(1);
  }
  stack_p->size--;
  int result = stack_p->array[stack_p->size];
  //"Delete" the item by resetting to 0
  stack_p->array[stack_p->size] = 0;
  return result;
}

//Push a number to the top of the stack
void push(stack_t * stack_p, int value) {
  //Check capacity of array. If it's full, double the allocated memory
  if (stack_p->size == stack_p->capacity) {
    stack_p->array = (int *)realloc(stack_p->array, sizeof(int[stack_p->size * 2]));
    stack_p->capacity *= 2;
  }
  //Add value to top of stack
  stack_p->array[stack_p->size] = value;
  stack_p->size++;
}

//Return the topmost number in the stack, without changing the stack
int top(stack_t * stack_p) {
  if (empty(stack_p)) {
    fprintf(stderr, "%s\n", "Trying to print top of empty stack!");
    exit(1);
  }
  return stack_p->array[stack_p->size - 1];
}

//Is the stack empty?
int empty(stack_t * stack_p) {
  return (stack_p->size == 0);
}
