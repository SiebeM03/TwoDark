package woareXengine.util;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

public class SafeList<E> extends AbstractList<E> {
    private List<E> actualList = new ArrayList<>();
    private List<E> toAdd = new ArrayList<>();
    private List<E> toRemove = new ArrayList<>();

    public SafeList() {}

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

    public void applyChanges() {
        actualList.addAll(toAdd);
        actualList.removeAll(toRemove);
        toAdd.clear();
        toRemove.clear();
    }

    public List<E> getToRemove() {
        return toRemove;
    }
}
