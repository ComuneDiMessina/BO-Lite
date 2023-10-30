package it.almaviva.impleme.bolite.integration.entities;

//@Converter(autoApply = true)
public class JsonConverter {
/*
    private static final long serialVersionUID = 1L;
    private static ObjectMapper mapper = new ObjectMapper();

    @Override
    public Object convertToDatabaseColumn(JsonObject objectValue) {
        try {
            PGobject out = new PGobject();
            out.setType("json");
            out.setValue(objectValue.toString());
            return out;
        } catch (Exception e) {
            throw new IllegalArgumentException("Unable to serialize to json field ", e);
        }
    }

    @Override
    public JsonObject convertToEntityAttribute(Object dataValue) {
        try {
            if (dataValue instanceof PGobject && ((PGobject) dataValue).getType().equals("json")) {
                return mapper.reader(new TypeReference<JsonObject>() {
                }).readValue(((PGobject) dataValue).getValue());
            }
            return Json.createObjectBuilder().build();
        } catch (IOException e) {
            throw new IllegalArgumentException("Unable to deserialize to json field ", e);
        }
    }

 */
}
