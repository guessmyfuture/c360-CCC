package com.campuz360.campuzz.repository.search;

import com.campuz360.campuzz.domain.Building;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Building entity.
 */
public interface BuildingSearchRepository extends ElasticsearchRepository<Building, Long> {
}
