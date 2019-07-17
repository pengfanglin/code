package spi;

import java.util.ServiceLoader;

/**
 * @author 彭方林
 * @version 1.0
 * @date 2019/7/16 15:49
 **/
public class Main {
    public static void main(String[] args) {
        ServiceLoader<Color> loader=ServiceLoader.load(Color.class);
        for (Color color : loader) {
            color.showColor();
        }
    }
}
