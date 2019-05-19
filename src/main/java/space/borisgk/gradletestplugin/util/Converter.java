package space.borisgk.gradletestplugin.util;

public interface Converter<F, T> {
    T convert(F o);
}
