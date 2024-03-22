package be.kuleuven.candycrush.Candy;

public interface Candy permits normalCandy, zureMat, drop, zuurtjes, chocolade{
    record NormalCandy(int color) implements Candy {
        // Implementatie van NormalCandy
    }

    record zureMat() implements Candy {
        // Implementatie van SpecialCandy1
    }

    record drop() implements Candy {
        // Implementatie van SpecialCandy2
    }

    record zuurtjes() implements Candy {
        // Implementatie van SpecialCandy3
    }

    record chocolade() implements Candy {
        // Implementatie van SpecialCandy4
    }
}
