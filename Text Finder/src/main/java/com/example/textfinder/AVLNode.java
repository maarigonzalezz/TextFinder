package com.example.textfinder;

class AVLNode {
    int key, height;
    AVLNode left, right;

    public AVLNode(int key) {
        this.key = key;
        this.height = 1;  // Un nodo nuevo tiene siempre altura 1
        this.left = null;
        this.right = null;
    }
}

