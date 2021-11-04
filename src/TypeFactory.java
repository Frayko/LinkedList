public class TypeFactory {
    public static TypeBuilder getBuider(Types type) {
        switch (type) {
            case Integer -> {
                return new IntegerType();
            }
            case String -> {
                return new StringType();
            }
            default -> {
                System.out.println("Wrong type, return null");
                return null;
            }
        }
    }
}
