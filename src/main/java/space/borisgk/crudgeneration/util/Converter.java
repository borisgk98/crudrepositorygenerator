package space.borisgk.crudgeneration.util;

public interface Converter<F, T> {
    T convert(F o);
}
