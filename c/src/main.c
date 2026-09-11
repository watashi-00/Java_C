#include <stdio.h>

#include "reflect.h"

static int getInput(const char label[], int max);

int main() {

    const reflect_entry *start =   __start_reflect_entries;
    const reflect_entry *end   =    __stop_reflect_entries;
    int i = 0;
    for (const reflect_entry *entry = start;
        (const char *)entry < (const char *)end; entry++, i++) {
        printf("%d : %s\n", i, entry->label);
        printf("sizeof(reflect_entry) = %zu\n", sizeof(reflect_entry));
        printf("start = %p\n", (void *)start);
        printf("end   = %p\n", (void *)end);
    }


    const unsigned int input = getInput("> ", i);

    const unsigned int i1 = getInput("first  number: ", 0);
    const unsigned int i2 = getInput("second number: ", 0);

    int values[] = {(int) i1, (int) i2};

    ctx_t ctx = { .data = values, .size = sizeof(values) / sizeof(values[0]) };

    start[input].fn(&ctx);

    return 0;
}


int getInput(const char label[], const int max) {

    int input;

    while (1) {

        printf("%s", label);

        if (scanf("%d", &input) == 1 &&
            (max == 0 || input < max)) {
            return input;
            }

        printf("Invalid input\n");

        int c;
        while ((c = getchar()) != '\n' && c != EOF);
    }
}