package business.jacksonClasses;

import java.io.Serializable;

/**
 *
 * @author boris.klett
 */
public class ContextOut implements Serializable {

    private static final long serialVersionUID = 1L;


    private String name;
    private Parameters parameters;
    private int lifespan;

    public ContextOut() {
    }

    public ContextOut(String name, Parameters parameters, int lifespan) {
        this.name = name;
        this.parameters = parameters;
        this.lifespan = lifespan;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Parameters getParameters() {
        return parameters;
    }

    public void setParameters(Parameters parameters) {
        this.parameters = parameters;
    }

    public int getLifespan() {
        return lifespan;
    }

    public void setLifespan(int lifespan) {
        this.lifespan = lifespan;
    }

}
