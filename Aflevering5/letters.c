/************************************************
 * Program: letters
 *
 * Author: Thomas Conrad (s183742)
 * Course: 02102 Indledende Programmering F19
 * Purpose: Counts the amount of signs, excluding spaces, in the passed argument(s)
 * Usage: Run the program as ">letters ..." substituting ... with your argument(s)
 ************************************************/

#include <stdio.h>
#include <string.h>

int main(int argc, char *argv[])
{
    int len = 0;
    int i;
    for (i = 1; i < argc; i++){ // Løkke der summerer længderne af argumenterne og lægger dem i en variabal
        len += strlen(argv[i]);
    }
    printf("%d",len);
    return 0;
}