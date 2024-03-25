package be.kuleuven.candycrush.Candy;

import java.util.Objects;
import java.util.Random;

public sealed interface Candy permits Candy.NormalCandy, Candy.Spekjes, Candy.Drop, Candy.ZureMat, Candy.Zuurtjes {
    int color();

    record NormalCandy(int color) implements Candy {
        // Implementatie van NormalCandy
    }

    record ZureMat() implements Candy {
        @Override
        public int color() {
            return 0;
        }
        // Implementatie van ZureMat
    }

    record Drop() implements Candy {
        @Override
        public int color() {
            return 0;
        }
        // Implementatie van Drop
    }

    record Zuurtjes() implements Candy {
        @Override
        public int color() {
            return 0;
        }
        // Implementatie van Zuurtjes
    }

    record Spekjes() implements Candy {
        @Override
        public int color() {
            return 0;
        }
        // Implementatie van Spekjes
    }

}
