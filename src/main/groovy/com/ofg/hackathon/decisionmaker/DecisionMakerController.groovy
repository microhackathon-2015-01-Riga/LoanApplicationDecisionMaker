package com.ofg.hackathon.decisionmaker

import com.ofg.hackathon.decisionmaker.model.Decision
import com.ofg.hackathon.decisionmaker.model.LoanApplication
import com.wordnik.swagger.annotations.Api
import com.wordnik.swagger.annotations.ApiOperation
import groovy.transform.TypeChecked
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

import javax.validation.constraints.NotNull

import static com.ofg.hackathon.decisionmaker.config.Versions.HACKATHON_DECISION_MAKER_JSON_VERSION_1
import static org.springframework.http.HttpStatus.*
import static org.springframework.web.bind.annotation.RequestMethod.GET
import static org.springframework.web.bind.annotation.RequestMethod.PUT

@Slf4j
@RestController
@RequestMapping('/api/loanApplication/')
@TypeChecked
@Api(value = "loanApplicationId")
class DecisionMakerController {

    @Autowired
    DecisionEngine decisionEngine

    @RequestMapping(value = '{loanApplicationId}', method = PUT, consumes = HACKATHON_DECISION_MAKER_JSON_VERSION_1, produces = HACKATHON_DECISION_MAKER_JSON_VERSION_1)
    @ApiOperation(value = "Async new loan application putting for decision making")
    ResponseEntity putLoanApplication(
            @PathVariable @NotNull Long loanApplicationId, @RequestBody @NotNull LoanApplication application) {
        decisionEngine.process(loanApplicationId, application)
        new ResponseEntity(CREATED)
    }

    @RequestMapping(value = '{loanApplicationId}', method = GET, consumes = HACKATHON_DECISION_MAKER_JSON_VERSION_1, produces = HACKATHON_DECISION_MAKER_JSON_VERSION_1)
    @ApiOperation(value = "Returns processed decision")
    ResponseEntity<Decision> getDecision(@PathVariable @NotNull Long loanApplicationId) {
        def decision = decisionEngine.getDecision(loanApplicationId)
        if (decision.isPresent()) {
            return new ResponseEntity<Decision>(decision.get(), OK)
        }
        return new ResponseEntity<Decision>(NOT_FOUND)
    }
}
