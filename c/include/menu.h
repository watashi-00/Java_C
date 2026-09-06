#pragma once

typedef void (*menu_fn)(void);

typedef struct {
    const char *label;
    menu_fn fn;
} menu_entry;

#define MENU(label, function) \
    static const menu_entry __menu__##function \
    __attribute__((used, section("menu_entries"))) = { \
        label, function \
    };

extern const menu_entry __start_menu_entries[];
extern const menu_entry __stop_menu_entries[];