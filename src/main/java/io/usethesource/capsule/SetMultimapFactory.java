package io.usethesource.capsule;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class SetMultimapFactory {

  // private final Class<? extends ImmutableSetMultimap<?, ?>> targetClass;

  private final Method persistentMapOfEmpty;
  private final Method persistentMapOfKeyValuePairs;

  private final Method transientMapOfEmpty;
  private final Method transientMapOfKeyValuePairs;

  public SetMultimapFactory(Class<?> targetClass) {
    // this.targetClass = targetClass;

    try {
      persistentMapOfEmpty = targetClass.getMethod("of");
      persistentMapOfKeyValuePairs = targetClass.getMethod("of", Object.class, Object[].class);

      transientMapOfEmpty = targetClass.getMethod("transientOf");
      transientMapOfKeyValuePairs = targetClass.getMethod("transientOf", Object.class, Object[].class);
    } catch (NoSuchMethodException | SecurityException e) {
      e.printStackTrace();
      throw new RuntimeException(e);
    }
  }

  // public Class<? extends ImmutableSetMultimap<?, ?>> getTargetClass() {
  // return targetClass;
  // }

  @SuppressWarnings("unchecked")
  public final <K, V> ImmutableSetMultimap<K, V> of() {
    try {
      return (ImmutableSetMultimap<K, V>) persistentMapOfEmpty.invoke(null);
    } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
      throw new RuntimeException(e);
    }
  }

  @SuppressWarnings("unchecked")
  public final <K, V> ImmutableSetMultimap<K, V> of(K key, V ... values) {
    try {
      return (ImmutableSetMultimap<K, V>) persistentMapOfKeyValuePairs.invoke(null, key, values);
    } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
      throw new RuntimeException(e);
    }
  }

  @SuppressWarnings("unchecked")
  public final <K, V> TransientMap<K, V> transientOf() {
    try {
      return (TransientMap<K, V>) transientMapOfEmpty.invoke(null);
    } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
      throw new RuntimeException(e);
    }
  }

  @SuppressWarnings("unchecked")
  public final <K, V> TransientMap<K, V> transientOf(K key, V ... values) {
    try {
      return (TransientMap<K, V>) transientMapOfKeyValuePairs.invoke(null, key, values);
    } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
      e.printStackTrace();
      throw new RuntimeException(e);
    }
  }

}