package util.json;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static com.google.gson.FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES;

public final class JsonUtils {

    private JsonUtils() {}

    private static final String YYYY_MM_DD_T_HH_MM_SS_SSS_Z = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

    private static Map<String, Class> CLASS_CACHE = new ConcurrentHashMap<>();

    /**
     * Default GSON
     */
    public static Gson GSON = getDefaultGsonBuilder().create();

    /**
     * Used for special Map<String, Object> de/serialization.
     */
    public static Gson SERIALIZER_GSON = getSerializerGsonBuilder().create();

    /**
     * @return the default configuration for GSON.
     */
    public static GsonBuilder getDefaultGsonBuilder() {
        return new GsonBuilder()
                .disableHtmlEscaping()
                .setFieldNamingPolicy(LOWER_CASE_WITH_UNDERSCORES);
    }

    /**
     * @return a GSON with Map and List special de/serialization.
     */
    public static GsonBuilder getSerializerGsonBuilder() {
        return new GsonBuilder()
                .disableHtmlEscaping()
                .setFieldNamingPolicy(LOWER_CASE_WITH_UNDERSCORES)
                .registerTypeAdapter(Map.class, new MapDeserializer())
                .registerTypeAdapter(List.class, new ListDeserializer())
                .registerTypeAdapter(Optional.class, new OptionalJsonAdapter());
    }

    public static void setSerializerGson(Gson gson) {
        SERIALIZER_GSON = gson;
    }

    public static void setGSON(Gson gson) {
        GSON = gson;
    }

    public static <T> T fromJson(String str, Class<T> clazz) {
        return fromJson(SERIALIZER_GSON, str, clazz);
    }


    public static <T> T fromJson(Gson gson, String str, Class<T> clazz) {
        return gson.fromJson(str, clazz);
    }


    public static <T> List<T> fromJsonList(String str, Class<T> clazz) {
        return fromJsonList(SERIALIZER_GSON, str, clazz);
    }

    public static <T> List<T> fromJsonList(Gson gson, String str, Class<T> clazz) {
        Type type = new ListParameterizedType(clazz);
        return gson.fromJson(str, type);
    }


    public static String stringObjectMapToJson(Map<String, Object> map) {
        return JsonUtils.SERIALIZER_GSON.toJson(map, getMapStringStringType());
    }


    private static Type getMapStringObjectType() {
        return new TypeToken<Map<String, Object>>() {
        }.getType();
    }

    private static Type getMapStringStringType() {
        return new TypeToken<Map<String, String>>() {
        }.getType();
    }


    /**
     * Return the class of the GSON Type.
     *
     * @param type
     * @return
     * @throws JsonParseException
     */
    private static Class<?> getClassOfT(Type type) throws JsonParseException {
        final String typeName = type.getTypeName();
        try {

            final Class aClass = CLASS_CACHE.get(typeName);
            if (aClass != null) return aClass;

            final Class<?> clazz = Class.forName(typeName);
            CLASS_CACHE.put(typeName, clazz);
            return clazz;

        } catch (ClassNotFoundException e) {
            throw new JsonParseException(e);
        }
    }

    private static Number getNumber(Type valueType, JsonElement v) {
        Number num = null;
        ParsePosition position = new ParsePosition(0);
        String vString = v.getAsString();
        try {
            if (valueType != null && Long.class.equals(valueType)) {
                num = new Long(vString);
            } else if (valueType != null && Integer.class.equals(valueType)) {
                num = new Integer(vString);
            } else {
                num = NumberFormat.getInstance(Locale.ROOT).parse(vString, position);
            }
        } catch (Exception e) {
        }
        if (num != null && num.getClass() == Double.class) {
            num = new BigDecimal(num.toString());
        }
        /*Check if the position corresponds to the length of the string
        if (position.getErrorIndex() < 0 && vString.length() == position.getIndex()) {
            if (num != null) {
                m.put(key, num);
                return;
            }
        }
        */
        return num;
    }

    private static class MapDeserializer implements JsonSerializer<Map<Object, Object>>, JsonDeserializer<Map<Object, Object>> {

        public Map<Object, Object> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

            Map<Object, Object> m = new LinkedHashMap<>();

            JsonObject jo = json.getAsJsonObject();

            Type valueType = null;
            Type keyType = null;
            if (typeOfT instanceof ParameterizedType) {
                keyType = ((ParameterizedType) typeOfT).getActualTypeArguments()[0];
                valueType = ((ParameterizedType) typeOfT).getActualTypeArguments()[1];
            }


            for (Map.Entry<String, JsonElement> mx : jo.entrySet()) {
                Object key = getKey(keyType, mx.getKey());

                JsonElement v = mx.getValue();

                if (v.isJsonArray()) {
                    if (valueType != null && (valueType instanceof ParameterizedType || List.class.isAssignableFrom(getClassOfT(valueType)))) {
                        m.put(key, JsonUtils.SERIALIZER_GSON.fromJson(v, valueType));
                    } else {
                        m.put(key, JsonUtils.SERIALIZER_GSON.fromJson(v, List.class));
                    }

                } else if (v.isJsonPrimitive()) {
                    JsonPrimitive prim = v.getAsJsonPrimitive();
                    if (prim.isBoolean()) {
                        m.put(key, prim.getAsBoolean());
                    } else if (prim.isString()) {
                        m.put(key, prim.getAsString());
                    } else if (prim.isNumber()) {
                        m.put(key, getNumber(valueType, prim));

                    } else {
                        m.put(key, null);
                    }

                } else if (v.isJsonObject()) {
                    if (valueType != null && !"java.lang.Object".equals(valueType.getTypeName())) {
                        m.put(key, JsonUtils.SERIALIZER_GSON.fromJson(v, valueType));
                    } else {
                        m.put(key, JsonUtils.SERIALIZER_GSON.fromJson(v, getMapStringObjectType()));
                    }
                } else {
                    m.put(key, null);
                }
            }
            return m;
        }

