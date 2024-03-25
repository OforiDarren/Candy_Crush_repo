package be.kuleuven.candycrush.Candy;

public record Spekjes() {
    @Override
    public boolean equals(Object o) {
        //
        return (o != null && getClass() == o.getClass());
    }
}
