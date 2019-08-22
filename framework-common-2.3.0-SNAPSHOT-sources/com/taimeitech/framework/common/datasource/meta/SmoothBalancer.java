package com.taimeitech.framework.common.datasource.meta;

import java.util.HashMap;

public class SmoothBalancer<K, T> {

	private HashMap<K, Weighted<T>> map = new HashMap<>();

	private int n;

	public  synchronized void  add(K id, T item, int weight) {
		Weighted<T> tWeighted = new Weighted<>(item, weight);
		map.put(id, tWeighted);
		n++;
	}

	public synchronized T next() {
		Weighted<T> weighted = nextWeighted();
		if (weighted == null) {
			return null;
		}
		return weighted.getItem();
	}

	public synchronized void remove(K id) {
		map.remove(id);
		n--;
	}

	public synchronized void update(K id, T item, int weight) {
		Weighted<T> weighted = map.get(id);
		if (weighted == null) {
			return;
		}
		if (weight == weighted.getWeight()) {
			weighted.setItem(item);
			return;
		}
		Weighted<T> tWeighted = new Weighted<>(item, weight);
		map.put(id, tWeighted);
	}


	private Weighted<T> nextWeighted() {
		if (n == 0) {
			return null;
		}

		if (n == 1) {
			return map.get(0);
		}

		return nextWeighted(map);
	}

	private Weighted<T> nextWeighted(HashMap<K, Weighted<T>> map) {
		int total = 0;
		Weighted<T> best = null;
		for (Weighted<T> weighted : map.values()) {
			if (weighted == null || weighted.getWeight() <= 0) {
				continue;
			}
			weighted.setCurrentWeight(weighted.getCurrentWeight() + weighted.getEffectiveWeight());
			total += weighted.getEffectiveWeight();
			if (weighted.getEffectiveWeight() < weighted.getWeight()) {
				weighted.setEffectiveWeight(weighted.getEffectiveWeight() + 1);
			}

			if (best == null || weighted.getCurrentWeight() > best.getCurrentWeight()) {
				best = weighted;
			}
		}

		if (best == null) {
			return null;
		}
		best.setCurrentWeight(best.getCurrentWeight() - total);
		return best;
	}

	public HashMap<K, Weighted<T>> getMap() {
		return map;
	}
}
