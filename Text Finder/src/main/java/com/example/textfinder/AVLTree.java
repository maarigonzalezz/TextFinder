package com.example.textfinder;

//Estructura básica de un Árbol AVL
public class AVLTree {
    private AVLNode root;

    // Constructor
    public AVLTree() {
        root = null;
    }

    // Método público para insertar un nuevo nodo
    public void insert(int key) {
        root = insert(root, key);
    }

    // Método recursivo privado para insertar un nuevo nodo
    private AVLNode insert(AVLNode node, int key) {
        if (node == null) {
            return new AVLNode(key);
        }

        if (key < node.key) {
            node.left = insert(node.left, key);
        } else if (key > node.key) {
            node.right = insert(node.right, key);
        } else {
            return node;  // No se permiten valores duplicados
        }

        // Actualizar altura de este nodo ancestro
        node.height = 1 + Math.max(height(node.left), height(node.right));

        // Obtener el factor de equilibrio
        int balance = getBalance(node);

        // Si el nodo se desbalanceó, hay 4 casos

        // Caso izquierda-izquierda
        if (balance > 1 && key < node.left.key) {
            return rotateRight(node);
        }

        // Caso derecha-derecha
        if (balance < -1 && key > node.right.key) {
            return rotateLeft(node);
        }

        // Caso izquierda-derecha
        if (balance > 1 && key > node.left.key) {
            node.left = rotateLeft(node.left);
            return rotateRight(node);
        }

        // Caso derecha-izquierda
        if (balance < -1 && key < node.right.key) {
            node.right = rotateRight(node.right);
            return rotateLeft(node);
        }

        // Devolver el nodo (cambiado o no)
        return node;
    }

    // Método para obtener la altura del árbol
    private int height(AVLNode N) {
        if (N == null)
            return 0;
        return N.height;
    }

    // Método para obtener el factor de equilibrio de un nodo
    private int getBalance(AVLNode N) {
        if (N == null)
            return 0;
        return height(N.left) - height(N.right);
    }

    // Rotación a la derecha
    private AVLNode rotateRight(AVLNode y) {
        AVLNode x = y.left;
        AVLNode T2 = x.right;

        // Realizar rotación
        x.right = y;
        y.left = T2;

        // Actualizar alturas
        y.height = Math.max(height(y.left), height(y.right)) + 1;
        x.height = Math.max(height(x.left), height(x.right)) + 1;

        // Retornar nueva raíz
        return x;
    }

    // Rotación a la izquierda
    private AVLNode rotateLeft(AVLNode x) {
        AVLNode y = x.right;
        AVLNode T2 = y.left;

        // Realizar rotación
        y.left = x;
        x.right = T2;

        // Actualizar alturas
        x.height = Math.max(height(x.left), height(x.right)) + 1;
        y.height = Math.max(height(y.left), height(y.right)) + 1;

        // Retornar nueva raíz
        return y;
    }
}