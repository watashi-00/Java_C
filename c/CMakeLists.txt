cmake_minimum_required(VERSION 3.20)

project(menu_reflection C)

set(CMAKE_C_STANDARD 11)
set(CMAKE_C_STANDARD_REQUIRED ON)

add_executable(app
    src/main.c
        src/operations/sum.c
)

target_include_directories(app PRIVATE
    include
)