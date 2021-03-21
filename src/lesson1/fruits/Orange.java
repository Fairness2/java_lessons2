package lesson1.fruits;

public class Orange extends Fruit {
    public Orange () {
        this(1.5f);
    }

    public Orange (float weight) {
        setWeight(weight);
    }

    @Override
    public String toString() {
        return String.format("Апельсин, вес: %s", getWeight());
    }
}
