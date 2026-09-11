#include <stdio.h>

#include "reflect.h"


int main() {

    const reflect_entry *start =   __start_reflect_entries;
    const reflect_entry *end   =    __stop_reflect_entries;
    int i = 0;
    for (const reflect_entry *entry = start; entry != end; entry++, i++) {
        printf("%d : %s\n", i, entry->label);
    }

    unsigned int input;

    while (scanf("%u", &input) != 1 || input < 0 || input >= i) {
        printf("Invalid input\n");

        int c;
        while ((c = getchar()) != '\n' && c != EOF);
    };

    int values[] = {10, 20};

    ctx_t ctx = { .data = values, .size = sizeof(values) / sizeof(values[0]) };

    start[input].fn(&ctx);

    return 0;
}
