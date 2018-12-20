#include <stdio.h>
#include<string.h>
#include<stdlib.h>

typedef struct Cell {
    struct Cell* next;
    struct Cell* prev;
    char content;
};

typedef struct Trans {
    char state;
    char symbol;
    char newState;
    char newSymbol;
    char dir;
};
int main(int argc, char *argv[]) {
    struct Trans *t = malloc(sizeof(struct Trans));
    int states;
    FILE *f = fopen(argv[1], "r");
    char *str = malloc(sizeof(char) * 20);
    fscanf(f, "%[^\n]", str);
    printf("Initial tape content: "); puts(str);
    struct Cell *last = malloc(sizeof(struct Cell));
    struct Cell *head = malloc(sizeof(struct Cell));
    head = NULL;
    for (int i = 0; i < strlen(str); i++) {
        struct Cell *add = malloc(sizeof(struct Cell));
        add->content = str[i];
        add->prev = last;
        if (i ==0){
            add->prev =NULL;
        }
        if (last != NULL) {
            last->next = add;
            head = last->prev;
        }
        last = add;
    }

    char *stat = malloc(sizeof(int) * 50);
    //find # states
    fscanf(f," %[^\n]",stat);
    char states1;
    states1=stat[0];
    states = states1 -'0';
    char *star = malloc(sizeof(char)*20);
    char *sto = malloc(sizeof(char)*10);

    char stop1;
    int start;
    int stop;
    // get start
    fscanf(f," %[^\n]",star);
    char start1 = star[0];
    start = start1 - '0';

    //get stop
    fscanf(f," %[^\n]",sto);
    stop1 = sto[0];
    stop = stop1-'0';

    //make 2d array
    struct Trans tm1 [states][256];
    struct Trans *new1 = malloc(sizeof(struct Trans));
    char *str5 = malloc(sizeof(char)*20);
    //put into 2d array
        while(!feof(f)) {
            struct Trans *temp;
            fscanf(f, " %[^\n]", str5);
            new1->state = str5[0];
            int st =str5[0] -'0';
            char sy =str5[2];
            new1->symbol = str5[2];
            new1->newSymbol = str5[4];
            new1 ->dir = str5[6];
            new1 -> newState= str5[8];
            temp = new1;
            tm1[st][sy] = *temp;
        }
        //do the thing
    while (head->prev != NULL) {
        head = head->prev;
    }
    //head=head->next;
    int  state5 = start;
    while(state5 != stop) {
             t =&tm1[state5][head->content];
             head->content = t->newSymbol;
            if ((t->dir == 82)) {
                if ((head->next->content == 13)|| head->next ==NULL) {
                    struct Cell *newCellR = malloc(sizeof(struct Cell));
                    newCellR->content = 66;
                    newCellR->prev = head;
                    head->next = newCellR;
                    head = head->next;
                }
                else if(head->next!= NULL){
                    head=head->next;
                }

            }
            else if ((t->dir == 76)) {

                if(head->prev != NULL){
                    head =head->prev;
                } else if(head->prev == NULL){
                    struct Cell *newCellL = malloc(sizeof(struct Cell));
                    newCellL->content = 66;
                    newCellL->next = head;
                    head->prev = newCellL;
                    head = head->prev;
                }

            }
            state5 =(t->newState) -'0';
            if (state5 == stop ){
                break;
            }
    }
            //print 2ll
            while (head->prev != NULL) {
                head = head->prev;
            }
            char head5;
            printf("Final tape contents:");
            while (head != NULL) {
                head5 = head->content;
                printf("%c",head5);
                head = head->next;
            }
            printf("\n");
            fclose(f);
            return 0;
}
