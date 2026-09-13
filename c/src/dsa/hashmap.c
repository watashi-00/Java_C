//
// Created by watashi on 9/13/26.
//

#include <reflect.h>
#include <stdlib.h>

typedef struct {
    const void *data;
    size_t size;
    unsigned long hash;
} Key;

typedef struct {
    void *data;
    size_t size;
} Value;

typedef struct Entry {
    Value data;
    Key key;
    struct Entry *next;
} Entry;

typedef struct Map {
    Entry **buckets;
    size_t cap;
    size_t size;

    unsigned long (*HashFN)(const void *key, size_t length);
    void (*put)(struct Map *map, Entry *entry);
} Map;

void put(Map *map, Entry *entry);

unsigned long gen_hash(const void *key, size_t length);

Map createMap() {
    const int cap = 8;
    return (Map) {
        .HashFN = gen_hash,
        .cap = cap,
        .size = 0,
        .buckets = calloc(cap, sizeof(Entry *)),
        .put = put,
    };
}

Entry *createEntry(const Key key,const Value value) {
    Entry *entry = malloc(sizeof(Entry));

    *entry = (Entry) {
        .data = value,
        .key = key,
        .next = NULL
    };

    return entry;
}

Key createKey(const void *value, const size_t length) {
    return (Key) {
        .data = value,
        .size = length,
        .hash = gen_hash(value, length)
    };
}

Value createValue(void *value, const size_t length) {
    return (Value) {
        .data = value,
        .size = length,
    };
}

void run(void *ctx) {

    Map map = createMap();

    const Key key = createKey("oi", sizeof("oi") - 1);
    const Value value = createValue("oi", sizeof("oi") - 1);

    map.put(&map, createEntry(key, value));


    for (size_t i = 0; i < map.cap; i++) {
        printf("%zu - %p\n", i, (void *) map.buckets[i]);

        if (map.buckets[i]) {
            Entry *e = map.buckets[i];

            printf("  key: %.*s\n",
                   (int) e->key.size,
                   (const char *) e->key.data);
        }
    }
}

unsigned long gen_hash(const void *key, size_t length) {
    const unsigned char *p = key;
    unsigned long hash = 5381;

    for (size_t i = 0; i < length; i++) {
        hash = ((hash << 5) + hash) + p[i]; // hash * 33 + act byte
    }

    return hash;
}

void put(Map *map, Entry *entry) {
    const size_t index = entry->key.hash & (map->cap - 1);

    map->buckets[index] = entry;
    map->size++;
}

REFLECT(dsa, "hashmap", run)