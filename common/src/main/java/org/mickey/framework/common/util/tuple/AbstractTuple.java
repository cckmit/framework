package org.mickey.framework.common.util.tuple;

import lombok.extern.slf4j.Slf4j;

/**
 * description
 *
 * @author mickey
 * 23/07/2019
 */
@Slf4j
public abstract class AbstractTuple {

    @Override
    public int hashCode() {
        int result = 17;
        result = 37 * result + this.hashCodeInternal();
        return result;
    }

    /**
     * build hash code internal
     * @return int
     */
    abstract protected int hashCodeInternal();

    /**
     * 元组中的元素内容比较
     * @param abstractTuple 元组
     * @return Boolean
     */
    abstract protected boolean elementEquals(AbstractTuple abstractTuple);

    @Override
    public boolean equals(Object object) {
        boolean rv = false;
        if (!(object instanceof AbstractTuple)) {
            return false;
        }
        if (object == this) {
            return true;
        }

        AbstractTuple abstractTuple = (AbstractTuple) object;
        rv = elementEquals(abstractTuple);

        return rv;
    }
}
