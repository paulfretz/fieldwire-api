package com.imageApp.controller.searchOptions;

import com.imageApp.resource.ImageResource;
import lombok.Value;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.From;
import javax.persistence.criteria.Predicate;
import java.util.Optional;

@Value
public class ImageSearchOptionsSpec implements SearchOptionsSpec<ImageResource> {

    ImageSearchOptions options;

    public <V, W> Predicate makePredicate(From<V, ImageResource> from, CriteriaQuery<W> query, CriteriaBuilder criteriaBuilder) {
        Predicate ret = null;
        Optional<String> optName = Optional.of(
                options
        ).map(
                ImageSearchOptions::getName
        ).filter(
                StringUtils::hasText
        );

        if (optName.isPresent()) {
            String name = '%' + optName.get() + '%';
            ret = and(
                    criteriaBuilder,
                    ret,
                    criteriaBuilder.like(from.get("name"), name)
            );
        }
        return makeDummyIfNeeded(criteriaBuilder, ret);
    }
}
