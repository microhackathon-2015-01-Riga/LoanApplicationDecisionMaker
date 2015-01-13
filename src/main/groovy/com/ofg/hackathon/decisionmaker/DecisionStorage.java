package com.ofg.hackathon.decisionmaker;

import com.google.common.base.Optional;
import com.ofg.hackathon.decisionmaker.model.Decision;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import static com.google.common.base.Optional.fromNullable;
import static org.springframework.data.mongodb.core.query.Criteria.where;


@Component
public class DecisionStorage {

    @Autowired
    private MongoTemplate mongoTemplate;

    public void storeDecision(Decision decision) {
        mongoTemplate.save(decision);
    }

    public Optional<Decision> getDecision(Long applicationId) {
        return fromNullable(mongoTemplate.findOne(new Query(where("applicationId").is(applicationId)), Decision.class));
    }
}
