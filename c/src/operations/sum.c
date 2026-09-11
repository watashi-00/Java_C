#include <stdio.h>
#include <stdlib.h>

#include "reflect.h"

typedef struct {
    int a;
    int b;
} sum_context;

static void sum(ctx_t *ctx) {
    sum_context *c = ctx->data;

    printf("%d + %d = %d\n", c->a, c->b, c->a + c->b);
}

REFLECT("Sum a + b", sum);