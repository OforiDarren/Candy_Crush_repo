package be.kuleuven.candycrush.Candy;

public record Drop() {
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Drop that = (Drop) o;
        return that == o;
    }
}
