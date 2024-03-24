package component;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Components {
    private final ArrayList<Primitive> primitives;

    private static Components INSTANCE;


    public static Components getINSTANCE() {
        if(INSTANCE == null){
            INSTANCE = new Components();
        }
        return INSTANCE;
    }

    private Components(){
       primitives = new ArrayList<>();
    }

    public void add(Primitive p){
        primitives.add(p);
    }

    public void remove(Primitive p){
        primitives.remove(p);
    }

    public List<Primitive> getComponents(){
        return primitives;
    }

}
