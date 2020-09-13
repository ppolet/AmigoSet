
package amigoset;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.NotSerializableException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.AbstractSet;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;


public class AmigoSet<E> extends AbstractSet<E> implements Serializable, Cloneable, Set<E>{

    private static final Object PRESENT = new Object();  //это будет наша заглушка
    
    //Список ключей будет нашим сэтом, а вместо значений будем пихать в мапу заглушку PRESENT
    private transient HashMap<E,Object> map;
    
    private static final long serialVersionUID = 12345678900L;
    
    public AmigoSet(){
        map = new HashMap<>();
    }
            
    public AmigoSet(Collection<? extends E> collection){
        int capacity = Math.max(16, (int)(collection.size()/.75f));
        map = new HashMap<>(capacity);
        for (E e: collection){
            this.add(e);
        }
    }
    
    @Override
    public Iterator<E> iterator() {
        return map.keySet().iterator();   //это итератор ключей. Получи множество ключей в map, верни его итератор
    }

    @Override
    public int size() {
        return map.size();   //это количество ключей в map, равно количеству элементов в map
    }

    @Override
    public boolean add(E e) {
        return map.put(e, PRESENT) == null;
    }

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    public boolean remove(Object o) {
        return map.keySet().remove(o);
    }

    @Override
    public boolean contains(Object o) {
        return map.keySet().contains(o);
    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    //4
    @Override
    public Object clone(){
        AmigoSet amigoSet;
        try{
            amigoSet = (AmigoSet) super.clone();
            amigoSet.map = (HashMap) map.clone();
        } catch (Exception ex){
            throw new InternalError();
        }
        return amigoSet;
    }

    private void writeObject(ObjectOutputStream objOutputStream) throws IOException{
        objOutputStream.defaultWriteObject();

        objOutputStream.writeInt(HashMapReflectionHelper.callHiddenMethod(map, "capacity"));
        objOutputStream.writeFloat(HashMapReflectionHelper.callHiddenMethod(map, "loadFactor"));

        objOutputStream.writeInt(map.keySet().size());

        for(E e:map.keySet()){
            objOutputStream.writeObject(e);
        }
    }
    
    private void readObject(ObjectInputStream objInputStream) throws IOException, ClassNotFoundException{
        objInputStream.defaultReadObject();
        
        int capacity = objInputStream.readInt();
        float loadFactor = objInputStream.readFloat();

        int size = objInputStream.readInt();

        map = new HashMap<>(capacity, loadFactor);

        for(int i = 0; i<size; i++){
            E e = (E)objInputStream.readObject();
            map.put(e, PRESENT);
        }
    }
    
}
