package lesson1.fruits;

public class Apple extends Fruit {
    public Apple () {
        this(1.0f);
    }

    public Apple (float weight) {
        setWeight(weight);
    }

    @Override
    public String toString() {
        return String.format("Яблоко, вес: %s", getWeight());
    }
}