        private Object getKey(Type keyType, String key) {
            if (keyType != null && !Long.class.equals(keyType) && !String.class.equals(keyType)) {
                return JsonUtils.SERIALIZER_GSON.fromJson(key, keyType);
            }
            return Long.class.equals(keyType) ? Long.valueOf(key) : key;
        }

        @Override
        public JsonElement serialize(Map<Object, Object> src, Type typeOfSrc, JsonSerializationContext context) {
            JsonObject obj = new JsonObject();

            for (Map.Entry<Object, Object> entry : src.entrySet()) {

                final Object value = entry.getValue();
                JsonElement element;

                if (value instanceof Double) {
                    Double aDouble = (Double) value;
                    BigDecimal bigDecimal = new BigDecimal(aDouble);

                    if (bigDecimal.scale() > 0) {
                        element = new JsonPrimitive(aDouble);
                    } else {
                        element = new JsonPrimitive(aDouble.intValue());
                    }

                } else if (value instanceof Map) {
                    element = JsonUtils.SERIALIZER_GSON.toJsonTree(value, getMapStringStringType());

                } else {
                    element = JsonUtils.SERIALIZER_GSON.toJsonTree(value);
                }

                obj.add(entry.getKey().toString(), element);
            }
            return obj;
        }
    }

    private static class ListDeserializer implements JsonDeserializer<List<Object>> {

        public List<Object> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

            List<Object> m = new ArrayList<>();

            JsonArray arr = json.getAsJsonArray();
            for (JsonElement jsonElement : arr) {
                if (jsonElement.isJsonObject()) {

                    if (typeOfT instanceof ParameterizedType) {
                        Type type = ((ParameterizedType) typeOfT).getActualTypeArguments()[0];
                        if (type instanceof ParameterizedType && Map.class.equals(((ParameterizedType) type).getRawType())) {
                            m.add(JsonUtils.SERIALIZER_GSON.fromJson(jsonElement, type));
                        } else {
                            m.add(JsonUtils.SERIALIZER_GSON.fromJson(jsonElement, getClassOfT(type)));
                        }
                    } else {
                        m.add(JsonUtils.SERIALIZER_GSON.fromJson(jsonElement, Map.class));
                    }

                } else if (jsonElement.isJsonArray()) {
                    m.add(JsonUtils.SERIALIZER_GSON.fromJson(jsonElement, List.class));

                } else if (jsonElement.isJsonPrimitive()) {

                    JsonPrimitive prim = jsonElement.getAsJsonPrimitive();

                    if (prim.isBoolean()) {
                        m.add(prim.getAsBoolean());

                    } else if (prim.isString()) {

                        if (typeOfT instanceof ParameterizedType) {
                            final Type type = ((ParameterizedType) typeOfT).getActualTypeArguments()[0];
                            m.add(JsonUtils.SERIALIZER_GSON.fromJson(jsonElement, getClassOfT(type)));
                        } else {
                            m.add(prim.getAsString());
                        }

                    } else if (prim.isNumber()) {
                        m.add(getNumber(typeOfT, prim));

                    } else {
                        m.add(null);
                    }
                }
            }
            return m;
        }
    }

    private static class ListParameterizedType implements ParameterizedType {

        private Type type;

        private ListParameterizedType(Type type) {
            this.type = type;
        }

        @Override
        public Type[] getActualTypeArguments() {
            return new Type[]{type};
        }

        @Override
        public Type getRawType() {
            return ArrayList.class;
        }

        @Override
        public Type getOwnerType() {
            return null;
        }

    }

    /**
     * Serialize the content of the value and omit this object.
     */
    public static class OptionalJsonAdapter implements JsonSerializer<Optional> {

        /**
         * Gson invokes this call-back method during serialization when it encounters a field of the
         * specified type.
         * <p>
         * <p>In the implementation of this call-back method, you should consider invoking
         * {@link JsonSerializationContext#serialize(Object, Type)} method to create JsonElements for any
         * non-trivial field of the {@code src} object. However, you should never invoke it on the
         * {@code src} object itself since that will cause an infinite loop (Gson will call your
         * call-back method again).</p>
         *
         * @param optional  the object that needs to be converted to Json.
         * @param typeOfSrc the actual type (fully genericized version) of the source object.
         * @param context
         * @return a JsonElement corresponding to the specified object.
         */
        @Override
        public JsonElement serialize(Optional optional, Type typeOfSrc, JsonSerializationContext context) {
            return JsonUtils.SERIALIZER_GSON.toJsonTree(optional.orElse(null));
        }
    }

}