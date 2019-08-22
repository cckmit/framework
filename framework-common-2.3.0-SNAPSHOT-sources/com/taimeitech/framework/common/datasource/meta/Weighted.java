package com.taimeitech.framework.common.datasource.meta;

import java.util.ArrayList;
import java.util.List;

public class Weighted<T> {

	private T item;

	private int weight;

	private int currentWeight;

	private int effectiveWeight;

	public Weighted(T item, int weight) {
		this.item = item;
		this.weight = weight;
		this.effectiveWeight = weight;
	}

	public T getItem() {
		return item;
	}

	public void setItem(T item) {
		this.item = item;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public int getCurrentWeight() {
		return currentWeight;
	}

	public void setCurrentWeight(int currentWeight) {
		this.currentWeight = currentWeight;
	}

	public int getEffectiveWeight() {
		return effectiveWeight;
	}

	public void setEffectiveWeight(int effectiveWeight) {
		this.effectiveWeight = effectiveWeight;
	}
}
