package ru.progwards.java2.lessons.trees;

import java.util.ArrayList;
import java.util.TreeMap;
import java.util.function.Consumer;

/**
 * Класс АВЛ дерева
 * @author DanSprat
 * @version 1.0
 * @param <K> Тип ключа
 * @param <V> Тип значения
 */
public class AvlTree <K extends Comparable<K>,V> {
    /**
     * Звено АВЛ дерева
     * @param <K> Тип ключа
     * @param <V> Тип значения
     */
    protected class TreeNode <K extends Comparable<K>,V> {
        /**
         * Ключ
         */
        private K key;
        /**
         * Значение
         */
        private V value;
        /**
         * Левое поддерво
         */
        private TreeNode left;
        /**
         * Правое поддерво
         */
        private TreeNode right;
        /**
         * Узел - "Родитель"
         */
        private TreeNode parent;
        /**
         * Высота поддерева
         */
        private Integer height;

        /**
         * Констурктор создания звена по ключу и значению
         * @param key Ключ
         * @param value Значение
         */
        public TreeNode(K key, V value){
            this.key= key;
            this.value= value;
            height=1;
        }

        /**
         * Вычисление баланса поддереева
         * @return Баланс поддерева
         */
        public int balance(){
            int l;
            int r;
            if (left==null)
                l=0;
            else
                l=left.height;
            if (right==null)
                r=0;
            else
                r = right.height;
            return l-r;
        }

        /**
         * Поиск минимума
         * @return Лист с минимальным ключом
         */
        private TreeNode <K,V> findMin(){
            if (this.left == null){
                return this;
            } else {
                return this.left.findMin();
            }
        }

        /**
         * Поиск максимума
         * @return Лист с максимальным ключом
         */
        private TreeNode<K,V> findMax(){
            if (this.right == null){
                return this;
            } else {
                return this.right.findMax();
            }
        }
        /**
         * Удаление минимума
         * @return Лист с минимальным ключом
         */
        private TreeNode <K,V> deleteMin(){
            TreeNode <K,V> tmp = this.findMin();
            tmp.parent.left = null;
            return tmp;
        }
        /**
         * Удаление максимума
         * @return Лист с максимальным ключом
         */
        private TreeNode <K,V> deleteMax(){
            TreeNode <K,V> tmp = this.findMax();
            tmp.parent.right = null;
            return tmp;
        }

        /**
         * Поиск звена по ключу
         * @param key Ключ
         * @return Звено с соответствующим ключом
         */
        private TreeNode<K,V> find(K key){
            int cmp = key.compareTo(this.key);
            if (cmp>0){
                if (right !=null){
                    return right.find(key);
                } else {
                    return this;
                }
            }
            if (cmp<0){
                if (left!=null){
                    return left.find(key);
                } else {
                    return this;
                }
            }
            return this;
        }

        /**
         * Вставка звена
         * @param treeNode Звено
         */
        public void add (TreeNode<K,V> treeNode){
            int cmp = treeNode.key.compareTo(key);
            if (cmp == 0){
                value = treeNode.value;
                return;
            }
            if (cmp >0){
                right =  treeNode;
                treeNode.parent = this;
            } else {
                left = treeNode;
                treeNode.parent=this;
            }
        }

        /**
         * Удаление звена
         * @return Удаленное звено
         */
        public TreeNode <K,V> delete(){
          if (left !=null || right!=null){
              TreeNode<K,V> tmp;
              if (this.balance()>0){
                  tmp= left.findMin();
              } else {
                  tmp = right.findMax();
              }
              key = tmp.key;
              value = tmp.value;
              if (tmp.parent.left == tmp){
                  tmp.parent.left = null;
              } else {
                  tmp.parent.right = null;
              }
              return tmp;
          } else {
              if(parent.left == this){
                  parent.left = null;
              } else {
                  parent.right = null;
              }
              return this;
          }
        }

        /**
         *
         * @return Строка формата Key: {key} Value: {value}
         */
        public String toString() { return "Key: "+ key + " Value: "+ value;}

