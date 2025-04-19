package com.example.utils;

import com.example.contacts.models.ContactType;
import lombok.experimental.UtilityClass;
import org.mapstruct.Named;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@UtilityClass
public class MapperUtils {

    @Named("mapToEntity")
    public Map<ContactType, Map<Integer, String>> mapToEntity(Map<String, List<String>> contacts) {
        return contacts.entrySet().stream()
                .filter(entry -> {
                    try {
                        ContactType.valueOf(entry.getKey());
                        return true;
                    } catch (IllegalArgumentException e) {
                        return false;
                    }
                })
                .collect(Collectors.toMap(
                        entry -> ContactType.valueOf(entry.getKey()),
                        entry -> IntStream.range(0, entry.getValue().size())
                                .boxed()
                                .collect(Collectors.toMap(
                                        i -> i,
                                        i -> entry.getValue().get(i)
                                ))
                ));
    }

    @Named("mapToDto")
    public Map<String, List<String>> mapToDto(Map<ContactType, Map<Integer, String>> contacts) {
        return contacts.entrySet().stream()
                .collect(Collectors.toMap(
                   entry -> entry.getKey().toString(),
                   entry -> entry.getValue().entrySet().stream()
                           .sorted(Map.Entry.comparingByKey())
                           .map(Map.Entry::getValue)
                           .toList()
                ));
    }
}
