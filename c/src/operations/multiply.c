//
// Created by watashi on 9/6/26.
//
#include <stdio.h>
#include <stdlib.h>

#include "menu.h"

static void multiply(int argc, char **argv) {
    if (argc < 2) {
        printf("Usage: sum <a> <b>\n");
        return;
    }

    int a = atoi(argv[0]);
    int b = atoi(argv[1]);

    printf("%d * %d = %d\n", a, b, a * b);
}

MENU("Multiply a * b", multiply);