        /**
         * Метод для использования функционального интерфейса - consumer на каждый элемент children данного звена
         * @param consumer метод "Потребитель"
         */
        private void process (Consumer<TreeNode<K,V>>consumer){
            if (left!=null){
                left.process(consumer);
            }
            consumer.accept(this);
            if (right!=null){
                right.process(consumer);
            }
        }

    }

    /**
     * Вершина дерева
     */
    private TreeNode <K,V> head;

    /**
     * Получение высоты у поддерева
     * @param node - Поддерево
     * @return Высота
     */
    private int getHeight(TreeNode <K,V> node){
        if (node == null)
            return 0;
        return node.height;
    }

    /**
     * Метод для использования функционального интерфейса - consumer на каждый элемент дерева
     * @param consumer метод "Потребитель"
     */
    public void process(Consumer<TreeNode<K,V>>consumer){
        head.process(consumer);
    }

    /**
     * Поиск значения по ключу
     * @param key Ключ
     * @return Значение найденное по соответсвующему ключу
     * @exception NullPointerException несуществующий ключ
     */
    public V find(K key){
        if (head == null){
            throw new NullPointerException();
        } else {
            TreeNode <K,V> treeNode = head.find(key);
            return treeNode.key.compareTo(key) == 0? treeNode.value : null;
        }
    }

    /**
     * Вставить элемент в дерево
     * @param key Ключ
     * @param value Значение
     */
     public void put (K key,V value){
        add(new TreeNode<>(key,value));
     }

    /**
     * Вставка узла в дерево
     * @param node Узел
     */
    private void add (TreeNode<K,V> node){
        if (head == null){
            head  = node;
        } else {
            head.find(node.key).add(node);
            balance(node);
        }
     }

    /**
     * Удаление элемента по ключу
     * @param key Ключ
     */
     public void delete(K key){
        internalDelete(key);
     }

    /**
     * Удаление узла по ключу
     * @param key Ключ
     * @return Узел
     */
     private TreeNode<K,V> internalDelete(K key){
        if (head == null){
            throw new NullPointerException();
        }
        TreeNode <K,V> found = head.find(key);
        int cmp = found.key.compareTo(key);
        if (cmp!=0){
            throw new NullPointerException();
        }
        if (found.parent == null) {
            if (found.right != null) {
                head = found.right;
                head.parent=null;
                if (found.left != null)
                    add(found.left);
            } else if (found.left != null) {
                head = found.left;
                head.parent =null;
            }
            else
                head = null;
            balance(head);
        } else {
            TreeNode <K,V> parent = found.delete();
            balance(parent);
        }
        return found;
     }

    /**
     * Проверка: сбалансировано ли поддерево
     * @param node поддерво
     */
     private void balance(TreeNode<K,V> node){
        if (node == null){
            return;
        }
        TreeNode <K,V> tmp = node;
         while (tmp.parent!=null){
            tmp = tmp.parent;
            tmp.height = Math.max(getHeight(tmp.left),getHeight(tmp.right))+1;
            if (getHeight(tmp.right) - getHeight( tmp.left)==2){
                if (getHeight(tmp.right.left)<=getHeight(tmp.right.right)){
                    tmp =  smallLeft(tmp);
                    break;
                } else {
                    tmp = bigLeft(tmp);
                    break;
                }
            } else if (getHeight(tmp.left)-getHeight(tmp.right)==2){
                if(getHeight(tmp.left.right) <= getHeight(tmp.left.left)){
                    tmp = smallRight(tmp);
                    break;
                } else {
                    tmp = bigRight(tmp);
                    break;
                }
            }
        }
     }

    /**
     * Малое левое вращение
     * @param a Узел относительно которого идет вращение
     * @return Сссылка на новый узел, который встал на место узла a
     */
     private TreeNode <K,V> smallLeft(TreeNode<K,V> a){
         TreeNode <K,V> c = a.right.left;
         TreeNode <K,V> b = a.right;
         a.right = c;
         if (c!=null) {
             c.parent = a;
         }
         if (a.parent!=null){
             if (a == a.parent.left){
                 a.parent.left = b;
             } else {
                 a.parent.right = b;
             }
         }
         else {
             head = b;
         }
         b.left = a;
         b.parent = a.parent;
         a.parent = b;
         a.height = Math.max(getHeight(a.left),getHeight(a.right))+1;
         b.height = Math.max(getHeight(b.right),getHeight(b.left))+1;
         return b;
     }

