//
// Created by watashi on 9/6/26.
//
#include <stdio.h>

#include <menu.h>

static void multiply(void) {
    int a = 1;
    int b = 2;

    printf("> ");
    scanf("%d", &a);
    printf("> ");
    scanf("%d", &b);

    printf("%d * %d = %d", a, b, a*b);
}

MENU("multiply a * b", multiply);