package work.example.demo.Model;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import work.example.demo.entities.RealisationEntity;

import javax.mail.Address;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Builder
@Getter
@Setter
public class Realisation {
    private String id;
    private String title;
    private List<Address> cities;
    private List<String> workTypes;
    private String description;
    private List<String> images;
    private String dateValid;
    private Professional professional;

    private static ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    private static Logger log = LoggerFactory.getLogger(Address.class);

    public static RealisationEntity toEntity(Realisation realisation) {
        try {
            return RealisationEntity.builder().id(realisation.getId()).title(realisation.getTitle()).cities(objectMapper.writeValueAsString(realisation.getCities())).
                    workTypes(realisation.getWorkTypes()).description(realisation.getDescription()).images(realisation.getImages()).
                    dateValid(realisation.getDateValid())
                    .professional(realisation.getProfessional()!=null?Professional.toEntity(realisation.getProfessional()):null)
                    .build();
        }catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }
    public static Realisation toModel(RealisationEntity realisationEntity) {
        if(realisationEntity==null)
            return null;
        try {
            return Realisation.builder().id(realisationEntity.getId()).title(realisationEntity.getTitle()).cities(Arrays.asList(objectMapper.readValue(realisationEntity.getCities(),Address[].class))).
                    workTypes(realisationEntity.getWorkTypes()).description(realisationEntity.getDescription()).images(realisationEntity.getImages()).
                    dateValid(realisationEntity.getDateValid())
                    .professional(realisationEntity.getProfessional()!=null?Professional.toModel(realisationEntity.getProfessional()):null)
                    .build();
        }catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }

    public static List<RealisationEntity> convertRealisationListToRealisationEntityList(List<Realisation> realisations){
        return realisations.stream().map(Realisation::toEntity).collect(Collectors.toList());
    }
    public static List<Realisation> convertRealisationEntityListToRealisationList(List<RealisationEntity> realisations){
        return realisations.stream().map(Realisation::toModel).collect(Collectors.toList());
    }
}