    /**
     * Малое правое вращение
     * @param a Узел относительно которого идет вращение
     * @return Сссылка на новый узел, который встал на место узла 'a'
     */
    private TreeNode <K,V> smallRight(TreeNode<K,V> a){
        TreeNode<K,V> c = a.left.right;
        TreeNode<K,V> b = a.left;
        a.left = c;
        if (c!=null){
            c.parent = a;
        }
        if (a.parent!=null){
            if (a == a.parent.left){
                a.parent.left = b;
            } else {
                a.parent.right = b;
            }
        } else {
            head = b;
        }
        b.right = a;
        b.parent = a.parent;
        a.parent =b;
        a.height = Math.max(getHeight(a.left),getHeight(a.right))+1;
        b.height = Math.max(getHeight(b.right),getHeight(b.left))+1;
        return b;


    }
    /**
     * Большое левое вращение
     * @param a Узел относительно которого идет вращение
     * @return Сссылка на новый узел, который встал на место узла 'a'
     */
    private TreeNode <K,V> bigLeft(TreeNode <K,V> a){
        TreeNode<K,V> b = a.right;
        TreeNode<K,V> c = a.right.left;
        a.right = b.left.left;
        if (b.left.left != null) {
            b.left.left.parent = a;
        }
        b.left = b.left.right;
        if (b.left!=null){
            b.left.parent = b;
        }
        c.left = a;
        c.right = b;
        if (a.parent!=null){
            if (a.parent.left == a){
                a.parent.left = c;
            } else {
                a.parent.right = c;
            }
        } else {
            head = c;
        }
        c.parent = a.parent;
        b.parent = c;
        a.parent = c;
        a.height = Math.max(getHeight(a.left),getHeight(a.right))+1;
        b.height = Math.max(getHeight(b.left),getHeight(b.right))+1;
        c.height = Math.max(a.height,b.height)+1;

        return c;
    }
    /**
     * Большое правое левое вращение
     * @param a Узел относительно которого идет вращение
     * @return Сссылка на новый узел, который встал на место узла 'a'
     */
    private TreeNode <K,V> bigRight(TreeNode <K,V> a){
        TreeNode<K,V> b = a.left;
        TreeNode<K,V> c = a.left.right;
        a.left = c.right;
        if (c.right!=null){
            c.right.parent = a;
        }
        b.right = c.left;
        if (c.left!=null){
            c.left.parent = b;
        }
        c.left = b;
        c.right =a;
        b.parent = c;
        if (a.parent!=null){
            if (a.parent.left == a){
                a.parent.left = c;
            } else {
                a.parent.right = c;
            }
        } else {
            head = c;
        }
        c.parent = a.parent;
        a.parent = c;
        a.height = Math.max(getHeight(a.left),getHeight(a.right))+1;
        b.height = Math.max(getHeight(b.left),getHeight(b.right))+1;
        c.height =  Math.max(a.height,b.height)+1;
        return c;
    }

    /**
     * Проверка: сбалансировано ли поддерево
     * @param node Поддерево
     * @return True / False
     */
    private boolean isAvl(TreeNode<K,V> node){
        if (node.left!=null){
            boolean is =  isAvl(node.left);
            if(is == false)
                return false;
        }
        if(node.balance()==2 || node.balance()==-2){
            return false;
        }
        if (node.right!=null){
            boolean is = isAvl(node.right);
            if (is == false)
                return false;
        }
        return true;
    }

    /**
     * Проверка: сбалансировано ли дерево
     * @return True / False
     */
    private boolean isAvl(){
        return isAvl(head);
    }

    /**
     *  Конструктор по умолчанию. Создает пустое АВЛ дерево.
     */
    public AvlTree(){

    }

    /**
     * Поменять ключ у объекта
     * @param oldKey Старый ключ
     * @param newKey Новый ключ
     */
    public void change(K oldKey,K newKey){
        TreeNode<K,V> delete = internalDelete(oldKey);
        delete.key = newKey;
        add(delete);
    }

}
