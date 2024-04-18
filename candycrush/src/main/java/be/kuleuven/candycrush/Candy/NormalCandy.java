package be.kuleuven.candycrush.Candy;

public record NormalCandy(int color) {
    public NormalCandy{
        if (color < 0 || color >= 4){
            throw new IllegalArgumentException("Onbekend kleurtype van Normalcandy (kleur): " + color);
        }
    }
    @Override
    public boolean equals(Object o) {

        return o != null && getClass() == o.getClass() && ((NormalCandy) o).color == this.color;
    }

}
