package com.inter.car.backendchallenge.service;

public class AbstractCarServiceTest {

    public static final String resultMakes = "?({\n"
            + "    \"Makes\": [\n"
            + "        {\n"
            + "            \"make_id\": \"acura\",\n"
            + "            \"make_display\": \"Acura\",\n"
            + "            \"make_is_common\": \"1\",\n"
            + "            \"make_country\": \"USA\"\n"
            + "        }\n"
            + "    ]\n"
            + "});";

    public static final String resultModel = "?({\n"
            + "    \"Models\": [\n"
            + "        {\n"
            + "            \"model_name\": \"ILX\",\n"
            + "            \"model_make_id\": \"acura\"\n"
            + "        },\n"
            + "        {\n"
            + "            \"model_name\": \"RDX\",\n"
            + "            \"model_make_id\": \"acura\"\n"
            + "        }\n"
            + "    ]\n"
            + "});";
}
