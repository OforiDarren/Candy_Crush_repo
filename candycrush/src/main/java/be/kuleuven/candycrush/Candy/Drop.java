package be.kuleuven.candycrush.Candy;

public record Drop() {
    @Override
    public boolean equals(Object o) {
        return o != null && getClass() == o.getClass();
    }
}
