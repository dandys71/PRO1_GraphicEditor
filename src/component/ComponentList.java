package component;

import java.util.ArrayList;
import java.util.List;

public class ComponentList {
    private final ArrayList<BaseComponent> baseComponents;

    private static ComponentList INSTANCE;


    public static ComponentList getINSTANCE() {
        if(INSTANCE == null){
            INSTANCE = new ComponentList();
        }
        return INSTANCE;
    }

    private ComponentList(){
       baseComponents = new ArrayList<>();
    }

    public void add(BaseComponent p){
        baseComponents.add(p);
    }

    public void remove(BaseComponent p){
        baseComponents.remove(p);
    }

    public List<BaseComponent> getComponents(){
        return baseComponents;
    }

}
