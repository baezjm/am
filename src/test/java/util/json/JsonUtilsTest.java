package util.json;

import com.google.gson.annotations.SerializedName;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

enum TestEnum {
    @SerializedName("jrr")
    JRR,
    @SerializedName("d10s")
    D10S,
    @SerializedName("crack")
    CRACK;
}

public class JsonUtilsTest {

    @Test
    public void testEnumDesAndSer() {
        String j = "{\"name\":\"lalala\",\"enums\": [\"jrr\",\"d10s\",\"crack\"]}";
        TestDto fromJson = JsonUtils.SERIALIZER_GSON.fromJson(j, TestDto.class);
        assertEquals(TestEnum.JRR, fromJson.getEnums().get(0));
    }

    @Test
    public void fromJsonTestDTO() {
        String testDtoJson = "{\"name\":\"roman\",\"enums\":[\"jrr\"],\"map_list_dtos\":{\"foo\":[{\"age\": 1}]},\"list_of_maps\":[{\"foo\":\"bar\"}], \"ages\":{\"uno\": {\"age\": 1}, \"dos\": {\"age\": 2}}, \"ages_list\":[{\"uno\": {\"age\": 1}, \"dos\": {\"age\": 2}}]}";
        final TestDto testDto = JsonUtils.fromJson(JsonUtils.getSerializerGsonBuilder().create(), testDtoJson, TestDto.class);

        assertEquals("roman", testDto.getName());
        assertEquals(1, testDto.getEnums().size());
        assertEquals(TestEnum.JRR, testDto.getEnums().get(0));

        assertEquals(1, testDto.getListOfMaps().size());
        assertEquals("bar", testDto.getListOfMaps().get(0).get("foo"));

        assertEquals(InnerTestDTO.class, testDto.getAgesList().get(0).get("uno").getClass());

        assertEquals(InnerTestDTO.class, testDto.getAges().get("uno").getClass());
        assertEquals(new Integer(1), testDto.getAges().get("uno").getAge());
    }

    @Test
    public void fromJsonTestDTOEnumAsKey() {
        String testDtoJson = "{\"name\":\"roman\",\"enum_in_map_with_integer\":{\"jrr\":10},\"enum_in_map_with_long\":{\"jrr\":10},\"enums\":[\"jrr\"],\"map_list_dtos\":{\"foo\":[{\"age\": 1}]},\"list_of_maps\":[{\"foo\":\"bar\"}], \"ages\":{\"uno\": {\"age\": 1}, \"dos\": {\"age\": 2}}, \"ages_list\":[{\"uno\": {\"age\": 1}, \"dos\": {\"age\": 2}}]}";
        final TestDto testDto = JsonUtils.fromJson(testDtoJson, TestDto.class);

        assertEquals(new Integer(10), testDto.getEnumInMapWithInteger().get(TestEnum.JRR));
        assertEquals(new Long(10), testDto.getEnumInMapWithLong().get(TestEnum.JRR));
    }

    @Test
    public void fromJsonMapLongString() {
        String testDtoJson = "{\"name\":\"roman\",\"long_string_map\":{\"123\":\"uno\", \"456\":\"dos\"}}";
        final TestDto testDto = JsonUtils.fromJson(JsonUtils.getSerializerGsonBuilder().create(), testDtoJson, TestDto.class);

        assertEquals(2, testDto.getLongStringMap().size());
        assertEquals("uno", testDto.getLongStringMap().get(123L));
        assertEquals("dos", testDto.getLongStringMap().get(456L));
    }

    @Test
    public void fromJsonMapWithBracketStringKey() {
        String testDtoJson = "{\"name\":\"roman\",\"list_of_maps\":[{\"a_bracket[0]_key\":\"uno\"}]}";
        final TestDto testDto = JsonUtils.fromJson(JsonUtils.getSerializerGsonBuilder().create(), testDtoJson, TestDto.class);

        assertEquals(1, testDto.getListOfMaps().size());
        assertEquals("uno", testDto.getListOfMaps().get(0).get("a_bracket[0]_key"));
    }

    private Map<String, Object> jsonToMap(String json) {
        return JsonUtils.SERIALIZER_GSON.fromJson(json, Map.class);
    }


}


class TestDto {
    private String name;
    private List<TestEnum> enums;

    private Map<TestEnum, Integer> enumInMapWithInteger;

    private Map<TestEnum, Long> enumInMapWithLong;

    private List<Map<String, Object>> listOfMaps;

    private Map<String, InnerTestDTO> ages;

    private List<Map<String, InnerTestDTO>> agesList;

    private Map<String, List<InnerTestDTO>> mapListDtos;

    private Map<Long, String> longStringMap;

    public Map<Long, String> getLongStringMap() {
        return longStringMap;
    }

    public TestDto setLongStringMap(Map<Long, String> longStringMap) {
        this.longStringMap = longStringMap;
        return this;
    }

    public Map<TestEnum, Integer> getEnumInMapWithInteger() {
        return enumInMapWithInteger;
    }

    public TestDto withEnumInMapWithInteger(Map<TestEnum, Integer> enumInMap) {
        this.enumInMapWithInteger = enumInMap;
        return this;
    }

    public Map<TestEnum, Long> getEnumInMapWithLong() {
        return enumInMapWithLong;
    }

    public TestDto withEnumInMapWithLong(Map<TestEnum, Long> enumInMapWithLong) {
        this.enumInMapWithLong = enumInMapWithLong;
        return this;
    }

    public Map<String, List<InnerTestDTO>> getMapListDTOs() {
        return mapListDtos;
    }

    public TestDto setMapListDTOs(Map<String, List<InnerTestDTO>> mapListDTOs) {
        this.mapListDtos = mapListDTOs;
        return this;
    }

    public List<Map<String, InnerTestDTO>> getAgesList() {
        return agesList;
    }

    public TestDto setAgesList(List<Map<String, InnerTestDTO>> agesList) {
        this.agesList = agesList;
        return this;
    }

    public Map<String, InnerTestDTO> getAges() {
        return ages;
    }

    public TestDto setAges(Map<String, InnerTestDTO> ages) {
        this.ages = ages;
        return this;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<TestEnum> getEnums() {
        return enums;
    }

    public void setEnums(List<TestEnum> enums) {
        this.enums = enums;
    }

    public List<Map<String, Object>> getListOfMaps() {
        return listOfMaps;
    }

    public TestDto setListOfMaps(List<Map<String, Object>> listOfMaps) {
        this.listOfMaps = listOfMaps;
        return this;
    }
}

class InnerTestDTO {
    private Integer age;

    public Integer getAge() {
        return age;
    }

    public InnerTestDTO setAge(Integer age) {
        this.age = age;
        return this;
    }
}