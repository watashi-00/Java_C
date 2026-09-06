#include <stdio.h>

#include "menu.h"


int main() {

    const menu_entry *start =  __start_menu_entries;
    const menu_entry *end   =   __stop_menu_entries;
    int i = 0;
    for (const menu_entry *entry = start; entry != end; entry++, i++) {
        printf("%d : %s\n", i, entry->label);
    }

    unsigned int input;

    while (scanf("%u", &input) != 1 || input < 0 || input >= i) {
        printf("Invalid input\n");

        int c;
        while ((c = getchar()) != '\n' && c != EOF);
    };

    start[input].fn();

    return 0;
}
