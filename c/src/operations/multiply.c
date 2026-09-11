//
// Created by watashi on 9/6/26.
//
#include <stdio.h>
#include <stdlib.h>

#include "reflect.h"

static void multiply(ctx_t *ctx) {
    // int a = atoi(ctx[0]);
    // int b = atoi(ctx[1]);
    //
    // printf("%d * %d = %d\n", a, b, a * b);
}

REFLECT("Multiply a * b", multiply);