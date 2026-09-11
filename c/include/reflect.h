#pragma once

// #define DEBUG, use the CMakeLists to define DEBUG
// target_compile_definitions(app PRIVATE DEBUG)

#include <stdio.h>

#ifdef DEBUG

#define REFLECT_DEBUG(name, function, ctx) \
    printf( \
        "[DEBUG] %s | fn=%s | ctx=%p | data=%p | size=%zu\n", \
        name, \
        #function, \
        (void *)(ctx), \
        (ctx) ? (ctx)->data : NULL, \
        (ctx) ? (ctx)->size : 0 \
    )

#else

#define REFLECT_DEBUG(name, function, ctx)

#endif

typedef struct ctx {
    void *data;
    size_t size;
} ctx_t;

typedef void (*reflect_fn)(ctx_t *ctx);

typedef struct {
    const char *label;
    reflect_fn fn;
} reflect_entry;

#define REFLECT(name, function) \
    static void __reflect_call_##function(ctx_t *ctx) { \
        REFLECT_DEBUG(name, function, ctx); \
        function(ctx); \
    } \
    static const reflect_entry __reflect__##function \
    __attribute__((used, section("reflect_entries"))) = { \
        name, \
        __reflect_call_##function \
    };

extern const reflect_entry __start_reflect_entries[];
extern const reflect_entry __stop_reflect_entries[];