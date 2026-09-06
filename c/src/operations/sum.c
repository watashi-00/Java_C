#include <stdio.h>
#include "menu.h"

static void sum(void) {
    int a = 1;
    int b = 2;

    printf("> ");
    scanf("%d", &a);
    printf("> ");
    scanf("%d", &b);

    printf("%d + %d = %d", a, b, a+b);
}

MENU("Sum a + b", sum);