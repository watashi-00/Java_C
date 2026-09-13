#pragma once

#include <stdio.h>
#include <stddef.h>

/*
 * DEBUG
 *
 * target_compile_definitions(app PRIVATE DEBUG)
 */

#ifdef DEBUG

#define RFL_HANDLER(name, function, ctx) \
    printf( \
        "[DEBUG] %s | fn=%s | ctx=%p | data=%p | size=%zu\n", \
        name, \
        #function, \
        (void *)(ctx), \
        (ctx) ? (ctx)->data : NULL, \
        (ctx) ? (ctx)->size : 0 \
    )

#else

#define RFL_HANDLER(name, function, ctx)

#endif

/*
 * CONTEXT
 */

typedef struct ctx {
    void *data;
    size_t size;
} ctx_t;

/*
 * REFLECTION FUNCTION
 */

typedef void (*reflect_fn)(ctx_t *ctx);

/*
 * REFLECTION
 */

typedef struct {
    const char *name;
} reflection_t;

/*
 * REFLECTION ENTRY
 */

typedef struct {
    const char *label;
    const reflection_t *reflection;
    reflect_fn fn;
} reflect_entry;


/*
 * REFLECT
 *
 * Automatically creates:
 *
 *   - reflection_t
 *   - reflected function wrapper
 *   - reflect_entry
 *
 * and places them into their respective registries.
 */

#define REFLECT(reflection_name, entry_label, function) \
    static const reflection_t __reflection_##reflection_name \
    __attribute__((used, section("reflection_entries"))) = { \
        .name = #reflection_name \
    }; \
    \
    static void __reflect_call_##function(ctx_t *ctx) { \
        RFL_HANDLER(entry_label, function, ctx); \
        function(ctx); \
    } \
    \
    static const reflect_entry __reflect__##function \
    __attribute__((used, aligned(8), section("reflect_entries"))) = { \
        .label = entry_label, \
        .reflection = &__reflection_##reflection_name, \
        .fn = __reflect_call_##function \
    };

/*
 * REFLECTION REGISTRY
 */

extern const reflection_t __start_reflection_entries[];
extern const reflection_t __stop_reflection_entries[];

/*
 * REFLECT ENTRY REGISTRY
 */

extern const reflect_entry __start_reflect_entries[];
extern const reflect_entry __stop_reflect_entries[];