package org.xapps.services.utils;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Singleton;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

@ApplicationScoped
public class ModelMapperProducer {

    @Produces
    @Singleton
    public ModelMapper produceModelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return modelMapper;
    }

}
