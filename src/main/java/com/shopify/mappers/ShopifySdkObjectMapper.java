package com.shopify.mappers;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import com.fasterxml.jackson.module.jakarta.xmlbind.JakartaXmlBindAnnotationIntrospector;

public class ShopifySdkObjectMapper {

  private ShopifySdkObjectMapper() {
  }

  public static ObjectMapper buildMapper() {
    final AnnotationIntrospector inter = AnnotationIntrospector.pair(
        new JakartaXmlBindAnnotationIntrospector(TypeFactory.defaultInstance()),
        new JacksonAnnotationIntrospector());
    return JsonMapper.builder().addModule(new JodaModule()).enable(MapperFeature.USE_ANNOTATIONS)
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        .serializationInclusion(JsonInclude.Include.NON_NULL).annotationIntrospector(inter).build();
  }
}
