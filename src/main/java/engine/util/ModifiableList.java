package engine.util;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

public class ModifiableList<E> extends AbstractList<E> {
    private List<E> actualList = new ArrayList<>();
    private List<E> toAdd = new ArrayList<>();
    private List<E> toRemove = new ArrayList<>();

    public ModifiableList() {}

    @Override
    public E get(int index) {
        return actualList.get(index);
    }

    @Override
    public int size() {
        return actualList.size();
    }

    @Override
    public boolean add(E e) {
        toAdd.add(e);
        return true;
    }

    @Override
    public boolean remove(Object o) {
        try {
            @SuppressWarnings("unchecked")
            E e = (E) o;
            toRemove.add(e);
            return true;
        } catch (ClassCastException ex) {
            return false;
        }
    }

    public List<E> getRemoveTasks() {
        return toRemove;
    }

    public List<E> getAddTasks() {
        return toAdd;
    }

    public void applyChanges() {
        actualList.addAll(toAdd);
        actualList.removeAll(toRemove);
        toAdd.clear();
        toRemove.clear();
    }
}
