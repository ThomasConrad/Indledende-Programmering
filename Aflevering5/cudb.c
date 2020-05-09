/*************************************************
 * Program: cudb
 *
 * Author: Thomas Conrad (s183742)
 *
 * Course: 02102 Indledende Programmering F19
 *
 * Purpose: To store and view data about students
 *
 * Usage: Run program and follow instructions to store
 * data and view said data.
 * ***********************************************/

#include <stdio.h>
#include <string.h> //Used to reverse a string and find the length of strings. Not necessary, just simplifies things
#define NAME_SIZE 5

typedef struct
{
    char name[NAME_SIZE];
    int data;
} student_t;

//Takes a decimal number and stores the binary number in a given string. The number is extended with leading zero's to fit a given length.
void toBinary(int decIn, char * out, int len){
    char temp[1];
    //Effectively finds the remainder when dividing by powers of two, which can be used to convert to binary.
    sprintf(out, "%d", decIn % 2);
    decIn /= 2;
    int i = 1;
    while(decIn > 0){
        sprintf(out, "%s%d",out, decIn % 2);
        decIn /= 2;
        i++;
    }
    while(i < len){
        out[i] = '0';
        i++;
    }
    //reverses the string, so that the bit is portrayed in the correct order.
    strrev(out);
    out[len] = '\0';
}

//Raises the base to the exponent, and returns the value.
int power(int base, int exponent){
    int i;
    int result = 1;
    for(i = 0; i < exponent; i++){
        result *= base;
    }
    return result;
}

//Takes a console inpout and ouputs the binary number representation as a string.
void readToBinary(char * out,int len){
    int input;
    scanf("%d",&input);
    toBinary(input,out,len);
}

//Converts a binary number string to it's decimal counterpart.
int toDecimal(char * binaryIn){
    int i;
    int result = 0;
    //Converts the char to an int and multiplies it by a power of two specified by it's position in the string
    for(i = 0; i < strlen(binaryIn) ;i++){
        int num = binaryIn[strlen(binaryIn) - 1 - i] - '0';
        result +=   num *  power(2,i);
    }
    return result;
}

//Takes an input string and fills an output string with a substring of the input, specified by a given start position and length.
void substring(char * strIn, char * strOut, int start, int length){
    int i = 0;
    for (i = start; i < start+length; i++){
        strOut[i-start] = strIn[i];
    }
    strOut[length] = '\0';
}

//Returns the GPA as an integer of a given student
int getGPA(student_t * student_p){
    char data[15];
    char GPA[15];
    toBinary(student_p->data,data,14);
    substring(data,GPA,0,8);
    return toDecimal(GPA);
}

//Prints information about a given student to the console
void printStudent(student_t * student_p, int numStudent){
    char data[15];
    char year[15];
    char term[15];
    char GPA[15];

    printf("s%04d ",numStudent);
        
    printf("%s ", student_p->name);

    toBinary(student_p->data,data,14);
    
    substring(data,year,9,5);
    printf("%d ", toDecimal(year)+2009);
    
    substring(data,term,8,1);
    printf("%s ", (*term == '1') ? "Spring" : "Autumn");
    
    substring(data,GPA,0,8);
    printf("%d \n", toDecimal(GPA));

}

int main()
{
    char* in_p, in;
    in_p = &in;
    int studentAmount = 0;
    student_t students[1000];
    
    puts("Welcome to CUDB - The C University Data Base\n");
    puts("0: Halt\n1: List all students\n2: Add a new student\n ");
    puts("Enter action:");    
    scanf("%c", in_p);
        
    while (in != '0')
    {
        switch (in)
        {

        //prints all student details and the average GPA
        case '1':{
            int i;
            double GPA = 0;

            printf("\n");
            for (i = 0; i < studentAmount; i++){
                printStudent(&students[i],i+1);
                GPA += getGPA(&students[i]);
            }
            printf("\nAverage GPA = %.2f\n",GPA/studentAmount);
            
            puts("\nEnter action:");
            break;
        }
            
        //Reads information about a single students, and saves it in the struct array
        case '2':{
            char data[15];
            char tempTerm[15];
            char tempStart[15];
            char tempGPA[15];
            
            puts("\nEnter name (4 characters only):");
            scanf("%s",&students[studentAmount].name);

            puts("\nEnter start year (2009-2040): ");
            int read;
            scanf("%d",&read);
            toBinary(read-2009,tempStart,5);

            puts("\nEnter start semester (0=Autumn/1=Spring):");
            readToBinary(tempTerm,1);

            puts("\nEnter GPA (0-255):");
            scanf("%d",&read);
            toBinary(read,tempGPA,8);

            sprintf(data,"%s%s%s", &tempGPA,&tempTerm,&tempStart);
            students[studentAmount].data = toDecimal(data);

            studentAmount = studentAmount + 1;

            puts("\nEnter action:");
            break;
        }

        default:{
            puts("Enter valid action!");
            break;
        }
        }
        scanf(" %c", in_p);
        
    }

    puts("\nBye");
    return 0;
}