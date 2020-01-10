package org.mickey.framework.common.util.tuple;

import lombok.extern.slf4j.Slf4j;

/**
 * description
 *
 * @author mickey
 * 23/07/2019
 */
@Slf4j
public class Tuple2<E1, E2> extends AbstractTuple {
    private E1 e1;
    private E2 e2;

    public Tuple2(E1 e1, E2 e2) {
        this.e1 = e1;
        this.e2 = e2;
    }

    public E1 getE1() {
        return e1;
    }

    public E2 getE2() {
        return e2;
    }

    @Override
    protected int hashCodeInternal() {
        final int prime = 31;
        int result = 1;
        E1 e1 = getE1();
        E2 e2 = getE2();
        result = prime * result + ((e1 == null) ? 0 : e1.hashCode());
        result = prime * result + ((e2 == null) ? 0 : e2.hashCode());
        return result;
    }

    @Override
    protected boolean elementEquals(AbstractTuple abstractTuple) {
        if (!(abstractTuple instanceof Tuple2)) {
            return false;
        }

        Tuple2 tuple2 = (Tuple2) abstractTuple;
        if (this.e1.equals(tuple2.getE1()) && this.e2.equals(tuple2.getE2())) {
            return true;
        } else {
            return false;
        }
    }
}
