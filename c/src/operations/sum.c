#include <stdio.h>
#include <stdlib.h>

#include "menu.h"

static void sum(int argc, char **argv) {
    if (argc < 2) {
        printf("Usage: sum <a> <b>\n");
        return;
    }

    int a = atoi(argv[0]);
    int b = atoi(argv[1]);

    printf("%d + %d = %d\n", a, b, a + b);
}

MENU("Sum a + b", sum);