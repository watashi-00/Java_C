//
// Created by watashi on 9/6/26.
//
#include <stdio.h>

#include "reflect.h"

REFLECTION_DECLARE(Operations)
static void multiply(ctx_t *ctx) {
    // int a = atoi(ctx[0]);
    // int b = atoi(ctx[1]);
    //
    // printf("%d * %d = %d\n", a, b, a * b);
}

REFLECT(Operations, "Multiply a * b", multiply)