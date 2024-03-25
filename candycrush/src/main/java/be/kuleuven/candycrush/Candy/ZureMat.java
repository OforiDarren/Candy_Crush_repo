package be.kuleuven.candycrush.Candy;

public record ZureMat() {
    @Override
    public boolean equals(Object o) {
        //
        return (o != null && getClass() == o.getClass());
    }
}
