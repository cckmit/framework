package org.mickey.framework.common.util.tuple;

import lombok.extern.slf4j.Slf4j;

/**
 * description
 *
 * @author mickey
 * 23/07/2019
 */
@Slf4j
public abstract class Tuple {

    @Override
    public int hashCode(){
        int result = 17;
        result = 37 * result + this.hashCodeInternal();
        return result;
    }

    abstract protected int hashCodeInternal();

    abstract protected boolean elementEquals(Tuple tuple);

    @Override
    public boolean equals(Object object){
        boolean rv = false;
        if(!(object instanceof Tuple)) {
            return false;
        }
        if(object == this) {
            return true;
        }

        Tuple tuple = (Tuple)object;
        rv = elementEquals(tuple);

        return rv;
    }
}
