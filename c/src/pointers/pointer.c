//
// Created by watashi on 9/13/26.
//

#include "reflect.h"

REFLECTION_DECLARE(Pointers)

void showPointersReference(void *ctx) {
    int x = 42;
    int *p = &x;



}


void fn(int v) {
        
}
REFLECT(Pointers, "show pointers", showPointersReference)
